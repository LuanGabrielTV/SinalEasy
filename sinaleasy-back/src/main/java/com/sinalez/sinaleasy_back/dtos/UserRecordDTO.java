package com.sinalez.sinaleasy_back.dtos;

import java.util.List;
import java.util.UUID;

public record UserRecordDTO(
    UUID userId,
    String userLogin,
    String userPassword,
    String userEmail,
    List<SignalRecordDTO> userSignals

) {}