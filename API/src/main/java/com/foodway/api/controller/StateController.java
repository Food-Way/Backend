package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.RateNotFoundException;
import com.foodway.api.handler.exceptions.StateNotFoundException;
import com.foodway.api.model.Rate;
import com.foodway.api.model.State;
import com.foodway.api.record.RequestRate;
import com.foodway.api.record.RequestState;
import com.foodway.api.service.state.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@Tag(name = "State")
public class StateController {
    @Autowired
    StateService stateService;

    @GetMapping
    @Operation(summary = "Get all states", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all states"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<State>> getAll(){
        return stateService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get state by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a state by ID"),
            @ApiResponse(responseCode = StateNotFoundException.CODE, description = StateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<State> get(@PathVariable int id){
        return stateService.get(id);
    }

    @PostMapping
    @Operation(summary = "post a new state", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the posted state"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<State> post(@RequestBody @Valid RequestState state) {
        return stateService.post(state);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update state by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the state updated by id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = StateNotFoundException.CODE, description = StateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<State> put(@PathVariable int id, @RequestBody @Valid RequestState state){
        return stateService.put(id, state);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete state by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the state deleted by id"),
            @ApiResponse(responseCode = StateNotFoundException.CODE, description = StateNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@PathVariable int id){
        return stateService.delete(id);
    }
}
