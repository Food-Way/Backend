package com.foodway.api.controller;


import com.foodway.api.record.RequestTag;
import com.foodway.api.record.RequestTagEstablishment;
import com.foodway.api.record.RequestTagTeste;
import com.foodway.api.record.UpdateTag;
import com.foodway.api.service.tag.TagService;
import com.foodway.api.model.Tags;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/tags")
@Tag(name = "Tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping()
    public ResponseEntity<List<Tags>> getAll() {
        return tagService.getAll();
    }

    @GetMapping("/{idTag}")
    public ResponseEntity<Optional<Tags>> get(@PathVariable Long idTag) {
        return tagService.get(idTag);
    }

    @PostMapping()
    public ResponseEntity<Tags> post(@RequestBody RequestTag requestTag) {
        return tagService.post(requestTag);
    }
    @PostMapping("establishment")
    public ResponseEntity<Set<Tags>> addTagToEstablishment(@RequestBody RequestTagEstablishment requestTagEstablishment){
        return tagService.addTagEstablishment(requestTagEstablishment.establishment(),requestTagEstablishment.tags());
    }


    @PutMapping("/{idTag}")
    public ResponseEntity<Tags> put(@PathVariable Long idTag, @RequestBody UpdateTag updateTag) {
        return tagService.put(idTag, updateTag);
    }

    @DeleteMapping("/{idTag}")
    public ResponseEntity<Tags> delete(@PathVariable Long idTag) {
        return tagService.delete(idTag);
    }
}
