package com.foodway.api.service.rate;

import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RateService {

    @Autowired
    RateRepository rateRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;

    public ResponseEntity<List<Rate>> getAll() {
        if(rateRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(rateRepository.findAll());
    }

    public ResponseEntity<Rate> get(Long id) {
        if(!rateRepository.existsById(id)) return ResponseEntity.status(404).build();
        return ResponseEntity.status(200).body(rateRepository.findById(id).get());
    }

    public ResponseEntity<Rate> post(UUID idCustomer, UUID idEstablishment, RequestRate data) {
        if(!customerRepository.existsById(idCustomer)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        if(!establishmentRepository.existsById(idEstablishment)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        final Customer customer = customerRepository.findById(idCustomer).get();
        final Establishment establishment = establishmentRepository.findById(idEstablishment).get();
        final Rate newRate = new Rate(data);

        if(customer.validateTypeRate(newRate.getTypeRate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rate type already exist!");
        }
        customer.addRate(newRate);
        establishment.addRate(newRate);
        newRate.setIdCustomer(idCustomer);
        newRate.setIdEstablishment(idEstablishment);
        return ResponseEntity.status(201).body(rateRepository.save(newRate));
    }

    public ResponseEntity<Rate> put(Long id, RequestRate data) {
        if(!rateRepository.existsById(id)) return ResponseEntity.status(404).build();
        final Rate rate = rateRepository.findById(id).get();
        final Rate newRate = new Rate(data);
        rate.update(newRate);
        return ResponseEntity.status(200).body(rateRepository.save(rate));
    }

    public ResponseEntity<Void>delete(Long id) {
        if(!rateRepository.existsById(id)) return ResponseEntity.status(404).build();
        rateRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }


}
