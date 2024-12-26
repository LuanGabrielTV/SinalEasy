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
    String userId,
    LocalDate date,
    Integer numberOfLikes,
    Integer status,
    List<MilestoneRecordDTO> signalMilestones,
    GradeRecordDTO signalGrade,
    boolean liked
) {

    public SignalRecordDTO setNumberOfLikes(int numberOfLikes){
        return new SignalRecordDTO(signalId(), name(), description(), address(), scaleFactor(), latitude(), longitude(), typeOfSignal(), cityId(), userId(), date(), numberOfLikes, status(), signalMilestones(), signalGrade(), liked());
    }

    public SignalRecordDTO setLiked(boolean liked){
        return new SignalRecordDTO(signalId(), name(), description(), address(), scaleFactor(), latitude(), longitude(), typeOfSignal(), cityId(), userId(), date(), numberOfLikes(), status(), signalMilestones(), signalGrade(), liked);
    }

}

