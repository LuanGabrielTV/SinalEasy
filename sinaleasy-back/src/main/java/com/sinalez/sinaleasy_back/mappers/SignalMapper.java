package com.sinalez.sinaleasy_back.mappers;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.sinalez.sinaleasy_back.domains.Signal;
import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;

@Mapper(componentModel = "spring")
public interface SignalMapper {
    @Mapping(target = "scaleFactor", constant = "1")
    // @Mapping(target = "liked", constant = "false")
    @Mapping(target = "numberOfLikes", constant = "1")
    @Mapping(target = "cityId", source = "signal.city.cityId")
    @Mapping(target = "userId", source = "signal.user.userId")
    @Mapping(target = "signalGrade", source = "signal.grade")
    SignalRecordDTO toDTO(Signal signal);

    @Mapping(target = "cityId", source = "signal.city.cityId")
    @Mapping(target = "userId", source = "signal.user.userId")
    @Mapping(target = "signalGrade", source = "signal.grade")
    @Mapping(target = "numberOfLikes", source = "numberOfLikes")
    @Mapping(target = "scaleFactor", source = "scaleFactor")
    SignalRecordDTO toDTO(Signal signal, boolean liked, Integer numberOfLikes, BigDecimal scaleFactor);

    // @Mapping(target = "city", ignore = true)
    // @Mapping(target = "signalId", ignore = true)
    // Signal fromDTO(SignalRecordDTO signalRecordDTO);

    // @Mapping(target = "city", ignore = true)
    // @Mapping(target = "signalId", ignore = true)
    // @Mapping(target = "signalMilestones", ignore = true)
    // Signal updateFromDTO(SignalRecordDTO signalRecordDTO, @MappingTarget Signal signal);

}
