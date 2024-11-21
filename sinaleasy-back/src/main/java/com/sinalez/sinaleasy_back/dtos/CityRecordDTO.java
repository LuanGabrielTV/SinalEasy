package com.sinalez.sinaleasy_back.dtos;

public record CityRecordDTO(
    String cityId,
    String name,
    String state,
    Integer rating
) {}