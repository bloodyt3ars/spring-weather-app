package com.example.springweather.service;

import java.util.Map;

public interface WeatherService {

    Map getWeatherByLongitudeAndLatitude(String longitude, String latitude);
}
