package com.example.springweather.city;

import org.springframework.stereotype.Component;

@Component
public class CityMapperImpl implements CityMapper {
    @Override
    public CityEntity toEntity(CityDto dto) {
        if (dto == null) {
            return null;
        }
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(dto.getName());
        cityEntity.setLatitude(dto.getLatitude());
        cityEntity.setLongitude(dto.getLongitude());
        return cityEntity;
    }
}
