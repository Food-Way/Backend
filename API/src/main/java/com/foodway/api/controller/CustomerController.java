package com.foodway.api.controller;


import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Favorite;
import com.foodway.api.record.DTOs.CustomerProfileDTO;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.record.UpdateCustomerPersonalInfo;
import com.foodway.api.record.UpdateCustomerProfile;
import com.foodway.api.service.customer.CustomerService;
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

    @GetMapping("/profile/{id}")
    @Operation(summary = "Get customer profile by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(@PathVariable @Valid UUID id){
        return customerService.getCustomerProfile(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> putCustomer(@PathVariable UUID id, @RequestBody @Valid UpdateCustomerData customer){
        return customerService.putCustomer(id, customer);
    }

    @PostMapping
    @Operation(summary = "Create a new customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid RequestUserCustomer customer){
        return customerService.saveCustomer(customer);
    }

    @PatchMapping("/profile/{id}")
    @Operation(summary = "Update customer profile by ID", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> patchCostumer(@PathVariable UUID id, @RequestBody  UpdateCustomerProfile customer){
        return customerService.patchCustomerProfile(id, customer);
    }
    @PatchMapping("/personal/{id}")
    @Operation(summary = "Update customer profile by email", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = CustomerNotFoundException.CODE, description = CustomerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> patchCostumer(@PathVariable UUID id, @RequestBody @Valid UpdateCustomerPersonalInfo customer){
        return customerService.patchCustomerPersonalInfo(id, customer);
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

    @PostMapping("/{idCustomer}/establishments/{idEstablishment}/favorite")
    @Operation(summary = "Add favorite establishment to customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created favorite"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Favorite> addFavoriteEstablishment(@PathVariable UUID idCustomer, @PathVariable UUID idEstablishment){
        return customerService.addFavoriteEstablishment(idCustomer, idEstablishment);
    }
}