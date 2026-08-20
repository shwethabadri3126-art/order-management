package com.shwetha.order_management_service.service;

import com.shwetha.order_management_service.exception.ResourceNotFoundException;
import com.shwetha.order_management_service.model.Product;
import com.shwetha.order_management_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setSku(updatedProduct.getSku());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }
}