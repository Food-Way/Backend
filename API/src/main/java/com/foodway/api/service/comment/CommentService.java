package com.foodway.api.service.comment;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    EstablishmentService establishmentService;

    public ResponseEntity<Comment> postComment(UUID id, RequestComment data) {
        final Establishment establishment = establishmentService.getEstablishment(id).getBody();
        final Comment comment = new Comment(data);

        assert establishment != null;
        comment.setIdEstablishment(establishment.getIdUser());
        establishment.addComment(comment);
        return ResponseEntity.status(200).body(commentRepository.save(comment));
    }

    public ResponseEntity<Comment> postCommentChild(UUID idParent, RequestCommentChild data) {
        Optional<Comment> commentOptional = commentRepository.findById(idParent);

        if (commentOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        Comment commentParent = commentOptional.get();
        Comment comment = new Comment(idParent, data);

        commentParent.addReply(comment);

//        commentRepository.save(commentParent);
        commentRepository.save(comment);

        return ResponseEntity.status(200).body(comment);
    }

    public ResponseEntity<List<Comment>> getComments() {
        if (commentRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(commentRepository.findAll());
    }

    public ResponseEntity<Optional<Comment>> get(UUID id) {
        if (commentRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(commentRepository.findById(id));
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
        if (comment.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        comment.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(commentRepository.save(comment.get()));
    }
}