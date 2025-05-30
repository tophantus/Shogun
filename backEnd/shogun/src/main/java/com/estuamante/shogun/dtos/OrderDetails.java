package com.estuamante.shogun.dtos;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.entities.Address;
import com.estuamante.shogun.entities.OrderItem;
import com.estuamante.shogun.entities.OrderStatus;
import com.estuamante.shogun.entities.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {
    private UUID id;

    private Date orderDate;

    private AddressDto address;

    private Double totalAmount;

    private OrderStatus orderStatus;

    private String paymentMethod;

    private String shipmentTrackingNumber;

    private Date expectedDeliveryDate;

    private List<OrderItemDetails> orderItems;
}
