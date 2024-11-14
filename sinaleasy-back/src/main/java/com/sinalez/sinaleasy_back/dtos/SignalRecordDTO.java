package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;

public record SignalRecordDTO(
    String name,
    String description,
    String address,
    BigDecimal scaleFactor,
    Double latitude,
    Double longitude,
    Integer typeOfSignal,
    String cityId,
    LocalDate date,
    Integer numberOfLikes
    
) {}

