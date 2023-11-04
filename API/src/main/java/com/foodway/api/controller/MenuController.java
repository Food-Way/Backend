package com.foodway.api.controller;

import com.foodway.api.model.Product;
import com.foodway.api.repository.ProductRepository;
import com.foodway.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

    @RestController
    @RequestMapping("/menu")
    public class MenuController {

        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private ProductService productService;

        @GetMapping
        public ResponseEntity<List<Product>> getMenu(@RequestParam(name = "orderBy", defaultValue = "price") String orderBy) {
            ResponseEntity<List<Product>> response = productService.getMenu(orderBy);

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return ResponseEntity.noContent().build();
            }

            return response;
        }
        @GetMapping("/search")
        public ResponseEntity<List<Product>> searchMenu(@RequestParam(name = "query") String query) {
            ResponseEntity<List<Product>> response = productService.searchMenu(query);

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return ResponseEntity.noContent().build();
            }

            return response;
        }
        @PostMapping
        public ResponseEntity<Product> createMenuItem(@RequestBody Product product) {
            if (product.getIdProduct() != null) {
                return ResponseEntity.badRequest().build();
            }
            Product createdProduct = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Product> updateMenuItem(@PathVariable UUID id, @RequestBody Product product) {
            if (!productRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            product.setIdProduct(id);
            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteMenuItem(@PathVariable UUID id) {
            if (!productRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }


    }

