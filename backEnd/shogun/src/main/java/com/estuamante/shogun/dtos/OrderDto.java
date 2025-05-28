package com.estuamante.shogun.dtos;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.entities.Address;
import com.estuamante.shogun.entities.OrderItem;
import com.estuamante.shogun.entities.OrderStatus;
import com.estuamante.shogun.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID id;

    private Date orderDate;

    private User user;

    private Address address;

    private Double totalAmount;

    private OrderStatus orderStatus;

    private String paymentMethod;

    private String shipmentTrackingNumber;

    private Date expectedDeliveryDate;

    private List<OrderItem> orderItems;

    private Double discount;

    private Payment payment;
}
