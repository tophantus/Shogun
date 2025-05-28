package com.estuamante.shogun.entities;

import com.estuamante.shogun.auth.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    private UUID productVariantId;

    @Column(nullable = false)
    private Integer quantity;

    private Double itemPrice;
}
