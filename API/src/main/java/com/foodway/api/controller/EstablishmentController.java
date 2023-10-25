package com.foodway.api.controller;

import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.utils.ListaObj;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/establishments")
public class EstablishmentController {
    @Autowired
    private EstablishmentService establishmentService;

    @GetMapping
    public ResponseEntity<List<Establishment>> getEstablishments() {
        return establishmentService.getEstablishment();
    }

    @GetMapping("/order-by-greater-rate")
    public ResponseEntity<ListaObj<Establishment>> getEstablishmentsOrderByRate() {
        return establishmentService.getEstablishmentOrderByRate();
    }

    @GetMapping("/search-rate")
    public ResponseEntity<Establishment> getBinarySearch(@RequestParam Double rate) {
        return establishmentService.getBinarySearch(rate);
    }

    @GetMapping("/export")
    public ResponseEntity<ListaObj<Establishment>> exportEstablishments(@RequestParam String archiveType) {
        if (archiveType.equals("csv")) {
            return establishmentService.exportEstablishmentsCsv();
        } else if (archiveType.equals("txt")) {
            return establishmentService.exportEstablishmentsTxt();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/import")
    public ResponseEntity<ListaObj<Establishment>> importEstablishments(@RequestParam String archiveType) {
        if (archiveType.equals("csv")) {
            return establishmentService.importEstablishmentsCsv();
        } else if (archiveType.equals("txt")) {
            return establishmentService.importEstablishmentTxt();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establishment> getEstablishment(@PathVariable UUID id) {
        return establishmentService.getEstablishment(id);
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