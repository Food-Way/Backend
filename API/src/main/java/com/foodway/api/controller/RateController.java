package com.foodway.api.controller;

import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.service.rate.RateService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<Rate>> getAll(){
        return rateService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Rate> get(@PathVariable Long id){
        return rateService.get(id);
    }
    @PostMapping
    public ResponseEntity<Rate> post(@RequestBody @Validated RequestRate rate){
        return rateService.post(rate);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rate> put(@PathVariable Long id, @RequestBody @Validated RequestRate rate){
        return rateService.put(id, rate);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return rateService.delete(id);
    }
}
