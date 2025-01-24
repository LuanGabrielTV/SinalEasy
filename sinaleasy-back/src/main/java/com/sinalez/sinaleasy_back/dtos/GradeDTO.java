package com.sinalez.sinaleasy_back.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record GradeDTO(
    UUID gradeId,
    Integer rating,
    String description,
    LocalDate gradeUpdateTime,
    UUID signalId
) {}
