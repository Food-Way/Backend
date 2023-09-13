package com.foodway.api.controller;


import com.foodway.api.model.Costumer;
import com.foodway.api.record.RequestUserCostumer;
import com.foodway.api.record.UpdateCostumerData;
import com.foodway.api.service.costumer.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private CostumerService costumerService;

    @GetMapping
    public ResponseEntity<List<Costumer>> getCostumer(){
        return costumerService.getConsumers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Costumer> findCostumer(@PathVariable UUID id){
        return costumerService.getCostumer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Costumer> putConstumer(@PathVariable UUID id, @RequestBody @Validated UpdateCostumerData costumer){
        return costumerService.putCostumer(id, costumer);
    }

    @PostMapping
    public ResponseEntity<Costumer> saveCostumer(@RequestBody @Validated RequestUserCostumer costumer){
        return costumerService.saveCostumer(costumer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCostumer(@PathVariable UUID id){
        return costumerService.deleteCostumer(id);
    }
}
