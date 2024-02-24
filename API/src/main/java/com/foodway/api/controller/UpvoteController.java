package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.UpvoteNotFoundException;
import com.foodway.api.model.Upvote;
import com.foodway.api.record.RequestUpvote;
import com.foodway.api.service.upvote.UpvoteService;
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
@RequestMapping("/upvotes")
@Tag(name = "Upvote")
public class UpvoteController {

    @Autowired
    UpvoteService upvoteService;

    @GetMapping
    @Operation(summary = "Get all upvotes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all upvotes"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Upvote>> getAll() {
        return upvoteService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get upvote by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return an upvote by ID"),
            @ApiResponse(responseCode = UpvoteNotFoundException.CODE, description = UpvoteNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Upvote> get(@PathVariable int id) {
        return upvoteService.get(id);
    }

    @PatchMapping
    @Operation(summary = "Post or delete an upvote", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the upvote toggled"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Upvote> patch(@RequestBody @Valid RequestUpvote data) {
        return upvoteService.patch(data);
    }

}
