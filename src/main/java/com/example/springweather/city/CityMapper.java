package com.example.springweather.city;

import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CityMapper {
    CityEntity toEntity(CityDto dto);
}
