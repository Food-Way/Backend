package com.foodway.api.record;
import jakarta.validation.constraints.NotBlank;
public record UpdateTag(
        @NotBlank
        String name

) {

}
