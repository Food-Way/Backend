package com.foodway.api.record;

import com.foodway.api.model.Enums.ETypeRate;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestRate(
        @NotNull
        UUID idCustomer,
        @NotNull
        UUID idEstablishment,
        @NotNull
        Double ratePoint,
        @NotNull
        ETypeRate typeRate

) {}
