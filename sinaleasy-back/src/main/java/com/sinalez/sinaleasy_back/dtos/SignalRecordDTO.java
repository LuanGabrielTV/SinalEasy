package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;

import com.sinalez.sinaleasy_back.enums.TypeOfSignal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SignalRecordDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank BigDecimal scaleFactor,
    @NotEmpty TypeOfSignal typeOfSignal
) {}