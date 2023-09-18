package com.foodway.api.service.comment;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Costumer;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EstablishmentService establishmentService;

//    public ResponseEntity postComment(UUID idUser, RequestComment data) {
//        Comment comment = new Comment(data);
//
//        return establishmentService.postComment(idUser, comment);
//    }

    public ResponseEntity<List<Comment>> getComments(UUID id) {
        if (commentRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(commentRepository.findAll());
    }

    public ResponseEntity deleteComment(UUID id, UUID idOwner) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity putComment(UUID id, UpdateCommentData data) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        comment.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(commentRepository.save(comment.get()));
    }
}
