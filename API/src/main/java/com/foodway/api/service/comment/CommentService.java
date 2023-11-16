package com.foodway.api.service.comment;

import com.foodway.api.handler.exceptions.CommentNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.UserRepository;
import com.foodway.api.service.customer.CustomerService;
import com.foodway.api.service.establishment.EstablishmentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CustomerService customerService;

    public ResponseEntity<Comment> postComment(UUID idCustomer, UUID idEstablishment, RequestComment data) {
        if (!userRepository.existsById(idCustomer)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (!userRepository.existsById(idEstablishment)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found");
        }
        final Establishment establishment = establishmentService.getEstablishment(idEstablishment).getBody();
        final Customer customer = customerService.getCustomer(idCustomer).getBody();
        final Comment comment = new Comment(data);

        comment.setIdEstablishment(establishment.getIdUser());
        comment.setIdCustomer(customer.getIdUser());
        establishment.addComment(comment);
        return ResponseEntity.status(200).body(commentRepository.save(comment));
    }

    public ResponseEntity<Comment> postCommentChild(UUID idCustomer, UUID idEstablishment, UUID idParent, RequestCommentChild data) {
        Optional<Comment> commentOptional = commentRepository.findById(idParent);
        if (!userRepository.existsById(idCustomer)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (!userRepository.existsById(idEstablishment)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found");
        }
        if (commentOptional.isEmpty()) {
            throw new CommentNotFoundException("Comment not found");
        }

        final Establishment establishment = establishmentService.getEstablishment(idEstablishment).getBody();
        final Customer customer = customerService.getCustomer(idCustomer).getBody();

        Comment commentParent = commentOptional.get();
        Comment comment = new Comment(idParent, data);
        comment.setIdEstablishment(establishment.getIdUser());
        comment.setIdCustomer(customer.getIdUser());

        commentParent.addReply(comment);

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
            throw new CommentNotFoundException("Comment not found");
        }
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

    public ResponseEntity<Comment> upvoteComment(UUID idComment, UUID idVoter) {
        // TODO CRIAR CLASSE PARA UPVOTES
        Comment comment = get(idComment).getBody().get();
        customerService.getCustomer(idVoter);
        comment.upvote();
        return null;
    }
}