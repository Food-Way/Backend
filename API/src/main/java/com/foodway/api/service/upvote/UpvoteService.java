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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpvoteService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private UpvoteRepository upvoteRepository;

    public ResponseEntity<List<Upvote>> getAll() {
        List<Upvote> upvotes = upvoteRepository.findAll();
        if (upvotes.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "List of Upvotes is empty");
        return ResponseEntity.status(200).body(upvotes);
    }

    public ResponseEntity<Upvote> get(long id) {
        Optional<Upvote> upvote = upvoteRepository.findById(id);
        if (upvote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Upvote not found.");
        }
        return ResponseEntity.status(200).body(upvote.get());
    }

    public ResponseEntity<Upvote> patch(RequestUpvote data) {
        Upvote upvote = new Upvote(data);
        final Comment comment = commentRepository.findById(data.idComment()).get();
        boolean existsUpvote = upvoteRepository.existsByIdCommentAndIdCustomer(upvote.getIdComment(), upvote.getIdCustomer());

        if (existsUpvote) {
            comment.getUpvoteList().remove(upvote);
            upvoteRepository.delete(upvote);
            return ResponseEntity.status(200).body(upvote);
        }

        comment.getUpvoteList().add(upvote);
        upvoteRepository.save(upvote);
        return ResponseEntity.status(201).body(upvote);
    }
}
