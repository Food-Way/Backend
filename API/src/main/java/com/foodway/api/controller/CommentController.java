package com.foodway.api.controller;

import com.foodway.api.handler.exceptions.CommentNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.service.comment.CommentService;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
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

    @PutMapping("/{id}")
    @Operation(summary = "Update comment by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the updated comment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = CommentNotFoundException.CODE, description = CommentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity putComment(@PathVariable UUID id, @RequestBody @Valid UpdateCommentData comment) {

        return commentService.putComment(id, comment);
    }

    @PostMapping
    @Operation(summary = "Create a new comment", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created comment"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Comment> postComment(@RequestBody @Valid RequestComment data) {
        return commentService.postComment(data);
    }

    @PostMapping("/child")
    @Operation(summary = "Create a new comment child", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created comment child"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Comment> postCommentChild(@RequestBody RequestCommentChild data) {
        return commentService.postCommentChild(data);
    }

    @DeleteMapping("/{idComment}/{idOwner}")
    @Operation(summary = "Delete comment by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the deleted comment"),
            @ApiResponse(responseCode = CommentNotFoundException.CODE, description = CommentNotFoundException.DESCRIPTION),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        return commentService.deleteComment(id);
    }

}