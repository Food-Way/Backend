package com.foodway.api.record.DTOs;

import com.foodway.api.model.Enums.ETypeUser;

import java.util.UUID;

public record SearchEstablishmentDTO(UUID idEstablishment, String name, ETypeUser typeUser, String culinary, Double generalRate, String bio, long upvote, String photo, String lat, String lng,
                                     int qtdComments, String lastComment, boolean isFavorite) {
}
