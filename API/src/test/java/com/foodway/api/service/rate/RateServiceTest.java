package com.foodway.api.service.rate;

import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.handler.exceptions.RateNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    @Mock
    RateRepository rateRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    EstablishmentRepository establishmentRepository;

    @InjectMocks
    RateService rateService;

    @Test
    @DisplayName("Should return all rates")
    void getAll() {
        Mockito.when(rateRepository.findAll()).thenReturn(List.of(new Rate(), new Rate()));
        ResponseEntity<List<Rate>> r = rateService.getAll();
        List<Rate> rates = r.getBody();

        assertNotNull(rates);
        assertEquals(2, rates.size());
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    @DisplayName("Should return exception NO CONTENT if Rate is empty")
    void getAllException() {
        Mockito.when(rateRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> rateService.getAll().getBody());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return a Rate by id")
    void get() {
        long id = 32L;
        Rate rate = new Rate();
        rate.setIdRate(id);
        Mockito.when(rateRepository.existsById(id)).thenReturn(true);
        Mockito.when(rateRepository.findById(id)).thenReturn(Optional.of(rate));

        ResponseEntity<Rate> ra = rateService.get(id);
        Rate r = ra.getBody();
        assertNotNull(r);
        assertEquals(rate.getIdRate(), r.getIdRate());
        assertEquals(HttpStatus.OK, ra.getStatusCode());
    }

    @Test
    @DisplayName("Should return a exception NO FOUND when Rate does not exist by id")
    void getException() {
        long id = 32L;
        Mockito.when(rateRepository.existsById(id)).thenReturn(false);

        RateNotFoundException exception = assertThrows(RateNotFoundException.class,
                () -> rateService.get(id));

        assertEquals(RateNotFoundException.DESCRIPTION, exception.getMessage());
    }

    @Test
    @DisplayName("Should return the Rate created")
    void post() {
        Customer customer = new Customer();
        UUID idC = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        customer.setIdUser(idC);

        Establishment establishment = new Establishment();
        UUID idE = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        establishment.setIdUser(idE);

        Mockito.when(customerRepository.existsById(idC)).thenReturn(true);
        Mockito.when(establishmentRepository.existsById(idE)).thenReturn(true);
        Mockito.when(customerRepository.findById(idC)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentRepository.findById(idE)).thenReturn(Optional.of(establishment));

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        ResponseEntity<Rate> rate = rateService.post(newRate);
        Rate r = rate.getBody();
        assertNotNull(r);
        assertEquals(idCustomer, r.getIdCustomer());
        assertEquals(idEstablishment, r.getIdEstablishment());
        assertEquals(ratePoint, r.getRatePoint());
        assertEquals(typeRate, r.getTypeRate());
        assertEquals(HttpStatus.CREATED, rate.getStatusCode());
    }

    @Test
    @DisplayName("Should return Exception NO FOUND when customer does not exist")
    void postExceptionExistByCustomer() {
        Customer customer = new Customer();
        UUID idC = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        customer.setIdUser(idC);

        Mockito.when(customerRepository.existsById(idC)).thenReturn(false);

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> rateService.post(newRate));

        assertEquals(CustomerNotFoundException.DESCRIPTION, exception.getMessage());
    }

    @Test
    @DisplayName("Should return Exception NO FOUND when establishment does not exist")
    void postExceptionExistByEstablishment() {
        Customer customer = new Customer();
        UUID idC = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        customer.setIdUser(idC);
        Establishment establishment = new Establishment();
        UUID idE = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        establishment.setIdUser(idE);

        Mockito.when(customerRepository.existsById(idC)).thenReturn(true);
        Mockito.when(establishmentRepository.existsById(idE)).thenReturn(true);
        Mockito.when(customerRepository.findById(idC)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentRepository.findById(idE)).thenReturn(Optional.of(establishment));

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        rateService.post(newRate);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> rateService.post(newRate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return Exception BAD REQUEST when rate type already does exist")
    void postExceptionValidation() {
        Customer customer = new Customer();
        UUID idC = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        customer.setIdUser(idC);
        Establishment establishment = new Establishment();
        UUID idE = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        establishment.setIdUser(idE);

        Mockito.when(customerRepository.existsById(idC)).thenReturn(true);
        Mockito.when(establishmentRepository.existsById(idE)).thenReturn(false);

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        EstablishmentNotFoundException exception = assertThrows(EstablishmentNotFoundException.class,
                () -> rateService.post(newRate));

        assertEquals(EstablishmentNotFoundException.DESCRIPTION, exception.getMessage());
    }

    @Test
    @DisplayName("Should return Rate updated")
    void put() {
        Rate rate = new Rate();
        Long idRate = 32L;
        rate.setIdRate(idRate);

        Mockito.when(rateRepository.existsById(idRate)).thenReturn(true);
        Mockito.when(rateRepository.findById(idRate)).thenReturn(Optional.of(rate));

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        ResponseEntity<Rate> ra = rateService.put(idRate, newRate);
        Rate r = ra.getBody();
        assertNotNull(r);
        assertEquals(ratePoint, r.getRatePoint());
        assertEquals(typeRate, r.getTypeRate());
        assertEquals(HttpStatus.OK, ra.getStatusCode());
    }

    @Test
    @DisplayName("Should return a exception NO FOUND when rate does not exist")
    void putExceptionRateNotFound() {
        Rate rate = new Rate();
        Long idRate = 32L;
        rate.setIdRate(idRate);

        Mockito.when(rateRepository.existsById(idRate)).thenReturn(false);

        UUID idCustomer = UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002");
        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
        Double ratePoint = 3.0;
        ETypeRate typeRate = ETypeRate.FOOD;

        RequestRate newRate = new RequestRate(
                idCustomer,
                idEstablishment,
                ratePoint,
                typeRate
        );

        RateNotFoundException exception = assertThrows(RateNotFoundException.class,
                () -> rateService.put(idRate, newRate));

        assertEquals(RateNotFoundException.DESCRIPTION, exception.getMessage());
    }

    @Test
    @DisplayName("Should return Rate deleted")
    void delete() {
        Rate rate = new Rate();
        Long idRate = 32L;
        rate.setIdRate(idRate);

        Mockito.when(rateRepository.existsById(idRate)).thenReturn(true);

        ResponseEntity<Void> ra = rateService.delete(idRate);
        assertEquals(HttpStatus.OK, ra.getStatusCode());
    }

    @Test
    @DisplayName("Should return a expeption NO FOUND when rate does not exist")
    void deleteExceptionRateNotFound() {
        Long idRate = 32L;

        Mockito.when(rateRepository.existsById(idRate)).thenReturn(false);

        RateNotFoundException exception = assertThrows(RateNotFoundException.class,
                () -> rateService.delete(idRate));

        assertEquals(RateNotFoundException.DESCRIPTION, exception.getMessage());
    }
}