package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;

import java.util.List;

public record CustomerProfileDTO(String name, String profilePhoto, String bio, Integer level, Double rate, Integer XP, Integer qtdComments, List<CommentDTO> comments) {
}
