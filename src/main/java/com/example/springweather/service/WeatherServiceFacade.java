package com.example.springweather.service;

import com.example.springweather.entity.City;
import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class WeatherServiceFacade {
    @Value(value = "${weather.defaultService}")
    private String defaultService;

    private final WeatherServiceRegistry weatherServiceRegistry;
    private final CityService cityService;
    private final WeatherService openWeatherMapService;
    private final WeatherService yandexWeatherService;

    public WeatherServiceFacade(WeatherServiceRegistry weatherServiceRegistry, CityService cityService, @Qualifier("openweathermap") WeatherService openWeatherMapService,@Qualifier("yandexweather") WeatherService yandexWeatherService) {
        this.weatherServiceRegistry = weatherServiceRegistry;
        this.cityService = cityService;
        this.openWeatherMapService = openWeatherMapService;
        this.yandexWeatherService = yandexWeatherService;
    }

    public Map<String, Object> getWeatherByCity(String cityName) throws IncorrectServiceNameException, IncorrectСityNameException {
        return getWeatherByCityAndService(cityName, defaultService);
    }

    public Map<String, Object> getWeatherByCityAndService(String cityName, String serviceName) throws IncorrectServiceNameException, IncorrectСityNameException {
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

    private WeatherService getWeatherService(String serviceName) throws IncorrectServiceNameException {
        return weatherServiceRegistry.getWeatherService(serviceName);
    }
}
