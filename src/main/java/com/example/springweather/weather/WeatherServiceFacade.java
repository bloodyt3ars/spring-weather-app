package com.example.springweather.weather;

public interface WeatherServiceFacade {
    WeatherDto getWeatherByCity(String cityName) throws Exception;

    WeatherDto getWeatherByCityAndService(String cityName, String serviceName) throws Exception;
}
