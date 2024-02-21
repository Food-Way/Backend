package com.foodway.api.service.dashboard;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.DTOs.CommentDTO;
import com.foodway.api.record.DTOs.DashboardDTO;
import com.foodway.api.record.DTOs.EstablishmentDashboardDTO;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class DashboardService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    CommentService commentService;

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
            int countUpvotes = commentService.countUpvotesOfComment(comment);
            Double generalRate = comment.setGeneralRate(commentService.generateGeneralRateForComment(comment.getIdCustomer(), comment.getIdEstablishment()));

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
