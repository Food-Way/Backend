package com.foodway.api.service.customer;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.record.DTOs.CommentDTO;
import com.foodway.api.record.DTOs.CustomerProfileDTO;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private RateRepository rateRepository;

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

    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(UUID id) {
        Customer customer = getCustomer(id).getBody();
        Double customerAvgRate = rateRepository.getAvgIndicatorCustomer(id);
        long customerQtdComments = commentRepository.countByIdCustomer(id);
        List<Comment> top4ByIdCustomer = commentRepository.findTop4ByIdCustomer(id);
        List<CommentDTO> commentDTOS = new ArrayList<>();

        top4ByIdCustomer.forEach(comment -> {
            String establishmentName = establishmentService.getEstablishment(comment.getIdEstablishment()).getBody().getEstablishmentName();
            commentDTOS.add(new CommentDTO(establishmentName, "Teste", comment.getComment(), 10.0));
        });

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customer.getName(), customer.getProfilePhoto(), customer.getBio(), 0, customerAvgRate, 0, customerQtdComments, commentDTOS);
        return ResponseEntity.status(200).body(customerProfileDTO);
    }
}
