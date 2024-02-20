package com.foodway.api.service.comment;

import com.foodway.api.handler.exceptions.CommentNotFoundException;
import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.DTOs.CommentDTO;
import com.foodway.api.record.DTOs.DashboardDTO;
import com.foodway.api.record.DTOs.EstablishmentDashboardDTO;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import com.foodway.api.record.UpdateCommentData;
import com.foodway.api.repository.*;
import com.foodway.api.service.customer.CustomerService;
import com.foodway.api.service.establishment.EstablishmentService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    UpvoteRepository upvoteRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CustomerService customerService;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;

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
            comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));
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

    public ResponseEntity<DashboardDTO> getDashboardData(UUID idEstablishment) {
        List<Comment> c = commentRepository.findAllFromidEstablishment(idEstablishment);
        Optional<Establishment> establishment = establishmentRepository.findById(idEstablishment);
        List<CommentDTO> comments = new ArrayList<>();

        if (c.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no comment!");
        }
        if (!establishment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment does not exist!");
        }

        Map<String, Integer> qtdEvaluationDaysForWeek = new HashMap<>(Map.ofEntries(
                Map.entry("MONDAY", 0),
                Map.entry("TUESDAY", 0),
                Map.entry("WEDNESDAY", 0),
                Map.entry("THURSDAY", 0),
                Map.entry("FRIDAY", 0),
                Map.entry("SATURDAY", 0),
                Map.entry("SUNDAY", 0)
        ));


        for (int i = 0; i < 10; i++) {
            Comment comment = c.get(i);
            int countUpvotes = countUpvotesOfComment(comment);
            Double generalRate = comment.setGeneralRate(generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));

            comments.add(
                    new CommentDTO(
                            establishment.get().getEstablishmentName(),
                            comment.getComment(),
                            generalRate,
                            countUpvotes
                    ));

            String commentDayOfWeek = String.valueOf(comment.getCreatedAt().getDayOfWeek());

            int current = qtdEvaluationDaysForWeek.getOrDefault(commentDayOfWeek.toUpperCase(), 0);
            qtdEvaluationDaysForWeek.put(commentDayOfWeek, current + 1);
        }

        Collections.sort(comments, Comparator.comparingInt(CommentDTO::upvotes).reversed());

        EstablishmentDashboardDTO establishmentDashboardDTO = new EstablishmentDashboardDTO(
                establishment.get().getGeneralRate(),
                establishment.get().getAmbientRate(),
                establishment.get().getServiceRate(),
                establishment.get().getFoodRate(),
                establishment.get().getTags()
        );

        DashboardDTO dashboardDTO = new DashboardDTO(
                comments,
                establishmentDashboardDTO,
                qtdEvaluationDaysForWeek
        );

        return ResponseEntity.status(200).body(dashboardDTO);
    }
}