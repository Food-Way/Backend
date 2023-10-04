package com.foodway.api.service.comment;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EstablishmentService establishmentService;

    @Autowired
    EstablishmentRepository establishmentRepository;

    public ResponseEntity<Comment> postComment(UUID id, RequestComment data) {
        Comment comment = new Comment(data);

        Optional<Establishment> establishmentOptional = establishmentRepository.findById(id);

        if (establishmentOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        establishmentOptional.get().addComment(comment);

        return ResponseEntity.status(200).body
                (commentRepository.save(comment));
    }

    public ResponseEntity<Comment> postCommentChild(UUID idParent, RequestCommentChild comment) {
        Comment newComment = new Comment(comment);
        Optional<Comment> commentOptional = commentRepository.findById(idParent);

        if(commentOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        Comment commentParent = commentOptional.get();

        commentParent.addCommentChild(newComment);

        commentRepository.save(commentParent);

        return ResponseEntity.status(200).body(newComment);
    }

    public ResponseEntity<List<Comment>> getComments() {
        if (commentRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(commentRepository.findAll());
    }

    public ResponseEntity<Optional<Comment>> get(UUID id) {
        if (commentRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
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
        if(comment.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        comment.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(commentRepository.save(comment.get()));
    }


}
