//package com.foodway.api.controller;
//
//import com.foodway.api.model.Comment;
//import com.foodway.api.record.RequestComment;
//import com.foodway.api.record.UpdateCommentData;
//import com.foodway.api.service.comment.CommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/comments")
//public class CommentController {
//
//    @Autowired
//    private CommentService commentService;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<List<Comment>> getComments(@PathVariable UUID id) {
//
//        return commentService.getComments(id);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity putComment(@PathVariable UUID id, @RequestBody @Validated UpdateCommentData comment){
//        return commentService.putComment(id, comment);
//    }
//
////    @PostMapping("/{idUser}")
////    public ResponseEntity postComment(@PathVariable UUID idUser, @RequestBody @Validated RequestComment data){
////        return commentService.postComment(idUser, data);
////    }
//
//    @DeleteMapping("/{idComment}/{idOwner}")
//    public ResponseEntity deleteComment(@PathVariable UUID id,
//                                        @PathVariable UUID idOwner){
//        return commentService.deleteComment(id, idOwner);
//    }
//
//    //todo fazer filtros de comentários
//
//}
