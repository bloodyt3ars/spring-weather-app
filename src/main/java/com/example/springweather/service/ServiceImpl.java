package com.example.springweather.service;

import com.example.springweather.repository.CityRepository;

public class ServiceImpl {
    private final CityRepository cityRepository;

    public ServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public double getTemperatureByCity(String name){

        return 0.0;
    }
}
