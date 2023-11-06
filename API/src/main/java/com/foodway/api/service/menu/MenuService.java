package com.foodway.api.service.menu;
import com.foodway.api.model.Product;
import com.foodway.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private ProductRepository productRepository;

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

    public ResponseEntity<Product> createMenuItem(Product product) {
        if (product.getIdProduct() != null) {
            return ResponseEntity.badRequest().build();
        }
        Product createdProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    public ResponseEntity<Product> updateMenuItem(UUID id, Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setIdProduct(id);
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    public ResponseEntity<Void> deleteMenuItem(UUID id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

