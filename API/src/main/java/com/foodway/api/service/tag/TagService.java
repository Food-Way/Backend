package com.foodway.api.service.tag;

import com.foodway.api.model.Establishment;
import com.foodway.api.model.Tag;
import com.foodway.api.record.RequestTag;
import com.foodway.api.record.UpdateTag;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;

    public ResponseEntity<List<Tag>> getAll() {
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Tag is empty!");
        }

        return ResponseEntity.status(200).body(tags);
    }

    public ResponseEntity<Optional<Tag>> get(Long idTag) {
        Optional<Tag> tag = tagRepository.findById(idTag);
        if (!tag.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }
        return ResponseEntity.status(200).body(tag);
    }

    public ResponseEntity<Tag> post(RequestTag requestTag) {
        Tag tag = new Tag(requestTag);
        Optional<Establishment> e = establishmentRepository.findById(tag.getIdEstablishment());
        if (!e.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not exist!");
        }
        Establishment establishment = e.get();
        establishment.getTags().forEach(t -> {
            if(t.getName().equals(tag.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag already does exist!");
            }
        });
        establishment.addTags(tag);
        tag.setIdEstablishment(establishment.getIdUser());
        tagRepository.save(tag);
        return ResponseEntity.status(201).body(tag);
    }

    public ResponseEntity<Tag> delete(Long idTag) {
        Optional<Tag> t = tagRepository.findById(idTag);
        Optional<Establishment> establishment = establishmentRepository.findById(t.get().getIdEstablishment());
        if (!t.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }

        Tag tag = t.get();
        establishment.get().getTags().remove(tag);
        tagRepository.deleteById(idTag);
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<Tag> put(Long idTag, UpdateTag updateTag) {
        Optional<Tag> t = tagRepository.findById(idTag);
        if (!t.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found!");
        }
        Tag tag = t.get();
        tag.setName(updateTag.name());
        tag.setEnable(updateTag.enable());

        return ResponseEntity.status(200).body(tagRepository.save(tag));
    }
}
