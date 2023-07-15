package com.example.springweather.service;

import com.example.springweather.exception.IncorrectServiceNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class WeatherServiceRegistryTest {

    @Mock
    private ApplicationContext applicationContext;

    private WeatherServiceRegistry weatherServiceRegistry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherServiceRegistry = new WeatherServiceRegistry(applicationContext);
    }


    @Test
    void getWeatherServiceByExistingServiceName() throws IncorrectServiceNameException {

        WeatherService mockYandexWeatherService = mock(YandexWeatherService.class);
        WeatherService mockOpenWeatherMapService = mock(OpenWeatherMapService.class);

        Map<String, WeatherService> weatherServices = new HashMap<>();
        weatherServices.put("yandexweather", mockYandexWeatherService);
        weatherServices.put("openweathermap", mockOpenWeatherMapService);

        when(applicationContext.getBeansOfType(WeatherService.class)).thenReturn(weatherServices);

        String serviceNameYandex = "yandexweather";
        String serviceNameOpenWeather = "openweathermap";
        WeatherService actualYandexWeatherService = weatherServiceRegistry.getWeatherService(serviceNameYandex);
        WeatherService actualOpenWeatherService = weatherServiceRegistry.getWeatherService(serviceNameOpenWeather);
        assertEquals(mockYandexWeatherService, actualYandexWeatherService);
        assertEquals(mockOpenWeatherMapService, actualOpenWeatherService);
    }

    @Test()
    void getWeatherServiceByNonExistingServiceName() {
        assertThrows(IncorrectServiceNameException.class, () ->
                weatherServiceRegistry.getWeatherService("nonExistingService"));
    }
    @Test
    void getWeatherServiceWithNullServiceName() {
        assertThrows(IncorrectServiceNameException.class, () ->
                weatherServiceRegistry.getWeatherService(null));
    }
}