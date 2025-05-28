package com.estuamante.shogun.dtos;

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
public class OrderRequest {
    private UUID userId;
    private Date orderDate;
    private UUID addressId;
    private Double totalAmount;
    private List<OrderItemRequest> orderItemsRequest;
    private Date expectedDeliveryDate;
    private String paymentMethod;
    private Double discount;
    private String shipmentTrackingNumber;
}
