package com.foodway.api.service.dashboard;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import com.foodway.api.record.DTOs.*;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public ResponseEntity<EstablishmentDashboardViewDTO> getDashboardData(UUID idEstablishment) {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Comment> c = commentRepository.findByidEstablishment(idEstablishment, pageable);
        Optional<Establishment> establishment = establishmentRepository.findById(idEstablishment);
        List<CommentDTO> comments = new ArrayList<>();

        if (!establishment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment does not exist!");
        }

        Map<String, Integer> qtdEvaluationDaysForWeek = new HashMap<>(Map.ofEntries(
                Map.entry("SUNDAY", 0),
                Map.entry("MONDAY", 0),
                Map.entry("TUESDAY", 0),
                Map.entry("WEDNESDAY", 0),
                Map.entry("THURSDAY", 0),
                Map.entry("FRIDAY", 0),
                Map.entry("SATURDAY", 0)
        ));

        if (!c.isEmpty()) {
            for (int i = 0; i < c.size(); i++) {
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

//            Collections.sort(comments, Comparator.comparingInt(CommentDTO::upvotes).reversed());
        }

        List<EstablishmentRateDto> establishmentRateDto = List.of(
                new EstablishmentRateDto("Ambient", establishment.get().getAmbientRate()),
                new EstablishmentRateDto("Food", establishment.get().getFoodRate()),
                new EstablishmentRateDto("Service", establishment.get().getServiceRate())
        );

        List<QtdEvaluationDaysForWeek> qtdEvaluationDaysForWeeks = List.of(
                new QtdEvaluationDaysForWeek("Sunday",  qtdEvaluationDaysForWeek.get("SUNDAY")),
                new QtdEvaluationDaysForWeek("Monday",  qtdEvaluationDaysForWeek.get("MONDAY")),
                new QtdEvaluationDaysForWeek("Tuesday",  qtdEvaluationDaysForWeek.get("TUESDAY")),
                new QtdEvaluationDaysForWeek("Wednesday",  qtdEvaluationDaysForWeek.get("WEDNESDAY")),
                new QtdEvaluationDaysForWeek("Thursday",  qtdEvaluationDaysForWeek.get("THURSDAY")),
                new QtdEvaluationDaysForWeek("Friday",  qtdEvaluationDaysForWeek.get("FRIDAY")),
                new QtdEvaluationDaysForWeek("Saturday",  qtdEvaluationDaysForWeek.get("SATURDAY"))
        );

        EstablishmentDashboardViewDTO dashboardDTO = new EstablishmentDashboardViewDTO(
                comments,
                establishment.get().getGeneralRate(),
                establishmentRateDto,
                qtdEvaluationDaysForWeeks,
                establishment.get().getTags()
        );

        return ResponseEntity.status(200).body(dashboardDTO);
    }
}
