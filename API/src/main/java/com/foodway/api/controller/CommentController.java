package com.foodway.api.controller;

import com.foodway.api.model.Comment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.service.comment.CommentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> get(@PathVariable UUID id) {
        return commentService.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity putComment(@PathVariable UUID id, @RequestBody @Validated UpdateCommentData comment) {
        return commentService.putComment(id, comment);
    }

    @PostMapping("establishment/{idEstablishment}")
    public ResponseEntity<Comment> postComment(@PathVariable UUID idEstablishment, @RequestBody @Validated RequestComment data) {
        return commentService.postComment(idEstablishment, data);
    }

    @PatchMapping("/{idComment}/upvote")
    public ResponseEntity<Comment> postCommentChild(UUID idComment, UUID idVoter) {
        return commentService.upvoteComment(idComment, idVoter);
    }

    @PostMapping("establishment/{idEstablishment}/parent/{idParent}")
    public ResponseEntity postCommentChild(@PathVariable UUID idEstablishment, @PathVariable UUID idParent, @RequestBody RequestCommentChild comment) {
        return commentService.postCommentChild(idEstablishment, idParent, comment);
    }

    @DeleteMapping("/{idComment}/{idOwner}")
    public ResponseEntity deleteComment(@PathVariable UUID id, @PathVariable UUID idOwner) {
        return commentService.deleteComment(id, idOwner);
    }

    //todo fazer filtros de comentários

}