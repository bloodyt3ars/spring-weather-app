package com.example.springweather.service;

import com.example.springweather.entity.City;
import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceFacadeTest {
    private final String SERVICE_NAME = "openweathermap";
    private final String CITY_NAME = "Москва";
    private final String LATITUDE = "55.7504461";
    private final String LONGITUDE = "37.6174943";

    @Mock
    private WeatherServiceRegistry weatherServiceRegistry;
    @Mock
    private CityService cityService;
    @Mock
    private WeatherService openWeatherMapService;
    @Mock
    private WeatherService yandexWeatherService;

    private WeatherServiceFacade weatherServiceFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherServiceFacade = new WeatherServiceFacade(weatherServiceRegistry, cityService, openWeatherMapService, yandexWeatherService);
    }

    @Test
    void getWeatherByExistingCity() throws IncorrectСityNameException, IncorrectServiceNameException {
        City city = new City();
        city.setName(CITY_NAME);
        city.setLongitude(LONGITUDE);
        city.setLatitude(LATITUDE);

        Map<String, Object> weatherMap = new LinkedHashMap<>();
        weatherMap.put("temperature", "25");
        weatherMap.put("condition", "sunny");

        when(cityService.findByName(CITY_NAME)).thenReturn(city);
        when(openWeatherMapService.getWeatherByLongitudeAndLatitude(city.getLongitude(), city.getLatitude())).thenReturn(weatherMap);

        Map<String, Object> expectedResponseMap = new LinkedHashMap<>();
        expectedResponseMap.put("City", CITY_NAME);
        expectedResponseMap.put("lat", city.getLatitude());
        expectedResponseMap.put("lon", city.getLongitude());
        expectedResponseMap.put("weather", weatherMap);


        Map<String, Object> result = weatherServiceFacade.getWeatherByCity(CITY_NAME);


        verify(cityService, times(1)).findByName(CITY_NAME);
        /*verify(openWeatherMapService, times(1)).getWeatherByLongitudeAndLatitude(city.getLongitude(), city.getLatitude());*/
        verifyNoMoreInteractions(cityService, openWeatherMapService);
    }

    @Test
    void getWeatherByNonExistingCity() throws IncorrectServiceNameException, IncorrectСityNameException {
        when(cityService.findByName(CITY_NAME)).thenReturn(null);

        Map<String, Object> result = weatherServiceFacade.getWeatherByCity(CITY_NAME);

        assertNull(result);
        verify(cityService, times(1)).findByName(CITY_NAME);
        verifyNoMoreInteractions(cityService, openWeatherMapService);
    }

    @Test
    void getWeatherByExistingCityAndService() throws IncorrectServiceNameException, IncorrectСityNameException {
        City city = new City();
        city.setName(CITY_NAME);
        city.setLongitude("37.6174943");
        city.setLatitude("55.7504461");

        Map<String, Object> weatherMap = new LinkedHashMap<>();
        weatherMap.put("temperature", "25");
        weatherMap.put("condition", "sunny");

        when(cityService.findByName(CITY_NAME)).thenReturn(city);
        when(openWeatherMapService.getWeatherByLongitudeAndLatitude(city.getLongitude(), city.getLatitude())).thenReturn(weatherMap);

        Map<String, Object> expectedResponseMap = new LinkedHashMap<>();
        expectedResponseMap.put("City", CITY_NAME);
        expectedResponseMap.put("lat", city.getLatitude());
        expectedResponseMap.put("lon", city.getLongitude());
        expectedResponseMap.put("weather", weatherMap);

        Map<String, Object> result = weatherServiceFacade.getWeatherByCityAndService(CITY_NAME, SERVICE_NAME);


        verify(cityService, times(1)).findByName(CITY_NAME);
        /*verify(openWeatherMapService, times(1)).getWeatherByLongitudeAndLatitude(city.getLongitude(), city.getLatitude());*/
        verifyNoMoreInteractions(cityService, openWeatherMapService);
    }

    @Test
    void getWeatherByNonExistingCityAndService() throws IncorrectServiceNameException, IncorrectСityNameException {
        when(cityService.findByName(CITY_NAME)).thenReturn(null);

        Map<String, Object> result = weatherServiceFacade.getWeatherByCityAndService(CITY_NAME, SERVICE_NAME);

        assertNull(result);
        verify(cityService, times(1)).findByName(CITY_NAME);
        verifyNoMoreInteractions(cityService, openWeatherMapService);
    }

    @Test
    void getWeatherByExistingCityNonExistingService() throws IncorrectServiceNameException, IncorrectСityNameException {
        City city = new City();
        city.setName(CITY_NAME);
        city.setLongitude("37.6174943");
        city.setLatitude("55.7504461");

        when(cityService.findByName(CITY_NAME)).thenReturn(city);
        when(weatherServiceRegistry.getWeatherService("nonExistingService")).thenThrow(IncorrectServiceNameException.class);

        assertThrows(IncorrectServiceNameException.class, () -> weatherServiceFacade.getWeatherByCityAndService(CITY_NAME, "nonExistingService"));

        verify(cityService, times(1)).findByName(CITY_NAME);
        verify(weatherServiceRegistry, times(1)).getWeatherService("nonExistingService");
        verifyNoMoreInteractions(cityService, openWeatherMapService, weatherServiceRegistry);
    }
}