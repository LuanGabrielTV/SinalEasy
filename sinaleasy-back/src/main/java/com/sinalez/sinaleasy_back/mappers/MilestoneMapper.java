// package com.sinalez.sinaleasy_back.mappers;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.sinalez.sinaleasy_back.dtos.MilestoneDTO;
// import com.sinalez.sinaleasy_back.entities.Milestone;

// @Mapper(componentModel = "spring")
// public interface MilestoneMapper {
//     @Mapping(target = "milestoneId", ignore = true)
//     @Mapping(target = "signal", ignore = true)
//     MilestoneDTO toDTO(Milestone milestoneResponseDTO);

//     @Mapping(target = "milestoneId", ignore = true)
//     @Mapping(target = "signal", ignore = true)
//     Milestone fromDTO(MilestoneDTO milestoneRequestDTO);

// }