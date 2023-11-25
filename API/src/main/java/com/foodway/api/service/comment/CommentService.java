package com.foodway.api.service.comment;

import com.foodway.api.handler.exceptions.CommentNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.repository.UpvoteRepository;
import com.foodway.api.repository.UserRepository;
import com.foodway.api.service.customer.CustomerService;
import com.foodway.api.service.establishment.EstablishmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.foodway.api.utils.Fila;
import com.foodway.api.utils.Pilha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UpvoteRepository upvoteRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CustomerService customerService;
    @Autowired
    private RateRepository rateRepository;

    public ResponseEntity<Comment> postComment(RequestComment data) {
        if (!userRepository.existsById(data.idCustomer())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (!userRepository.existsById(data.idEstablishment())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found");
        }
        final Establishment establishment = establishmentService.getEstablishment(data.idEstablishment()).getBody();
        final Comment comment = new Comment(data);

        establishment.addComment(comment);
        comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));
        return ResponseEntity.status(200).body(commentRepository.save(comment));
    }

    public ResponseEntity<Comment> postCommentChild(RequestCommentChild data) {
        if (!commentRepository.existsById(data.idParent())) {
            throw new CommentNotFoundException("Comment not found");
        }
        if (!userRepository.existsById(data.idCustomer())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (!userRepository.existsById(data.idEstablishment())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found");
        }

        Comment commentParent = commentRepository.findById(data.idParent()).get();
        Comment comment = new Comment(data);

        commentParent.addReply(comment);
        comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));

        commentRepository.save(comment);
        return ResponseEntity.status(200).body(comment);
    }

    public ResponseEntity<List<Comment>> getComments() {
        if (commentRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            countUpvotesOfComment(comment);
            comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));
        }

        return ResponseEntity.status(200).body(comments);
    }

    public ResponseEntity<Optional<Comment>> get(UUID id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new CommentNotFoundException("Comment not found");
        }
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            countUpvotesOfComment(comment);
            comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));        }
        return ResponseEntity.status(200).body(commentRepository.findById(id));
    }

    public ResponseEntity<Void> deleteComment(UUID id, UUID idOwner) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return ResponseEntity.status(200).build();
        }
        throw new CommentNotFoundException("Comment not found");
    }

    public ResponseEntity<Comment> putComment(UUID id, UpdateCommentData data) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException("Comment not found");
        }
        comment.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(commentRepository.save(comment.get()));
    }


    public void countUpvotesOfComment(Comment comment) {
        Integer countUpvotes = upvoteRepository.countUpvotesByComment(comment.getIdPost());
        comment.setUpvotes(countUpvotes);
    }

    public double generateGeneralRateForComment(UUID idCustomer, UUID idEstablishment) {
        List<Rate> rates = rateRepository.findByCommentOfCustomer(idCustomer, idEstablishment);
        int count = 0;
        double sum = 0.0;
        for (Rate rate: rates) {
            sum += rate.getRatePoint();
            count++;
        }
        if (count == 0) return 0.0;
        return sum / count;
    }

    public ResponseEntity<Fila<Comment>> getBetterAvaliated() {
        List<Comment> comments = commentRepository.findAllOrderByGeneralRateDesc();
        if(comments.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

       Fila<Comment> fila = new Fila<>(comments.size());

        for (int i = 0; i < comments.size(); i++) {
            Comment current = comments.get(i);
            fila.insert(current);
        }

        return ResponseEntity.status(200).body(fila);
    }


}