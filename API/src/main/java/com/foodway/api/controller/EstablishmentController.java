package com.foodway.api.controller;

import com.foodway.api.exceptions.EstablishmentNotFoundException;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.utils.ListaObj;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a establishment by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a establishment"),
            @ApiResponse(responseCode = EstablishmentNotFoundException.CODE, description = EstablishmentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Establishment> getEstablishment(@PathVariable UUID id) {
        return establishmentService.getEstablishment(id);
    }

    @GetMapping
    @Operation(summary = "Get all establishments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all establishments"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Establishment>> getEstablishments() {
        return establishmentService.getEstablishments();
    }

//    @GetMapping("/best")
//    public ResponseEntity<List<Establishment>> getBestEstablishments(@Nullable @RequestParam String culinary) {
//        if (culinary == null) {
//            return establishmentService.getBestEstablishments();
//        }
//        return establishmentService.getBestEstablishmentsByCulinary(culinary);
//    }

    @GetMapping("/culinary/{id}")
    @Operation(summary = "Get all establishments by culinary", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a establishment by culinary"),
            @ApiResponse(responseCode = EstablishmentNotFoundException.CODE, description = EstablishmentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Establishment>> getEstablishmentsByCulinary(@PathVariable int idCulinary) {
        return establishmentService.getEstablishmentsByCulinary(idCulinary);
    }


    @GetMapping("/most-commented")
    @Operation(summary = "Get most commented establishment", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a most commented establishment"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Establishment>> getMoreCommentedEstablishments(@Nullable @RequestParam String culinary) {
        if (culinary == null) {
            return establishmentService.getMoreCommentedEstablishments();
        }
        return establishmentService.getMoreCommentedEstablishmentsByCulinary(culinary);
    }

    @GetMapping("/order-by-greater-rate")
    @Operation(summary = "Order establishments by greater-rate", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return ordered establishments by greater-rate"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ListaObj<Establishment>> getEstablishmentsOrderByRate() {
        return establishmentService.getEstablishmentOrderByRate();
    }

    @GetMapping("/search-rate")
    @Operation(summary = "Search establishment by a rate", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a establishment by rate"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity getBinarySearch(@RequestParam Double rate) {
        return establishmentService.getBinarySearch(rate);
    }

    @GetMapping("/export")
    @Operation(summary = "Export all establishments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all establishments exported"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ListaObj<Establishment>> exportEstablishments(@RequestParam String archiveType) {
        return establishmentService.exportEstablishments(archiveType);
    }

    @GetMapping("/import")
    @Operation(summary = "Import all establishments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all establishments imported"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ListaObj<Establishment>> importEstablishments(@RequestParam String archiveType) {
        return establishmentService.importEstablishments(archiveType);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete establishment by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted establishment"),
            @ApiResponse(responseCode = EstablishmentNotFoundException.CODE, description = EstablishmentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Establishment> deleteEstablishment(@PathVariable UUID id) {
        return establishmentService.deleteEstablishment(id);
    }

    @PostMapping
    @Operation(summary = "Create a new establishment", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created establishment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
        public ResponseEntity<Establishment> postEstablishment(@RequestBody @Validated RequestUserEstablishment establishment) {
        return establishmentService.saveEstablishment(establishment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update establsihment by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated establishment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = EstablishmentNotFoundException.CODE, description = EstablishmentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Establishment> putEstablishment(@PathVariable UUID id, @RequestBody @Validated UpdateEstablishmentData establishment) {
        return establishmentService.putEstablishment(id, establishment);
    }

//    @PostMapping("/t")
//    public ResponseEntity<List<Estabelecimento>> postEstabelecimentos(@RequestBody List<Estabelecimento> estabelecimentos){
//        if(estabelecimentos.isEmpty())  return ResponseEntity.status(204).build();
//
//        return ResponseEntity.status(201).body(estabelecimentoRepository.saveAll(estabelecimentos));
//    }

}