package com.example.springweather.service;

import com.example.springweather.entity.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class WeatherServiceFacade {
    @Value(value = "${weather.defaultService}")
    private String defaultService;

    private final CityService cityService;
    private final WeatherService openWeatherMapService;
    private final WeatherService yandexWeatherService;

    public WeatherServiceFacade(CityService cityService, WeatherService openWeatherMapService, WeatherService yandexWeatherService) {
        this.cityService = cityService;
        this.openWeatherMapService = openWeatherMapService;
        this.yandexWeatherService = yandexWeatherService;
    }

    public Map getWeatherByCity(String cityName) throws IllegalArgumentException{
        return getWeatherByCityAndService(cityName, defaultService);
    }

    public Map getWeatherByCityAndService(String cityName, String serviceName) throws IllegalArgumentException{
        City cityByName = cityService.findByName(cityName);
        if (cityByName != null) {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            String longitude = cityByName.getLongitude();
            String latitude = cityByName.getLatitude();
            WeatherService weatherService = getWeatherService(serviceName);
            if (weatherService != null) {
                Map weatherByLongitudeAndLatitude = weatherService.getWeatherByLongitudeAndLatitude(longitude, latitude);
                responseMap.put("City", cityByName.getName());
                responseMap.put("lat", latitude);
                responseMap.put("lon", longitude);
                responseMap.put("weather", weatherByLongitudeAndLatitude);
                return responseMap;
            }
        }
        return null;
    }

    private WeatherService getWeatherService(String serviceName) throws IllegalArgumentException{
        if ("openweathermap".equalsIgnoreCase(serviceName)) {
            return openWeatherMapService;
        } else if ("yandexweather".equalsIgnoreCase(serviceName)) {
            return yandexWeatherService;
        } else {
            throw new IllegalArgumentException("Unknown service name");
        }
    }
}
