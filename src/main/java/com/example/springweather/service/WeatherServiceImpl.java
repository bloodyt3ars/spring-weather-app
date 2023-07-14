package com.example.springweather.service;

import com.example.springweather.entity.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class WeatherServiceImpl {
    @Value(value = "${weather.defaultService}")
    private String defaultService;

    private final CityService cityService;
    private final WeatherService openWeatherMapService;
    private final WeatherService yandexWeatherService;

    public WeatherServiceImpl(CityService cityService, WeatherService openWeatherMapService, WeatherService yandexWeatherService) {
        this.cityService = cityService;
        this.openWeatherMapService = openWeatherMapService;
        this.yandexWeatherService = yandexWeatherService;
    }

    public Map getWeatherByCity(String cityName) {
        return getWeatherByCityAndService(cityName, defaultService);
    }

    public Map getWeatherByCityAndService(String cityName, String serviceName) {
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

    private WeatherService getWeatherService(String serviceName) {
        if ("openweathermap".equalsIgnoreCase(serviceName)) {
            return openWeatherMapService;
        } else if ("yandexweather".equalsIgnoreCase(serviceName)) {
            return yandexWeatherService;
        } else {
            return null;
        }
    }
}
