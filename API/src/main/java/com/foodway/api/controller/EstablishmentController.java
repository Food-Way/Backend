package com.foodway.api.controller;

import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.utils.ListaObj;
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
    public ResponseEntity<Establishment> getEstablishment(@PathVariable UUID id) {
        return establishmentService.getEstablishment(id);
    }

    @GetMapping
    public ResponseEntity<List<Establishment>> getEstablishments() {
        return establishmentService.getEstablishments();
    }

    @GetMapping("/best")
    public ResponseEntity<List<Establishment>> getBestEstablishments(@Nullable @RequestParam String culinary) {
        if (culinary == null) {
            return establishmentService.getBestEstablishments();
        }
        return establishmentService.getBestEstablishmentsByCulinary(culinary);
    }

    @GetMapping("/more-commented")
    public ResponseEntity<List<Establishment>> getMoreCommentedEstablishments(@Nullable @RequestParam String culinary) {
        if (culinary == null) {
            return establishmentService.getMoreCommentedEstablishments();
        }
        return establishmentService.getMoreCommentedEstablishmentsByCulinary(culinary);
    }

    @GetMapping("/order-by-greater-rate")
    public ResponseEntity<ListaObj<Establishment>> getEstablishmentsOrderByRate() {
        return establishmentService.getEstablishmentOrderByRate();
    }

    @GetMapping("/search-rate")
    public ResponseEntity getBinarySearch(@RequestParam Double rate) {
        return establishmentService.getBinarySearch(rate);
    }

    @GetMapping("/export")
    public ResponseEntity<ListaObj<Establishment>> exportEstablishments(@RequestParam String archiveType) {
        return establishmentService.exportEstablishments(archiveType);
    }

    @GetMapping("/import")
    public ResponseEntity<ListaObj<Establishment>> importEstablishments(@RequestParam String archiveType) {
        return establishmentService.importEstablishments(archiveType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Establishment> deleteEstablishment(@PathVariable UUID id) {
        return establishmentService.deleteEstablishment(id);
    }

    @PostMapping
    public ResponseEntity<Establishment> postEstablishment(@RequestBody @Validated RequestUserEstablishment establishment) {
        return establishmentService.saveEstablishment(establishment);
    }

    @PutMapping("/{id}")
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