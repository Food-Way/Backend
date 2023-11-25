package com.foodway.api.record.DTOs;

import java.util.UUID;

public record SeachEstablishmentDTO(UUID idEstablishment, String name, String culinary, Double generalRate, String bio, long upvote, String photo, String lat, String lng, String lastComment) {
}
