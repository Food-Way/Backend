package com.foodway.api.controller;

import com.foodway.api.record.RequestTag;
import com.foodway.api.record.UpdateTag;
import com.foodway.api.service.tag.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/tags")
@Tag(name = "Tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping()
    public ResponseEntity<List<com.foodway.api.model.Tag>> getAll() {
        return tagService.getAll();
    }

    @GetMapping("/{idTag}")
    public ResponseEntity<Optional<com.foodway.api.model.Tag>> get(@PathVariable Long idTag) {
        return tagService.get(idTag);
    }

    @PostMapping()
    public ResponseEntity<com.foodway.api.model.Tag> post(@RequestBody RequestTag requestTag) {
        return tagService.post(requestTag);
    }

    @PutMapping("/{idTag}")
    public ResponseEntity<com.foodway.api.model.Tag> put(@PathVariable Long idTag, @RequestBody UpdateTag updateTag) {
        return tagService.put(idTag, updateTag);
    }

    @DeleteMapping("/{idTag}")
    public ResponseEntity<com.foodway.api.model.Tag> delete(@PathVariable Long idTag) {
        return tagService.delete(idTag);
    }
}
