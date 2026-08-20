package com.shwetha.order_management_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Customer name is required") String customerName,
        @NotEmpty(message = "An order must contain at least one item")
        @Valid
        List<OrderItemRequest> items
) {
}