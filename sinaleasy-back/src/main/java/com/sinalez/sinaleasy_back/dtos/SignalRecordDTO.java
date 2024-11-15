package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;

public record SignalRecordDTO(
    
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String address,
    @NotNull BigDecimal scaleFactor,
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotNull Integer typeOfSignal,
    @NotBlank String cityId,
    LocalDate date,
    Integer numberOfLikes,
    Integer Status
) {}

