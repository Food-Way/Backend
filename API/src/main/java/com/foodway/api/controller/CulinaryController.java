package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.CulinaryNotFoundException;
import com.foodway.api.model.Culinary;
import com.foodway.api.record.RequestCulinary;
import com.foodway.api.service.culinary.CulinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/culinaries")
@Tag(name = "Culinary")
public class CulinaryController {

    @Autowired
    private CulinaryService culinaryService;

    @GetMapping
    @Operation(summary = "Get all culinaries", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all culinaries"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Culinary>> getCulinaries(){
        return culinaryService.getCulinaries();
    }

    @PostMapping
    @Operation(summary = "Create a new culinary", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created culinary"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Culinary> saveCulinary(@RequestBody @Validated RequestCulinary culinary){
        return culinaryService.saveCulinary(culinary);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update culinary by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated culinary"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = CulinaryNotFoundException.CODE, description = CulinaryNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Culinary> putCulinary(@PathVariable int id, @RequestBody @Validated RequestCulinary culinary){
        return culinaryService.putCulinary(id, culinary);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete culinary by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted culinary"),
            @ApiResponse(responseCode = CulinaryNotFoundException.CODE, description = CulinaryNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Culinary> deleteCulinary(@PathVariable int id){
        return culinaryService.deleteCulinary(id);
    }
}