package com.foodway.api.record.DTOs;

import com.foodway.api.model.Culinary;

import java.util.List;

public record EstablishmentDTO(String establishmentName, Double establishmentRate, List<Culinary> culinary, String photo) {
}
