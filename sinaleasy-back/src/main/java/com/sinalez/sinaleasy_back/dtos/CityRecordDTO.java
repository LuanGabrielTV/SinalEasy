package com.sinalez.sinaleasy_back.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityRecordDTO(
    @NotNull Integer cityId,
    @NotBlank String name,
    @NotBlank String state,
    @NotNull  Integer rating
) {}