package com.foodway.api.record.DTOs;

public record SeachEstablishmentDTO(String name, String culinary, Double generalRate, String bio, long upvote, String photo, String lat, String lng, String lastComment) {
}
