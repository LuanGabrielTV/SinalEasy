package com.sinalez.sinaleasy_back.dtos;

import java.util.UUID;

public record VoteRecordDTO(
    UUID signalId,
    boolean voted // true = adiciona um like, false = remove o like
    
) {}