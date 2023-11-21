package com.foodway.api.service.upvote;

import com.foodway.api.model.Upvote;
import com.foodway.api.record.RequestUpvote;
import com.foodway.api.repository.UpvoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UpvoteService {
    @Autowired
    private UpvoteRepository upvoteRepository;

    public ResponseEntity<List<Upvote>> getAll() {
        return upvoteRepository.findAll().isEmpty() ?
                ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(upvoteRepository.findAll());
    }

    public ResponseEntity<Upvote> get(long id) {
        if(!upvoteRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        Optional<Upvote> upvote = upvoteRepository.findById(id);

        return ResponseEntity.status(200).body(upvote.get());
    }

    public ResponseEntity<Upvote> post(RequestUpvote data) {
        Upvote upvote = new Upvote(data);
        if(existUpvote(upvote)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upvote already exist");
        }
        upvote.setIdComment(data.idComment());
        upvote.setIdEstablishment(data.idEstablishment());
        upvote.setIdCustomer(data.idCustomer());
        return ResponseEntity.status(200).body(upvoteRepository.save(upvote));
    }

    public ResponseEntity<Upvote> put(long id, RequestUpvote data) {
        if(!upvoteRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        Upvote upvote = upvoteRepository.findById(id).get();
        upvote.update(data);
        return ResponseEntity.status(200).body(upvoteRepository.save(upvote));
    }

    public ResponseEntity<Void> delete(long id) {
        if(!upvoteRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }
        upvoteRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    public boolean existUpvote(Upvote upvote){
        List<Upvote> upvotes = upvoteRepository.findAll();
        if(upvotes.isEmpty()) {
            return false;
        }
        for (Upvote u : upvotes) {
            if(u.getIdComment().equals(upvote.getIdComment()) && u.getIdCustomer().equals(upvote.getIdCustomer())){
                return true;
            }
        }
        return false;
    }
}
