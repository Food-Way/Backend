package com.foodway.api.record.DTOs;

import com.foodway.api.model.Culinary;

import java.util.List;
import java.util.UUID;

public record EstablishmentDTO(UUID idEstablishment, String establishmentName, Double establishmentRate, List<Culinary> culinary, String photo) {
}
