package com.foodway.api.service.product;

import com.foodway.api.model.Product;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import com.foodway.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<List<Product>> getMenu(String orderBy) {
        List<Product> menu;

        if ("price".equals(orderBy)) {
            menu = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        } else if ("name".equals(orderBy)) {
            menu = productRepository.findAll(Sort.by(Sort.Order.asc("name")));
        } else if ("maxPrice".equals(orderBy)) {
            menu = productRepository.findAll(Sort.by(Sort.Order.desc("price")));
        } else if ("minPrice".equals(orderBy)) {
            menu = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        } else {

            menu = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        }

        if (menu.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(menu);
        }

        return ResponseEntity.ok(menu);
    }
    public ResponseEntity<List<Product>> searchMenu(String query) {
        List<Product> searchResults = productRepository.findByNameContaining(query);

        if (searchResults.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(searchResults);
    }
}





