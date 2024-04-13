package com.foodway.api.service.tag;

import com.foodway.api.model.Establishment;
import com.foodway.api.model.Tags;
import com.foodway.api.record.RequestTag;
import com.foodway.api.record.UpdateTag;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;


    public ResponseEntity<Set<Tags>> addTagEstablishment(UUID idEstablishments, Set<Long> tags){
        Optional<Establishment> establishment = establishmentRepository.findById(idEstablishments);
        List<Tags> tagsToInsert = tagRepository.findAllById(tags);
        if(establishment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found!");
        }
            Set<Tags> tagsSet = new HashSet<>(tagsToInsert);
            establishment.get().setTags(tagsSet);
            establishmentRepository.save(establishment.get());
            return ResponseEntity.status(200).body(tagsSet);
    }
    public ResponseEntity<List<Tags>> getAll() {
        List<Tags> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Tag is empty!");
        }
        return ResponseEntity.status(200).body(tags);
    }

    public ResponseEntity<Optional<Tags>> get(Long idTag) {
        Optional<Tags> tag = tagRepository.findById(idTag);
        if (tag.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }
        return ResponseEntity.status(200).body(tag);
    }

    public ResponseEntity<Tags> post(RequestTag requestTag) {
        Tags tag = new Tags(requestTag);
        tagRepository.save(tag);
        return ResponseEntity.status(201).body(tag);
    }

    public ResponseEntity<Tags> delete(Long idTag) {
        Optional<Tags> t = tagRepository.findById(idTag);
        if (t.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }
          Tags tag = t.get();
        tagRepository.deleteById(idTag);
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<Tags> put(Long idTag, UpdateTag updateTag) {
        Optional<Tags> t = tagRepository.findById(idTag);
        if (t.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }
        Tags tag = t.get();
        tag.setName(updateTag.name());
        return ResponseEntity.status(200).body(tagRepository.save(tag));
    }
}
