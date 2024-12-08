package com.sinalez.sinaleasy_back.dtos;

import java.time.LocalDate;

public record MilestoneRecordDTO(
    int status,
    LocalDate statusUpdateTime
) {}
