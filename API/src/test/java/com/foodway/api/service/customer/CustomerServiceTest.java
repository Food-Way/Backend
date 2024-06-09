package com.foodway.api.service.customer;

import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Favorite;
import com.foodway.api.record.DTOs.CustomerProfileDTO;
import com.foodway.api.record.DTOs.SearchCustomerDTO;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.repository.*;
import com.foodway.api.service.establishment.EstablishmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    EstablishmentService establishmentService;
    @Mock
    FavoriteRepository favoriteRepository;
    @Mock
    RateRepository rateRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UpvoteRepository upvoteRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    @DisplayName("Should return all Customers")
    void getCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setIdUser(UUID.randomUUID());
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setIdUser(UUID.randomUUID());
        customers.add(customer2);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerService.getCustomers();

        List<Customer> retrievedCustomers = response.getBody();
        assertNotNull(retrievedCustomers);
        assertEquals(customers.size(), retrievedCustomers.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return no content when there are no Customers")
    void getCustomersNoContent() {
        Mockito.when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Customer>> response = customerService.getCustomers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should return a Customer by id")
    void getCustomer() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerService.getCustomer(id);
        Customer retrievedCustomer = response.getBody();
        assertNotNull(retrievedCustomer);
        assertEquals(customer.getIdUser(), retrievedCustomer.getIdUser());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw exception when Customer is not found")
    void getCustomerNotFound() {
        UUID id = UUID.randomUUID();

        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomer(id);
        });
    }

    @Test
    @DisplayName("Should update and return a Customer by id")
    void putCustomer() {
        UUID id = UUID.randomUUID();
        UpdateCustomerData customerData = CustomerFactory.createUpdateCustomerData();
        Customer customer = new Customer();
        customer.setIdUser(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerService.putCustomer(id, customerData);
        Customer updatedCustomer = response.getBody();
        assertNotNull(updatedCustomer);
        assertEquals(customer.getIdUser(), updatedCustomer.getIdUser());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw exception when Customer is not found")
    void putCustomerNotFound() {
        UUID id = UUID.randomUUID();
        UpdateCustomerData customerData = CustomerFactory.createUpdateCustomerData();

        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.putCustomer(id, customerData);
        });
    }

    @Test
    @DisplayName("Should create and return a new Customer")
    void saveCustomer() {
        RequestUserCustomer userCreateDto = CustomerFactory.createRequestUserCustomer();
        Customer customer = new Customer(userCreateDto);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerService.saveCustomer(userCreateDto);
        Customer createdCustomer = response.getBody();
        assertNotNull(createdCustomer);
        assertEquals(customer.getIdUser(), createdCustomer.getIdUser());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    @DisplayName("Should delete a Customer by id")
    void deleteCustomer() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerService.deleteCustomer(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }

    @Test
    @DisplayName("Should throw exception when Customer is not found")
    void deleteCustomerNotFound() {
        UUID id = UUID.randomUUID();

        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(id);
        });
    }

    @Test
    @DisplayName("Should toggle favorite establishment for a Customer")
    void toggleFavoriteEstablishment() {
        UUID idCustomer = UUID.randomUUID();
        UUID idEstablishment = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(idCustomer);
        Establishment establishment = new Establishment();
        establishment.setIdUser(idEstablishment);
        Favorite favorite = new Favorite(idCustomer, idEstablishment);
        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentService.getEstablishment(idEstablishment)).thenReturn(ResponseEntity.ok(establishment));
        Mockito.when(favoriteRepository.findByIdCustomerAndIdEstablishment(idCustomer, idEstablishment)).thenReturn(null);
        Mockito.when(favoriteRepository.save(Mockito.any(Favorite.class))).thenReturn(favorite);

        ResponseEntity<Favorite> response = customerService.toggleFavoriteEstablishment(idCustomer, idEstablishment);
        Favorite savedFavorite = response.getBody();
        assertNotNull(savedFavorite);
        assertEquals(idCustomer, savedFavorite.getIdCustomer());
        assertEquals(idEstablishment, savedFavorite.getIdEstablishment());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should remove a favorite establishment")
    void toggleFavoriteEstablishmentRemove() {
        UUID idCustomer = UUID.randomUUID();
        UUID idEstablishment = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(idCustomer);
        Establishment establishment = new Establishment();
        establishment.setIdUser(idEstablishment);
        Favorite favorite = new Favorite(customer.getIdUser(), establishment.getIdUser());

        Mockito.when(favoriteRepository.findByIdCustomerAndIdEstablishment(idCustomer, idEstablishment)).thenReturn(favorite);
        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentService.getEstablishment(idEstablishment)).thenReturn(ResponseEntity.status(200).body(establishment));

        ResponseEntity<Favorite> response = customerService.toggleFavoriteEstablishment(idCustomer, idEstablishment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        Mockito.verify(favoriteRepository, Mockito.times(1)).delete(favorite);
    }


    @Test
    @DisplayName("Should return Customer profile by id")
    void getCustomerProfile() {
        UUID idCustomer = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(idCustomer);
        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));
        Mockito.when(rateRepository.getAvgIndicatorCustomer(idCustomer)).thenReturn(4.5);
        Mockito.when(commentRepository.countByIdCustomer(idCustomer)).thenReturn(10L);
        Mockito.when(upvoteRepository.countByIdCustomer(idCustomer)).thenReturn(5L);
        Mockito.when(commentRepository.findTop4ByIdCustomer(idCustomer)).thenReturn(Optional.of(new ArrayList<>()));
        Mockito.when(favoriteRepository.findTop4ByIdCustomer(idCustomer)).thenReturn(Optional.of(new ArrayList<>()));

        ResponseEntity<CustomerProfileDTO> response = customerService.getCustomerProfile(idCustomer);
        CustomerProfileDTO profile = response.getBody();
        assertNotNull(profile);
        assertEquals(customer.getName(), profile.name());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should search and return Customers by name")
    void searchAllCustomers() {
        String customerName = "John";
        Customer customer = new Customer();
        customer.setName("John Doe");
        List<Customer> customers = List.of(customer);
        Mockito.when(customerRepository.findByNameContainsIgnoreCase(customerName)).thenReturn(customers);

        ResponseEntity<List<SearchCustomerDTO>> response = customerService.searchAllCustomers(customerName);
        List<SearchCustomerDTO> searchResults = response.getBody();
        assertNotNull(searchResults);
        assertFalse(searchResults.isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return a CustomerProfileDTO when Customer is found")
    void getCustomerProfileFound() {
        UUID idCustomer = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setIdUser(idCustomer);

        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));

        ResponseEntity<CustomerProfileDTO> response = customerService.getCustomerProfile(idCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should throw exception when Customer is not found")
    void getCustomerProfileNotFound() {
        UUID idCustomer = UUID.randomUUID();

        Mockito.when(customerRepository.findById(idCustomer)).thenThrow(new CustomerNotFoundException("Customer not found"));

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerProfile(idCustomer);
        });
    }

    @Test
    @DisplayName("Should add CommentDTO to commentDTOS when Comment is found")
    void getCustomerProfileAddCommentDTO() {
        UUID idCustomer = UUID.randomUUID();
        UUID idEstablishment = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setIdUser(idCustomer);
        Establishment establishment = new Establishment();
        establishment.setIdUser(idEstablishment);
        Comment comment = new Comment();
        comment.setIdEstablishment(idEstablishment);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentService.getEstablishment(idEstablishment)).thenReturn(ResponseEntity.status(200).body(establishment));
        Mockito.when(commentRepository.findTop4ByIdCustomer(idCustomer)).thenReturn(Optional.of(comments));

        ResponseEntity<CustomerProfileDTO> response = customerService.getCustomerProfile(idCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should add EstablishmentDTO to favoriteEstablishments when Favorite is found")
    void getCustomerProfileAddEstablishmentDTO() {
        // Create a random UUID for customer and establishment
        UUID idCustomer = UUID.randomUUID();
        UUID idEstablishment = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setIdUser(idCustomer);
        Establishment establishment = new Establishment();
        establishment.setIdUser(idEstablishment);
        Favorite favorite = new Favorite(customer.getIdUser(), establishment.getIdUser());

        List<Favorite> favorites = new ArrayList<>();
        favorites.add(favorite);

        Mockito.when(customerRepository.findById(idCustomer)).thenReturn(Optional.of(customer));
        Mockito.when(establishmentService.getEstablishment(idEstablishment)).thenReturn(ResponseEntity.status(200).body(establishment));
        Mockito.when(favoriteRepository.findTop4ByIdCustomer(idCustomer)).thenReturn(Optional.of(favorites));

        ResponseEntity<CustomerProfileDTO> response = customerService.getCustomerProfile(idCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}