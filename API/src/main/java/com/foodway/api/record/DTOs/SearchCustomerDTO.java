package com.foodway.api.record.DTOs;

import java.util.UUID;

public record SearchCustomerDTO(UUID idCustomer, String name, String culinary, Double generalRate, String bio, long upvote, String photo, String lastComment) {
}
