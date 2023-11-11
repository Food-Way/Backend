package com.foodway.api.service.culinary;

import com.foodway.api.model.Culinary;
import com.foodway.api.record.RequestCulinary;
import com.foodway.api.repository.CulinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CulinaryService {
    
    @Autowired
    CulinaryRepository culinaryRepository;

    public ResponseEntity<List<Culinary>> getCulinaries() {
        if(culinaryRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(culinaryRepository.findAll());
    }

    public ResponseEntity<Culinary> saveCulinary(RequestCulinary culinary) {
        Culinary createdCulinary = new Culinary(culinary);
        return ResponseEntity.status(201).body(culinaryRepository.save(createdCulinary));

    }

    public ResponseEntity<Culinary> putCulinary(int id, RequestCulinary culinary) {
        Optional<Culinary> culinaryOptional = culinaryRepository.findById(id);
        if(culinaryOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        culinaryOptional.get().update(Optional.ofNullable(culinary));
        return ResponseEntity.status(200).body(culinaryRepository.save(culinaryOptional.get()));
    }

    public ResponseEntity<Culinary> deleteCulinary(int id) {
        Optional<Culinary> culinary = culinaryRepository.findById(id);
        if(culinary.isPresent()){
            culinaryRepository.delete(culinary.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
