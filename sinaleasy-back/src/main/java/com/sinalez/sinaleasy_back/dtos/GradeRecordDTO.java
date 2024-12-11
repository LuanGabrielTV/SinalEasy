package com.sinalez.sinaleasy_back.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record GradeRecordDTO(
    UUID gradeId,
    Integer rating,
    String description,
    LocalDate gradeUpdateTime,
    UUID signalId
) {}
