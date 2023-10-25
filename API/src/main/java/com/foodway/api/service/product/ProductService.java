package com.foodway.api.service.product;

import com.foodway.api.model.Product;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import com.foodway.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity deleteProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Product> putProduct(UUID id, UpdateProductData data) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        product.get().update(Optional.ofNullable(data));
        Product savedProduct = product.get();
        productRepository.save(savedProduct);
        return ResponseEntity.status(200).body(savedProduct);
    }

    public ResponseEntity<Product> postProduct(RequestProduct product) {
        Product createdProduct = new Product(product);
        return ResponseEntity.status(201).body(productRepository.save(createdProduct));
    }

    public ResponseEntity<List<Product>> getProducts() {
        if (productRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(productRepository.findAll());
    }

    public ResponseEntity<Product> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(product.get());
    }
}
