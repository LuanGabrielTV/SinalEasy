package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.Signal;

@Mapper(componentModel = "spring")
public interface SignalMapper {
    @Mapping(target = "scaleFactor", constant = "1")
    @Mapping(target = "numberOfLikes", constant = "1")
    @Mapping(target = "cityId", source = "signal.city.cityId")
    SignalRecordDTO toDTO(Signal signal);

    // @Mapping(target = "city", ignore = true)
    // @Mapping(target = "signalId", ignore = true)
    // Signal fromDTO(SignalRecordDTO signalRecordDTO);

    // @Mapping(target = "city", ignore = true)
    // @Mapping(target = "signalId", ignore = true)
    // @Mapping(target = "signalMilestones", ignore = true)
    // Signal updateFromDTO(SignalRecordDTO signalRecordDTO, @MappingTarget Signal signal);

}
