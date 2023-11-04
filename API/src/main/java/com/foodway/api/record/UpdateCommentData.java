package com.foodway.api.record;

import com.foodway.api.model.Customer;
import com.foodway.api.model.Rate;
import com.foodway.api.model.Tag;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateCommentData(
        @NotBlank int upvotes,
        @NotBlank String coment,
        @NotBlank List<Tag> tagList,
        @NotBlank List<Customer> listCustomer,
        @NotBlank List<String> images,
        @NotBlank Rate rate
) {
}
