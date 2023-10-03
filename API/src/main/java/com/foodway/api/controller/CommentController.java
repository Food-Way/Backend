package com.foodway.api.controller;

import com.foodway.api.model.Comment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<Comment>> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> get(@PathVariable UUID id) {

        return commentService.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity putComment(@PathVariable UUID id, @RequestBody @Validated UpdateCommentData comment){
        return commentService.putComment(id, comment);
    }

    @PostMapping("/{id}")
    public ResponseEntity postComment(@PathVariable UUID id, @RequestBody @Validated RequestComment data){
        return commentService.postComment(id, data);
    }

    @DeleteMapping("/{idComment}/{idOwner}")
    public ResponseEntity deleteComment(@PathVariable UUID id,
                                        @PathVariable UUID idOwner){
        return commentService.deleteComment(id, idOwner);
    }

    //todo fazer filtros de comentários

}
