package com.example.springweather.weather;

import com.example.springweather.city.CityEntity;
import com.example.springweather.city.CityService;
import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceFacade {
    @Value(value = "${weather.defaultService}")
    private String defaultService;
    private final CityService cityService;
    private final List<WeatherService> weatherServices;


    public WeatherDto getWeatherByCity(String cityName) throws IncorrectServiceNameException, IncorrectСityNameException, InternalErrorException {
        return getWeatherByCityAndService(cityName, defaultService);
    }

    public WeatherDto getWeatherByCityAndService(String cityName, String serviceName) throws IncorrectServiceNameException, IncorrectСityNameException, InternalErrorException {
        CityEntity city = cityService.findByName(cityName);
        WeatherService weatherService = getWeatherService(serviceName);
        return weatherService.getWeatherByLongitudeAndLatitude(city.getLongitude(), city.getLatitude()).setCity(city.getName());
    }

    private WeatherService getWeatherService(String serviceName) throws IncorrectServiceNameException {
        return weatherServices.stream().filter(weatherService -> {
                    WeatherServiceAnnotation weatherServiceAnnotation = weatherService.getClass().getAnnotation(WeatherServiceAnnotation.class);
                    return weatherServiceAnnotation.value().equalsIgnoreCase(serviceName);
                })
                .findFirst()
                .orElseThrow(() -> new IncorrectServiceNameException());
    }


}
