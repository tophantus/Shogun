package com.estuamante.shogun.controllers;

import com.estuamante.shogun.dtos.OrderDto;
import com.estuamante.shogun.dtos.OrderRequest;
import com.estuamante.shogun.dtos.OrderResponse;
import com.estuamante.shogun.entities.Order;
import com.estuamante.shogun.services.OrderService;
import com.estuamante.shogun.services.PaymentIntentService;
import com.stripe.exception.StripeException;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, Principal principal) throws Exception {
        OrderResponse orderResponse = orderService.createOrder(orderRequest, principal);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UUID>> getAllOrderByUserId(@PathVariable(name = "id") UUID id) {
        List<UUID> uuids = orderService.getAllOrder(id);
        return new ResponseEntity<>(uuids, HttpStatus.OK);
    }
}
