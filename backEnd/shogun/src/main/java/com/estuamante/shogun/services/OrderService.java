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
import com.stripe.param.CustomerCreateParams;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
                // Tạo Customer mới trên Stripe
                String stripeCustomerId = paymentIntentService.createStripeCustomer(user.getUsername());
                user.setStripeCustomerId(stripeCustomerId);
                userDetailsRepository.save(user);
            }
            orderResponse.setCredentials(paymentIntentService.createPaymentIntent(order));
        }

        return orderResponse;
    }


    public void deleteOrder(@PathVariable(name = "id") UUID id) {
        orderRepository.deleteById(id);
    }
}
