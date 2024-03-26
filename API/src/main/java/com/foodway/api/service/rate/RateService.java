package com.foodway.api.service.rate;

import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.handler.exceptions.RateNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.record.RequestRateAddOrUpdate;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<Rate> rates = rateRepository.findAll();
        if (rates.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "List of Rate is empty");
        return ResponseEntity.status(200).body(rates);
    }

    public ResponseEntity<Rate> get(Long id) {
        if (!rateRepository.existsById(id)) throw new RateNotFoundException("Rate not found!");
        return ResponseEntity.status(200).body(rateRepository.findById(id).get());
    }

    public ResponseEntity<List<Rate>> addOrUpdate(RequestRateAddOrUpdate data) {
        Optional<Customer> customer = customerRepository.findById(data.idCustomer());
        Optional<Establishment> establishment = establishmentRepository.findById(data.idEstablishment());
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException("Customer not found!");
        }
        if (!establishment.isPresent()) {
            throw new EstablishmentNotFoundException("Establishment not found!");
        }

        // validate if rate exists
        List<Rate> rates = new ArrayList<>();
        for (RequestRateAddOrUpdate.DescriptionRate rate : data.rates()) {
            Long id = 0L;
            if(customer.get().getRates().size() == 3){
                if (rate.name() == customer.get().getRates().get(0).getTypeRate()) {
                    id = customer.get().getRates().get(0).getIdRate();
                } else if (rate.name() == customer.get().getRates().get(1).getTypeRate()) {
                    id = customer.get().getRates().get(1).getIdRate();
                } else if (rate.name() == customer.get().getRates().get(2).getTypeRate()) {
                    id = customer.get().getRates().get(2).getIdRate();
                }
            }

            if(id == 0L){
                Rate newRate = new Rate(data.idCustomer(), data.idEstablishment(), rate.ratePoint(), rate.name());
                customer.get().addRate(newRate);
                establishment.get().addRate(newRate);
                newRate.setIdCustomer(newRate.getIdCustomer());
                newRate.setIdEstablishment(newRate.getIdEstablishment());
                rates.add(newRate);
                rateRepository.save(newRate);
            } else {
                Rate r = rateRepository.findById(id).get();
                r.setRatePoint(rate.ratePoint());
                r.setTypeRate(rate.name());
                rates.add(r);
                rateRepository.save(r);
            }
        }

        return ResponseEntity.status(201).body(rates);
    }

    public ResponseEntity<Void> delete(Long id) {
        if (!rateRepository.existsById(id)) throw new RateNotFoundException("Rate not found!");
        rateRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }


}
