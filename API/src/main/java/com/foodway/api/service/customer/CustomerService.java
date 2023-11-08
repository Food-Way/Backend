package com.foodway.api.service.customer;

import com.foodway.api.model.Customer;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CommentRepository commentRepository;

    public ResponseEntity<List<Customer>> getCustomers() {
        if (customerRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(customerRepository.findAll());
    }

    public ResponseEntity<Customer> getCustomer(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity<Customer> putCustomer(UUID id, UpdateCustomerData data) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        customerOptional.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(customerRepository.save(customerOptional.get()));
    }

    public ResponseEntity<Customer> saveCustomer(RequestUserCustomer userCreateDto) {
        Customer createdCustomer = new Customer(userCreateDto);
        return ResponseEntity.status(201).body(customerRepository.save(createdCustomer));
    }

    public ResponseEntity<Customer> deleteCustomer(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

//    public ResponseEntity<Customer> getCustomerProfile(UUID id) {
//        Customer customer = getCustomer(id).getBody();
//        long l = commentRepository.countByIdCustomer(id);
//    }
}
