package com.foodway.api.record.DTOs;

public record SeachEstablishmentDTO(String name, String culinary, Double generalRate, String bio, Integer upvote, String photo, String lat, String lng, String lastComment) {
}
