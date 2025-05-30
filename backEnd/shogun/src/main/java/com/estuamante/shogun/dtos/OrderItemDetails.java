package com.estuamante.shogun.dtos;

import com.estuamante.shogun.entities.Order;
import com.estuamante.shogun.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDetails {
    private UUID id;
    private ProductDto product;
    private UUID productVariantId;
    private Integer quantity;
    private Double itemPrice;
}
