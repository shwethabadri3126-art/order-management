package com.shwetha.order_management_service.dto;

import com.shwetha.order_management_service.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull(message = "Status is required") OrderStatus status
) {
}