package com.foodway.api.record.DTOs;

import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.model.Favorite;

import java.util.List;
import java.util.UUID;

public record SearchEstablishmentDTO(UUID idEstablishment, String name, ETypeUser typeUser, String culinary, Double generalRate, String bio, long upvote, String photo, String lat, String lng, String lastComment, boolean isFavorite) {
}
