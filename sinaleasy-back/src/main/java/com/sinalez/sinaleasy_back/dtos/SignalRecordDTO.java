package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sinalez.sinaleasy_back.enums.TypeOfSignal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SignalRecordDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String address,
    @NotBlank BigDecimal scaleFactor,
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotEmpty TypeOfSignal typeOfSignal,
    @NotNull Integer cityId,
    @NotNull LocalDate date
    
) {}