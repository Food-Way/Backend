package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.RateNotFoundException;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.service.rate.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rates")
@Tag(name = "Rate")
public class RateController {

    @Autowired
    RateService rateService;

    @GetMapping
    @Operation(summary = "Get all rates", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all rates"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Rate>> getAll(){
        return rateService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rate by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a rate by ID"),
            @ApiResponse(responseCode = RateNotFoundException.CODE, description = RateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rate> get(@PathVariable Long id){
        return rateService.get(id);
    }

    @PostMapping
    @Operation(summary = "post a new rate", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the posted rate"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Rate> post(@RequestBody @Valid RequestRate rate){
        return rateService.post(rate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rate by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the rate customer"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = RateNotFoundException.CODE, description = RateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rate> put(@PathVariable Long id, @RequestBody @Valid RequestRate rate){
        return rateService.put(id, rate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rate by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the rate customer"),
            @ApiResponse(responseCode = RateNotFoundException.CODE, description = RateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return rateService.delete(id);
    }
}