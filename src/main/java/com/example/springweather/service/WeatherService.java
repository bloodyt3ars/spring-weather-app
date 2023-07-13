package com.example.springweather.service;

import com.example.springweather.entity.City;

public interface WeatherService {
    Double getTemperatureByCity(City city);
}
