package com.shwetha.order_management_service.repository;

import com.shwetha.order_management_service.model.Order;
import com.shwetha.order_management_service.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}