package com.foodway.api.controller;

import com.foodway.api.model.Upvote;
import com.foodway.api.record.RequestUpvote;
import com.foodway.api.service.upvote.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upvotes")
public class UpvoteController {

    @Autowired
    UpvoteService upvoteService;

    @GetMapping
    public ResponseEntity<List<Upvote>> getAll(){
        return upvoteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Upvote> get(@PathVariable int id){
        return upvoteService.get(id);
    }
    @PostMapping
    public ResponseEntity<Upvote> post(@RequestBody RequestUpvote data){
        return upvoteService.post(data);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Upvote> put(@PathVariable int id, @RequestBody RequestUpvote data){
        return upvoteService.put(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        return upvoteService.delete(id);
    }
}
