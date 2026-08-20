package com.shwetha.order_management_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import com.shwetha.order_management_service.model.Order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product is required")
    private Product product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull
    private BigDecimal unitPrice;
}