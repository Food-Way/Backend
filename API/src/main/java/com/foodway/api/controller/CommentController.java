package com.foodway.api.controller;

import com.foodway.api.exceptions.CommentNotFoundException;
import com.foodway.api.exceptions.CulinaryNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.service.comment.CommentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all comments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all comments"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Comment>> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comment by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a comment"),
            @ApiResponse(responseCode = CommentNotFoundException.CODE, description = CommentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Optional<Comment>> get(@PathVariable UUID id) {
        return commentService.get(id);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update comment by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated comment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = CommentNotFoundException.CODE, description = CommentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity putComment(@PathVariable UUID id, @RequestBody @Validated UpdateCommentData comment) {
        return commentService.putComment(id, comment);
    }

    @PostMapping("establishment/{idEstablishment}")
    @Operation(summary = "Create a new comment", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created comment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Comment> postComment(@PathVariable UUID id, @RequestBody @Validated RequestComment data) {
        return commentService.postComment(id, data);
    }

    @PatchMapping("/{idComment}/upvote")
    @Operation(summary = "Add a upvote in comment", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the upvoted comment"),
            @ApiResponse(responseCode = CommentNotFoundException.CODE, description = CommentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Comment> postCommentChild(UUID idComment, UUID idVoter) {
        return commentService.upvoteComment(idComment, idVoter);
    }

    @PostMapping("establishment/{idEstablishment}/parent/{idParent}")
    @Operation(summary = "Create a new comment child", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created comment child"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity postCommentChild(@PathVariable UUID idEstablishment, @PathVariable UUID idParent, @RequestBody RequestCommentChild comment) {
        return commentService.postCommentChild(idEstablishment, idParent, comment);
    }

    @DeleteMapping("/{idComment}/{idOwner}")
    @Operation(summary = "Delete comment by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted comment"),
            @ApiResponse(responseCode = CulinaryNotFoundException.CODE, description = CulinaryNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity deleteComment(@PathVariable UUID id, @PathVariable UUID idOwner) {
        return commentService.deleteComment(id, idOwner);
    }

    //todo fazer filtros de comentários

}