package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.dtos.MilestoneRecordDTO;
import com.sinalez.sinaleasy_back.entities.Milestone;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {
    MilestoneRecordDTO toDTO(Milestone milestoneResponseDTO);
    Milestone fromDTO(MilestoneRecordDTO milestoneRequestDTO);
   
}