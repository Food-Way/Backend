package com.foodway.api.record;

import com.foodway.api.model.Tags;

import java.util.Set;
import java.util.UUID;

public record RequestTagEstablishment(UUID establishment, Set<Long> tags) {
}
