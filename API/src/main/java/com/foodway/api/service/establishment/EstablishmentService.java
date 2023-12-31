package com.foodway.api.service.establishment;

import com.foodway.api.model.Enums.EEntity;
import com.foodway.api.model.Establishment;
//import com.foodway.api.model.MapsClient;
import com.foodway.api.model.MapsClient;
import com.foodway.api.record.DTOs.MapsLongLag;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.utils.ListaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.foodway.api.utils.GerenciadorDeArquivo.*;

@Service
public class EstablishmentService {

    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private MapsClient mapsClient;

    public ResponseEntity<List<Establishment>> validateIsEmpty(List<Establishment> establishments) {
        if (establishments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(establishments);
    }

    public ResponseEntity<List<Establishment>> getEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAll();
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<List<Establishment>> getBestEstablishments() {
        List<Establishment> establishments = establishmentRepository.findTop3ByOrderByRateDesc();
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<List<Establishment>> getBestEstablishmentsByCulinary(String culinary) {
        List<Establishment> establishments = establishmentRepository.findTop3ByCulinary_NameOrderByRateDesc(culinary);
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<List<Establishment>> getMoreCommentedEstablishments() {
        List<Establishment> establishments = establishmentRepository.findByOrderByPostListDesc();
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<List<Establishment>> getMoreCommentedEstablishmentsByCulinary(String culinary) {
        List<Establishment> establishments = establishmentRepository.findByCulinary_NameOrderByPostListDesc(culinary);
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<ListaObj<Establishment>> getEstablishmentOrderByRate() {
        List<Establishment> establishments = getEstablishments().getBody();
        ListaObj<Establishment> list = new ListaObj<>(establishments.size(), establishments);
        return ResponseEntity.status(200).body(list.filterBySome(list, "rate", EEntity.ESTABLISHMENT));
    }

    public ResponseEntity<Establishment> getEstablishment(UUID paramId) {
        Optional<Establishment> establishment = establishmentRepository.findById(paramId);
        return establishment.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity getBinarySearch(Double rate) {
        ListaObj<Establishment> list = getEstablishmentOrderByRate().getBody();
        return ResponseEntity.status(200).body(list.findByRate(rate));
    }

    public ResponseEntity<Establishment> deleteEstablishment(UUID id) {
        Optional<Establishment> establishment = establishmentRepository.findById(id);
        if (establishment.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        establishmentRepository.delete(establishment.get());
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<Establishment> saveEstablishment(RequestUserEstablishment establishmentRequest) {
        Establishment establishment = new Establishment(establishmentRequest);
        RequestUserEstablishment.Address address = establishmentRequest.address();
        MapsLongLag mapsLongLag = mapsClient.getLongLat(address.number(), address.street(), address.city(), "AIzaSyAzEwtZ4fQ-3qu6McrI5MoleuC8PNJ3F4w");
        establishment.getAddress().setLatitude(mapsLongLag.results().get(0).geometry().location().lat());
        establishment.getAddress().setLongitude(mapsLongLag.results().get(0).geometry().location().lng());
        System.out.println(establishment);
        Establishment establishmentSaved = establishmentRepository.save(establishment);
        return ResponseEntity.status(201).body(establishmentSaved);
    }

    public ResponseEntity<Establishment> putEstablishment(UUID id, UpdateEstablishmentData data) {
        ResponseEntity<Establishment> establishment1 = getEstablishment(id);
        Establishment establishment = establishment1.getBody();
        establishment.update(Optional.of(data));
        return ResponseEntity.status(200).body(establishmentRepository.save(establishment));
    }

    public ResponseEntity<ListaObj<Establishment>> exportEstablishments(String archiveType) {
        List<Establishment> establishments = getEstablishments().getBody();
        ListaObj<Establishment> listaObjEstablishments = new ListaObj<>(establishments.size(), establishments);
        if (archiveType.equals("csv")) {
            gravaArquivoCsv(listaObjEstablishments, "establishments");
            return ResponseEntity.ok().build();
        } else if (archiveType.equals("txt")) {
            gravaArquivoTxt(establishments, "establishments.txt");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<ListaObj<Establishment>> importEstablishments(String archiveType) {
        if (archiveType.equals("csv")) {
            leArquivoCsv("establishments");
            return ResponseEntity.ok().build();
        } else if (archiveType.equals("txt")) {
            leArquivoTxt("establishments.txt");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
