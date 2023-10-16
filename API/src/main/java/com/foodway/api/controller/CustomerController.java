package com.foodway.api.controller;


import com.foodway.api.model.Customer;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable UUID id){
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> putCustomer(@PathVariable UUID id, @RequestBody @Validated UpdateCustomerData customer){
        return customerService.putCustomer(id, customer);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Validated RequestUserCustomer customer){
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable UUID id){
        return customerService.deleteCustomer(id);
    }
}
