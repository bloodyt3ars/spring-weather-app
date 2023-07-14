package com.example.springweather.service;

import com.example.springweather.entity.City;

import java.util.Map;

public interface WeatherService {
    Map getTemperatureByCity(City city);
}
