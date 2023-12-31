package com.foodway.api.controller;


import com.foodway.api.exceptions.CustomerNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get all customers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all customers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Customer>> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> getCustomer(@PathVariable UUID id){
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> putCustomer(@PathVariable UUID id, @RequestBody @Validated UpdateCustomerData customer){
        return customerService.putCustomer(id, customer);
    }

    @PostMapping
    @Operation(summary = "Create a new customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Validated RequestUserCustomer customer){
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted customer"),
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity deleteCustomer(@PathVariable UUID id){
        return customerService.deleteCustomer(id);
    }
}
