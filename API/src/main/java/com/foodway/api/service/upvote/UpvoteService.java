package com.foodway.api.service.upvote;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Upvote;
import com.foodway.api.record.RequestUpvote;
import com.foodway.api.repository.CommentRepository;
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
    @Autowired
    CommentRepository commentRepository;

    public ResponseEntity<List<Upvote>> getAll() {
        List<Upvote> upvotes = upvoteRepository.findAll();
        if (upvotes.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "List of Upvotes is empty");
        return ResponseEntity.status(200).body(upvotes);
    }

    public ResponseEntity<Upvote> get(long id) {
        Optional<Upvote> upvote = upvoteRepository.findById(id);
        if(upvote.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Upvote not found.");
        }
        return ResponseEntity.status(200).body(upvote.get());
    }

    public ResponseEntity<Upvote> post(RequestUpvote data) {
        Upvote upvote = new Upvote(data);
        final Comment comment = commentRepository.findById(data.idComment()).get();
        if(existUpvote(upvote)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upvote already exist");
        }
        comment.addUpvote(upvote);
        upvote.setIdComment(data.idComment());
        upvote.setIdEstablishment(data.idEstablishment());
        upvote.setIdCustomer(data.idCustomer());
        upvoteRepository.save(upvote);

        return ResponseEntity.status(200).body(upvote);
    }

    public ResponseEntity<Upvote> put(long id, RequestUpvote data) {
        if(!upvoteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Upvote not found");
        }

        Upvote upvote = upvoteRepository.findById(id).get();
        upvote.update(data);
        upvoteRepository.save(upvote);
        return ResponseEntity.status(200).body(upvote);
    }

    public ResponseEntity<Void> delete(long id) {
        if(!upvoteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Upvote not found");
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
