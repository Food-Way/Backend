package com.foodway.api.controller;

import com.foodway.api.model.Product;
import com.foodway.api.service.menu.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
@Tag(name = "Menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @Operation(summary = "Get menu", method = "GET")
    @ApiResponse(responseCode = "200", description = "Return the menu")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Product>> getMenu(@RequestParam(name = "orderBy", defaultValue = "price") String orderBy) {
        return menuService.getMenu(orderBy);
    }

    @GetMapping("/search")
    @Operation(summary = "Search menu", method = "GET")
    @ApiResponse(responseCode = "200", description = "Return search results")
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Product>> searchMenu(@RequestParam(name = "query") String query) {
        return menuService.searchMenu(query);
    }

    @PostMapping
    @Operation(summary = "Create a menu item", method = "POST")
    @ApiResponse(responseCode = "201", description = "Return the created menu item")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Product> createMenuItem(@RequestBody Product product) {
        return menuService.createMenuItem(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a menu item by ID", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Return the updated menu item")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Product> updateMenuItem(@PathVariable UUID id, @RequestBody Product product) {
        return menuService.updateMenuItem(id, product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a menu item by ID", method = "DELETE")
    @ApiResponse(responseCode = "200", description = "Menu item deleted successfully")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable UUID id) {
        return menuService.deleteMenuItem(id);
    }
}