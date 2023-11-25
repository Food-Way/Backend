package com.foodway.api.repository;

import com.foodway.api.model.Customer;
import com.foodway.api.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer,UUID> {
    Customer findByIdUserAndPassword(UUID idUser, String password);
    Customer findByEmailAndPassword(String email, String password);
    List<Customer> findByNameContainsIgnoreCase(String name);

    long countByUpvoteList_IdCustomer(UUID idCustomer);

}
