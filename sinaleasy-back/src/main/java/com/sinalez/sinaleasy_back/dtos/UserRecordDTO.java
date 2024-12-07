package com.sinalez.sinaleasy_back.dtos;

import java.util.UUID;

public record UserRecordDTO(
    UUID userId,
    String userLogin,
    String userPassword,
    String userEmail

) {}