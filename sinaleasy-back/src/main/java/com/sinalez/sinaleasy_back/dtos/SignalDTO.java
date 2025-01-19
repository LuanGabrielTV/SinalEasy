package com.sinalez.sinaleasy_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SignalDTO(
    UUID signalId,
    String name,
    String description,
    String address,
    BigDecimal scaleFactor,
    Double latitude,
    Double longitude,
    Integer typeOfSignal,
    String cityId,
    String userId,
    LocalDate date,
    Integer numberOfLikes,
    Integer status,
    List<MilestoneDTO> signalMilestones,
    GradeDTO signalGrade,
    boolean liked
) {

    public SignalDTO setNumberOfLikes(int numberOfLikes){
        return new SignalDTO(signalId(), name(), description(), address(), scaleFactor(), latitude(), longitude(), typeOfSignal(), cityId(), userId(), date(), numberOfLikes, status(), signalMilestones(), signalGrade(), liked());
    }

    public SignalDTO setLiked(boolean liked){
        return new SignalDTO(signalId(), name(), description(), address(), scaleFactor(), latitude(), longitude(), typeOfSignal(), cityId(), userId(), date(), numberOfLikes(), status(), signalMilestones(), signalGrade(), liked);
    }

}

