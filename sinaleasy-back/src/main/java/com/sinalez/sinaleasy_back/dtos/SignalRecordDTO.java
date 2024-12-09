package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SignalRecordDTO(
    UUID signalId,
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
    Integer status,
    List<MilestoneRecordDTO> signalMilestones

) {}

