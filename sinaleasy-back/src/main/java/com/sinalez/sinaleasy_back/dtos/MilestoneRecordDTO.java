package com.sinalez.sinaleasy_back.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record MilestoneRecordDTO(
    UUID milestoneId,
    int status,
    LocalDate statusUpdateTime
    
) {}
