package com.foodway.api.service.establishment;

import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Establishment;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EstablishmentServiceTest {

    @Mock
    private EstablishmentRepository establishmentRepository;
    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    EstablishmentService establishmentService;

    @Test
    void should_return_all_establishments() {
        when(establishmentRepository.findAll()).thenReturn(List.of(new Establishment(), new Establishment()));

        List<Establishment> establishments = establishmentService.getEstablishments().getBody();

        assertNotNull(establishments);
        assertEquals(2, establishments.size());
    }

    @Test
    void should_throw_ResponseStatusException_when_findAll_is_empty() {
        when(establishmentRepository.findAll()).thenReturn(Collections.emptyList());
        when(establishmentRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> establishmentService.getEstablishments().getBody());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
        assertEquals("No content", exception.getReason());
    }

    @Test
    void getMoreCommentedEstablishments() {
    }

    @Test
    void should_return_establishment_by_id() {
        UUID uuid = UUID.randomUUID();
        Establishment establishment = new Establishment();
        establishment.setIdUser(uuid);
        when(establishmentRepository.findById(uuid)).thenReturn(Optional.of(establishment));
        Establishment establishmentById = establishmentService.getEstablishment(uuid).getBody();
        assertEquals(uuid, establishmentById.getIdUser());
        assertNotNull(establishmentById);
        assertEquals(establishment, establishmentById);
    }
}