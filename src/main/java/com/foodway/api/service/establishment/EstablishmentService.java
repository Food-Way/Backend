package com.foodway.api.service.establishment;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.repository.EstablishmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EstablishmentService {

    private EstablishmentRepository establishmentRepository;

    public ResponseEntity<List<Establishment>> getEstablishment() {
        if(establishmentRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(establishmentRepository.findAll());
    }
    public ResponseEntity<Establishment> getEstablishment(UUID paramId) {
        Optional<Establishment> establishment = establishmentRepository.findById(paramId);
        return establishment.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity<Establishment> deleteEstablishment(UUID id) {
        Optional<Establishment> establishment = establishmentRepository.findById(id);
         if(establishment.isEmpty()){
             return ResponseEntity.status(404).build();
         }
         establishmentRepository.delete(establishment.get());
         return ResponseEntity.status(204).build();
    }

    public ResponseEntity<Establishment> saveEstablishment(RequestUserEstablishment establishment) {
        Establishment createdEstablishment = new Establishment(establishment);
        return ResponseEntity.status(201).body(establishmentRepository.save(createdEstablishment));
    }

    public ResponseEntity<Establishment> putEstablishment(UUID id, UpdateEstablishmentData data) {
        Optional<Establishment> establishmentOptional = establishmentRepository.findById(id);
        if(establishmentOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        establishmentOptional.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(establishmentRepository.save(establishmentOptional.get()));
    }

//    public ResponseEntity postComment(UUID idUser, Comment comment){
//            Optional<Establishment> establishment = establishmentRepository.findById(idUser);
//            if (establishment.isEmpty()){
//                return ResponseEntity.status(404).build();
//            }
//
//
//            Establishment teste = establishment.stream().findFirst().get();
//            teste.addComment(comment);
//
//            return ResponseEntity.status(201).body(comment);
//    }
}
