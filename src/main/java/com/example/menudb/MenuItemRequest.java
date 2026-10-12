package com.example.menudb;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MenuItemRequest(
        @NotBlank String name,
        String category,
        @NotNull @Positive BigDecimal price) {
}
