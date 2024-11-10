package com.sinalez.sinaleasy_back.dtos;

import com.sinalez.sinaleasy_back.enums.TypeOfSignal;

import jakarta.validation.constraints.NotBlank;

public record SignalRecordDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank float scaleFactor,
    TypeOfSignal typeOfSignal
) {}
