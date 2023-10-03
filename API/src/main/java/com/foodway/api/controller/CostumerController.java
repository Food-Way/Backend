package com.foodway.api.controller;


import com.foodway.api.exceptions.CostumerNotFoundException;
import com.foodway.api.model.Costumer;
import com.foodway.api.record.RequestUserCostumer;
import com.foodway.api.record.UpdateCostumerData;
import com.foodway.api.service.costumer.CostumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/costumers")
@Tag(name = "Costumer", description = "Costumer API")
public class CostumerController {

    @Autowired
    private CostumerService costumerService;

    @GetMapping
    @Operation(summary = "Get all costumers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all costumers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Costumer>> getCostumer(){
        return costumerService.getConsumers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get costumer by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = CostumerNotFoundException.CODE, description = CostumerNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Costumer> findCostumer(@PathVariable UUID id){
        return costumerService.getCostumer(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update costumer by ID", method = "PUT")
    public ResponseEntity<Costumer> putConstumer(@PathVariable UUID id, @RequestBody @Validated UpdateCostumerData costumer){
        return costumerService.putCostumer(id, costumer);
    }

    @PostMapping
    @Operation(summary = "Create a new costumer", method = "POST")
    public ResponseEntity<Costumer> saveCostumer(@RequestBody @Validated RequestUserCostumer costumer){
        return costumerService.saveCostumer(costumer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a costumer by ID", method = "DELETE")
    public ResponseEntity deleteCostumer(@PathVariable UUID id){
        return costumerService.deleteCostumer(id);
    }
}
