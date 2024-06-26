package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;

import java.util.List;

public record CustomerProfileDTO(String name, String profilePhoto, String profileHeaderImg, String bio, Integer level, Double profileRate, Integer XP, long qtdComments, long qtdUpvotes, List<CommentDTO> comments, List<EstablishmentDTO> establishmentDTOs) {
}
