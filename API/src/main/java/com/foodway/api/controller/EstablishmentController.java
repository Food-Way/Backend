package com.foodway.api.controller;

import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.utils.ListaObj;
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

    @GetMapping
    public ResponseEntity<List<Establishment>> getEstablishments() {
        return establishmentService.getEstablishment();
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
    public ResponseEntity<ListaObj<Establishment>> importEstablishments() {
        return establishmentService.importEstablishments();
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
        System.out.println("Passei aqui");
        return establishmentService.putEstablishment(id, establishment);
    }
}

//    @PostMapping("/t")
//    public ResponseEntity<List<Estabelecimento>> postEstabelecimentos(@RequestBody List<Estabelecimento> estabelecimentos){
//        if(estabelecimentos.isEmpty())  return ResponseEntity.status(204).build();
//
//        return ResponseEntity.status(201).body(estabelecimentoRepository.saveAll(estabelecimentos));
//    }

