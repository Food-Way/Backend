package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.ProductNotFoundException;
import com.foodway.api.model.Product;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import com.foodway.api.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Product>> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the product"),
            @ApiResponse(responseCode = ProductNotFoundException.CODE, description = ProductNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new product", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created product"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Product> postProduct(@RequestBody @Valid RequestProduct product) {
        return productService.postProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated product"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = ProductNotFoundException.CODE, description = ProductNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Product> putProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductData product) {
        return productService.putProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted product"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

}