package com.shwetha.order_management_service.controller;

import com.shwetha.order_management_service.dto.CreateOrderRequest;
import com.shwetha.order_management_service.dto.UpdateStatusRequest;
import com.shwetha.order_management_service.model.Order;
import com.shwetha.order_management_service.model.OrderStatus;
import com.shwetha.order_management_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        return status != null ? orderService.getOrdersByStatus(status) : orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest request) {
        return orderService.updateStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}