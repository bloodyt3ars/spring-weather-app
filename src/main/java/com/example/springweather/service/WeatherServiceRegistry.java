package com.example.springweather.service;

import com.example.springweather.exception.IncorrectServiceNameException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeatherServiceRegistry {
    private final ApplicationContext applicationContext;

    public WeatherServiceRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public WeatherService getWeatherService(String serviceName) throws IncorrectServiceNameException {
        Map<String, WeatherService> weatherServices = applicationContext.getBeansOfType(WeatherService.class);
        for (WeatherService weatherService : weatherServices.values()) {
            WeatherServiceAnnotation weatherServiceAnnotation = weatherService.getClass().getAnnotation(WeatherServiceAnnotation.class);
            if (weatherServiceAnnotation.value().equalsIgnoreCase(serviceName)) {
                return weatherService;
            }
        }
        throw new IncorrectServiceNameException();
    }
}
