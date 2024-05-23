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
        if (rates.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "List of Rate is empty");
        return ResponseEntity.status(200).body(rates);
    }

    public ResponseEntity<Rate> get(Long id) {
        if (!rateRepository.existsById(id))
            throw new RateNotFoundException("Rate not found!");
        return ResponseEntity.status(200).body(rateRepository.findById(id).get());
    }

    public ResponseEntity<List<Rate>> addOrUpdate(RequestRateAddOrUpdate data) {
        Optional<Customer> customerOptional = customerRepository.findById(data.idCustomer());
        Optional<Establishment> establishmentOptional = establishmentRepository.findById(data.idEstablishment());
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found!");
        }
        if (!establishmentOptional.isPresent()) {
            throw new EstablishmentNotFoundException("Establishment not found!");
        }
        Customer customer = customerOptional.get();
        Establishment establishment = establishmentOptional.get();
        List<Rate> rates = new ArrayList<>();
        for (RequestRateAddOrUpdate.DescriptionRate rate : data.rates()) {
            Long id = 0L;
            List<Rate> ratesEstablishment = customer.getRates()
                    .stream()
                    .filter(
                            r -> r.getIdEstablishment().equals(data.idEstablishment()))
                    .toList();
            if (ratesEstablishment.size() == 3) {
                for (Rate establishmentRate : ratesEstablishment) {
                    if (rate.name().equals(establishmentRate.getTypeRate())) {
                        id = establishmentRate.getIdRate();
                        break;
                    }
                }
            }
            if (id == 0L) {
                Rate newRate = new Rate(data.idCustomer(), data.idEstablishment(), rate.ratePoint(), rate.name());
                customer.addRate(newRate);
                establishment.addRate(newRate);
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
        if (!rateRepository.existsById(id))
            throw new RateNotFoundException("Rate not found!");
        rateRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }

}
