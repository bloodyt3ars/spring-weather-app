package com.example.springweather.weather;

import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.exception.InternalErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WeatherService {

    WeatherDto getWeatherByLongitudeAndLatitude(String longitude, String latitude) throws InternalErrorException;
}
