package com.foodway.api.record;

import com.foodway.api.model.Enums.ETypeRate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestRateAddOrUpdate(
        @NotNull
        UUID idCustomer,
        @NotNull
        UUID idEstablishment,
        @NotNull
        List<DescriptionRate> rates
) {
    public record DescriptionRate(
            @NotBlank
            ETypeRate name,
            @NotNull
            Double ratePoint
    ){
    }
}
