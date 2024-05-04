package com.foodway.api.service.comment;

import com.foodway.api.handler.exceptions.CommentNotFoundException;
import com.foodway.api.model.*;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.*;
import com.foodway.api.service.customer.CustomerService;
import com.foodway.api.service.establishment.EstablishmentService;
  
import java.util.*; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UpvoteRepository upvoteRepository;
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    CustomerService customerService;
    @Autowired
    private RateRepository rateRepository;

    public ResponseEntity<Comment> postComment(RequestComment data) {
        final Customer customer = customerService.getCustomer(data.idCustomer()).getBody();
        final Establishment establishment = establishmentService.getEstablishment(data.idEstablishment()).getBody();
        final Comment comment = new Comment(data);
        comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));
        establishment.addComment(comment);
        final Comment commentSaved = commentRepository.save(comment);
        final Customer customerIncreasedXP = customer.increaseXp(10);
        userRepository.save(customerIncreasedXP);
        return ResponseEntity.status(200).body(commentSaved);
    }

    public ResponseEntity<Comment> postCommentChild(RequestCommentChild data) {
            Comment commentChildSaved;
        if(data.typeUser().equals(ETypeUser.CLIENT)){
            final Comment commentParent = commentRepository.findById(data.idParent()).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
            final Customer customer = customerService.getCustomer(data.idCustomer()).getBody();
            final Comment commentChild = new Comment(data);
            commentChild.setGeneralRate(generateGeneralRateForComment(commentChild.getIdCustomer(), commentChild.getIdEstablishment()));
            commentParent.addReply(commentChild);
            commentChildSaved = commentRepository.save(commentChild);
            final Customer customerIncreasedXP = customer.increaseXp(10);
            userRepository.save(customerIncreasedXP);
        }
        else {
            final Comment commentParent = commentRepository.findById(data.idParent()).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
            final Comment commentChild = new Comment(data);
            commentParent.addReply(commentChild);
            commentChildSaved = commentRepository.save(commentChild);
            commentRepository.save(commentParent);
        }
        return ResponseEntity.status(200).body(commentChildSaved);
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

    public List<Comment> getCountsFromComments(List<Comment> comments) {
        if (!comments.isEmpty()) {
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                countUpvotesOfComment(comment);
                comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));
                if(!comment.getReplies().isEmpty()){
                    comments.addAll(comment.getReplies());
                }
            }
        }

        return comments;
    }

    public ResponseEntity<Void> deleteComment(UUID id) {
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

    public int countUpvotesOfComment(Comment comment) {
        Integer countUpvotes = upvoteRepository.countUpvotesByComment(comment.getIdPost());
        comment.setUpvotes(countUpvotes);
        return countUpvotes;
    }

    public Double generateGeneralRateForComment(UUID idCustomer, UUID idEstablishment) {
        List<Rate> rates = rateRepository.findByCommentOfCustomer(idCustomer, idEstablishment);
        int count = 0;
        Double sum = 0.0;
        for (Rate rate : rates) {
            sum += rate.getRatePoint();
            count++;
        }
        if (count == 0) return 0.0;
        return sum / count;
    }


}