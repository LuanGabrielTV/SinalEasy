package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    Integer numberOfLikes,
    Integer status

) {}

