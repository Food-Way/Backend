package com.foodway.api.controller;

import com.foodway.api.model.Product;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import com.foodway.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<Product> postProduct(@RequestBody @Validated RequestProduct product) {
        return productService.postProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> putProduct(@PathVariable UUID id, @RequestBody @Validated UpdateProductData product) {
        return productService.putProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

}
