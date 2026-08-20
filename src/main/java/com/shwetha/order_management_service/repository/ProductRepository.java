package com.shwetha.order_management_service.repository;

import com.shwetha.order_management_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}