package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.Signal;

@Mapper(componentModel = "spring")
public interface SignalMapper {
    @Mapping(target = "scaleFactor", constant = "1")
    @Mapping(target = "numberOfLikes", constant = "1")
    SignalRecordDTO toDTO(Signal signal);

    @Mapping(target = "city", ignore = true)
    @Mapping(target = "signalId", ignore = true)
    Signal fromDTO(SignalRecordDTO signalRecordDTO);
}



