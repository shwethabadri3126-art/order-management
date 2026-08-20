package com.shwetha.order_management_service.service;

import com.shwetha.order_management_service.dto.CreateOrderRequest;
import com.shwetha.order_management_service.dto.OrderItemRequest;
import com.shwetha.order_management_service.exception.ResourceNotFoundException;
import com.shwetha.order_management_service.model.Order;
import com.shwetha.order_management_service.model.OrderItem;
import com.shwetha.order_management_service.model.OrderStatus;
import com.shwetha.order_management_service.model.Product;
import com.shwetha.order_management_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setStatus(OrderStatus.PENDING);

        for (OrderItemRequest itemReq : request.items()) {
            Product product = productService.getProductById(itemReq.productId());

            if (product.getStockQuantity() < itemReq.quantity()) {
                throw new IllegalStateException(
                        "Insufficient stock for product '" + product.getName() +
                                "'. Available: " + product.getStockQuantity() + ", requested: " + itemReq.quantity());
            }

            product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());
            item.setUnitPrice(product.getPrice());

            order.addItem(item);
        }

        order.recalculateTotal();
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);

        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Cannot change status of an order that is already " + order.getStatus());
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}