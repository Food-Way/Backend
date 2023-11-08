package com.foodway.api.repository;

import com.foodway.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer,UUID> {


}
