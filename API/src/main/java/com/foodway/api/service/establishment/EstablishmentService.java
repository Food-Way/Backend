package com.foodway.api.service.establishment;

import com.foodway.api.model.EEntity;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.MapsClient;
import com.foodway.api.model.MapsLongLag;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.utils.ListaObj;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.foodway.api.utils.GerenciadorDeArquivo.*;

@Service
@AllArgsConstructor
public class EstablishmentService {

    private EstablishmentRepository establishmentRepository;
    private MapsClient mapsClient;

    public ResponseEntity<List<Establishment>> validateIsEmpty(List<Establishment> establishments) {
        if (establishments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).build();
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

    public ResponseEntity<Establishment> saveEstablishment(RequestUserEstablishment establishment) {
        Establishment createdEstablishment = new Establishment(establishment);
        RequestUserEstablishment.Address address = establishment.address();
        MapsLongLag mapsLongLag = mapsClient.getLongLat(establishment.address().number(), establishment.address().street(), establishment.address().city(), "api.key");
        createdEstablishment.setLong(mapsLong.getLng());
        createdEstablishment.setLat(mapsLat.getLat());
        return ResponseEntity.status(201).body(establishmentRepository.save(createdEstablishment));
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
