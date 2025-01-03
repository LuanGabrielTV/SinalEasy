package com.sinalez.sinaleasy_back.dtos;

public record CityDTO(
    String cityId,
    String name,
    String state,
    Integer rating
) {}