package com.estuamante.shogun.services;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.repositories.UserDetailsRepository;
import com.estuamante.shogun.auth.services.CustomUserDetailsService;
import com.estuamante.shogun.dtos.OrderRequest;
import com.estuamante.shogun.dtos.OrderResponse;
import com.estuamante.shogun.entities.*;
import com.estuamante.shogun.mappers.OrderMapper;
import com.estuamante.shogun.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.*;

@Service
public class OrderService {
    private final CustomUserDetailsService userDetailsService;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final PaymentIntentService paymentIntentService;
    private final UserDetailsRepository userDetailsRepository;

    public OrderService(
            CustomUserDetailsService userDetailsService,
            OrderRepository orderRepository,
            ProductService productService,
            OrderMapper orderMapper,
            PaymentIntentService paymentIntentService,
            UserDetailsRepository userDetailsRepository
    ) {
        this.userDetailsService = userDetailsService;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderMapper = orderMapper;
        this.paymentIntentService = paymentIntentService;
        this.userDetailsRepository = userDetailsRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest, Principal principal) throws Exception {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = user.getAddresses().stream().filter(
                a -> orderRequest.getAddressId().equals(a.getId())
            ).findFirst().orElseThrow(BadRequestException::new);
        Order order = orderMapper.requestToEntity(orderRequest);
        //
        order.setUser(user);
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.PENDING);
        //
        List<OrderItem> orderItems = orderRequest.getOrderItemsRequest().stream().map(orderItemRequest -> {
            try {
                Product product = productService.findProductById(orderItemRequest.getProductId());
                return OrderItem.builder()
                        .product(product)
                        .order(order)
                        .productVariantId(orderItemRequest.getProductVariantId())
                        .quantity(orderItemRequest.getQuantity())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }}).toList();
        order.setOrderItems(orderItems);
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(orderRequest.getTotalAmount());
        payment.setPaymentMethod(orderRequest.getPaymentMethod());
        payment.setPaymentDate(new Date());
        payment.setOrder(order);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        OrderResponse orderResponse = OrderResponse.builder()
                .paymentMethod(order.getPaymentMethod())
                .orderId(savedOrder.getId())
                .build();
        if (Objects.equals(orderRequest.getPaymentMethod(), "CARD")) {
            if (user.getStripeCustomerId() == null || user.getStripeCustomerId().isEmpty()) {
                // Create new Customer
                String stripeCustomerId = paymentIntentService.createStripeCustomer(user.getUsername());
                user.setStripeCustomerId(stripeCustomerId);
                userDetailsRepository.save(user);
            }
            orderResponse.setCredentials(paymentIntentService.createPaymentIntent(order));
        }

        return orderResponse;
    }


    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    public List<UUID> getAllOrder(UUID id) {
        return orderRepository.findOrderIdsByUserId(id);
    }

    public Map<String, String> updatePayment(String paymentIntentId, String status) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            if (paymentIntent != null && paymentIntent.getStatus().equals("succeeded")) {
                String orderId = paymentIntent.getMetadata().get("orderId");
                Order order = orderRepository.findById(UUID.fromString(orderId))
                        .orElseThrow(BadRequestException::new);
                Payment payment = order.getPayment();
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                payment.setPaymentMethod(paymentIntent.getPaymentMethod());
                order.setPaymentMethod(paymentIntent.getPaymentMethod());
                order.setPayment(payment);
                Order savedOrder = orderRepository.save(order);
                Map<String, String> map = new HashMap<>();
                map.put("orderId", String.valueOf(savedOrder.getId()));
                return map;
            } else {
                throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
            }
        } catch (StripeException | BadRequestException e) {
            throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
        }
    }
}
