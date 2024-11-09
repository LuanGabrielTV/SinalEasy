package com.sinalez.sinaleasy_back.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityRecordDTO(
    @NotNull long id,
    @NotBlank String name,
    @NotBlank String state,
    @NotNull  Integer rating
) {}