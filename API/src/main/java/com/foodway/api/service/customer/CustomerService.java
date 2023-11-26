package com.foodway.api.service.customer;

import com.foodway.api.controller.UserController;
import com.foodway.api.record.DTOs.*;
import com.foodway.api.record.UpdateCustomerPersonalInfo;
import com.foodway.api.record.UpdateCustomerProfile;
import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Favorite;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.FavoriteRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.service.user.authentication.dto.UserLoginDto;
import com.foodway.api.service.user.authentication.dto.UserTokenDto;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    UserController userController;

    public ResponseEntity<List<Customer>> getCustomers() {
        if (customerRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(customerRepository.findAll());
    }

    public ResponseEntity<Customer> getCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return ResponseEntity.status(200).body(customer);
    }

    public ResponseEntity<Customer> putCustomer(UUID id, UpdateCustomerData data) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
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
        throw new CustomerNotFoundException("Customer not found");
    }

    public ResponseEntity<Favorite> toggleFavoriteEstablishment(UUID idCustomer, UUID idEstablishment) {
        Customer customer = getCustomer(idCustomer).getBody();
        Establishment establishment = establishmentService.getEstablishment(idEstablishment).getBody();

        Favorite favoriteFound = favoriteRepository.findByIdCustomerAndIdEstablishment(idCustomer, idEstablishment);

        if (favoriteFound == null) {
            Favorite favorite = new Favorite(customer.getIdUser(), establishment.getIdUser());
            Favorite saved = favoriteRepository.save(favorite);
            customer.addFavorite(saved);
            return ResponseEntity.status(201).body(saved);
        }

        customer.getFavorites().remove(favoriteFound);
        favoriteRepository.delete(favoriteFound);
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(UUID idCustomer) {
        Customer customer = getCustomer(idCustomer).getBody();
        Double customerAvgRate = rateRepository.getAvgIndicatorCustomer(idCustomer);
        long customerQtdComments = commentRepository.countByIdCustomer(idCustomer);
        List<Comment> comments = commentRepository.findTop4ByIdCustomer(idCustomer).orElse(new ArrayList<>());
        List<Favorite> favorites = favoriteRepository.findTop4ByIdCustomer(idCustomer).orElse(new ArrayList<>());
        List<EstablishmentDTO> favoriteEstablishments = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(comment -> {
            String establishmentName = establishmentService.getEstablishment(comment.getIdEstablishment()).getBody().getEstablishmentName();
            CommentDTO commentDTO = new CommentDTO(establishmentName, "Teste", comment.getComment(), 10.0);
            commentDTOS.add(commentDTO);
        });

        favorites.forEach(favorite -> {
            Establishment establishment = establishmentService.getEstablishment(favorite.getIdEstablishment()).getBody();
            EstablishmentDTO establishmentDTO = new EstablishmentDTO(establishment.getIdUser(), establishment.getEstablishmentName(), establishment.getGeneralRate(), establishment.getCulinary(), establishment.getProfilePhoto());
            favoriteEstablishments.add(establishmentDTO);
        });

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customer.getName(), customer.getProfilePhoto(), customer.getProfileHeaderImg(), customer.getBio(), 0, customerAvgRate, 0, customerQtdComments, commentDTOS, favoriteEstablishments);
        return ResponseEntity.status(200).body(customerProfileDTO);
    }

    public ResponseEntity<Customer> patchCustomerProfile(UUID id, UpdateCustomerProfile customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(customer.email());
        userLoginDto.setPassword(customer.password());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);
        Customer custumerToUpdate = customerOptional.get();
        custumerToUpdate.updateProfile(Optional.ofNullable(customer));

        if (userTokenDtoResponseEntity.getStatusCodeValue() == 200) {
            return ResponseEntity.status(200).body(customerRepository.save(custumerToUpdate));
        }
        return ResponseEntity.status(401).build();
    }

    public ResponseEntity<Customer> patchCustomerPersonalInfo(UUID id, UpdateCustomerPersonalInfo customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(customerOptional.get().getEmail());
        userLoginDto.setPassword(customer.password());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);
        Customer customerToUpdate = customerOptional.get();
        customerToUpdate.updatePersonalInfo(Optional.ofNullable(customer));
        if (userTokenDtoResponseEntity.getStatusCodeValue() == 200) {
            return ResponseEntity.status(200).body(customerRepository.save(customerToUpdate));
        } else {
            System.out.println("Erro ao atualizar");
        }
        return ResponseEntity.status(401).build();
    }


    public ResponseEntity<List<SearchCustomerDTO>> searchAllCustomers(String customerName) {

        List<Customer> customers = customerName != null ? customerRepository.findByNameContainsIgnoreCase(customerName) : customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No customers found");
        }
        List<SearchCustomerDTO> searchCustomerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            SearchCustomerDTO searchEstablishmentDTO = getSearchCustomerDTO(customer);
            searchCustomerDTOS.add(searchEstablishmentDTO);
        }

        return ResponseEntity.status(200).body(searchCustomerDTOS);
    }

    @NotNull
    private SearchCustomerDTO getSearchCustomerDTO(Customer customer) {
        int sizeCulinary = customer.getCulinary().size();
        final long countUpvotes = customerRepository.countByUpvoteList_IdCustomer(customer.getIdUser());
        Double customerAvgRate = rateRepository.getAvgIndicatorCustomer(customer.getIdUser());
        String culinary = null;
        if (sizeCulinary == 0 || customer.getCulinary().get(sizeCulinary-1).getName() == null) {
            culinary = "Nenhuma culinária";
        } else {
            culinary = customer.getCulinary().get(sizeCulinary-1).getName();
        }
        return new SearchCustomerDTO(
                customer.getIdUser(),
                customer.getName(),
                customer.getTypeUser(),
                culinary,
                customerAvgRate,
                customer.getBio(),
                countUpvotes,
                customer.getProfilePhoto());
    }
}
