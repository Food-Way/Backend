package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestUpvote(
        @NotNull
        UUID idCustomer,
        @NotNull
        UUID idComment
) {
}
