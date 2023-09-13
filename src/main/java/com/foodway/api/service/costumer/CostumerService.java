package com.foodway.api.service.costumer;

import com.foodway.api.model.Costumer;
import com.foodway.api.record.RequestUserCostumer;
import com.foodway.api.record.UpdateCostumerData;
import com.foodway.api.repository.CostumerRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CostumerService {
    @Autowired
    CostumerRepository costumerRepository;

    public ResponseEntity<List<Costumer>> getConsumers() {
        if(costumerRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(costumerRepository.findAll());
    }

    public ResponseEntity<Costumer> getCostumer(UUID id) {
        Optional<Costumer> costumer = costumerRepository.findById(id);
        return costumer.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity<Costumer> putCostumer(UUID id, UpdateCostumerData data) {
        Optional<Costumer> costumerOptional = costumerRepository.findById(id);
        if(costumerOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        costumerOptional.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(costumerRepository.save(costumerOptional.get()));
    }

    public ResponseEntity<Costumer> saveCostumer(RequestUserCostumer costumer) {
        Costumer createdCostumer = new Costumer(costumer);
        return ResponseEntity.status(201).body(costumerRepository.save(createdCostumer));
    }

    public ResponseEntity<Costumer> deleteCostumer(UUID id) {
        Optional<Costumer> costumer = costumerRepository.findById(id);
        if(costumer.isPresent()){
            costumerRepository.delete(costumer.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

}
