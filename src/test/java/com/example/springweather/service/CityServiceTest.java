package com.example.springweather.service;

import com.example.springweather.entity.City;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {
    private final String CITY_NAME = "Москва";
    private final String LATITUDE = "55.7504461";
    private final String LONGITUDE = "37.6174943";
    private final String DISPLAY_NAME = "Москва, Российская Федерация";
    @Mock
    private CityRepository cityRepository;

    @Mock
    private RestTemplate restTemplate;

    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityService = new CityService(cityRepository, restTemplate);
    }

    @Test
    void findByNameExistingCityFromRepository() throws IncorrectСityNameException {
        City expectedCity = new City();
        expectedCity.setName(CITY_NAME);
        when(cityRepository.findByName(CITY_NAME)).thenReturn(expectedCity);

        City actualCity = cityService.findByName(CITY_NAME);

        assertEquals(expectedCity, actualCity);
        verify(cityRepository, times(1)).findByName(CITY_NAME);
    }
    @Test
    void findByNameNonExistingCityReturnsCityFromAPIAndSavesToRepository() throws IncorrectСityNameException {

        City createdCity = new City();
        createdCity.setName(CITY_NAME);
        createdCity.setLongitude(LONGITUDE);
        createdCity.setLatitude(LATITUDE);
        Map<String, Object> coordinates = createMockCityCoordinates();

        when(cityRepository.findByName(CITY_NAME)).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(Collections.singletonList(coordinates));
        when(cityRepository.save(any(City.class))).thenReturn(createdCity);

        City result = cityService.findByName(CITY_NAME);

        assertEquals(createdCity, result);
        verify(cityRepository, times(1)).findByName(CITY_NAME);
        verify(cityRepository, times(1)).save(createdCity);
        verify(cityRepository,times(1)).exists(Example.of(createdCity));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(List.class));
        verifyNoMoreInteractions(cityRepository, restTemplate);
    }
    @Test
    void findByNameNotExistingCityAPIReturnsNullThrowsIncorrectCityNameException() {
        when(cityRepository.findByName(CITY_NAME)).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(null);

        assertThrows(IncorrectСityNameException.class, () -> cityService.findByName(CITY_NAME));

        verify(cityRepository, times(1)).findByName(CITY_NAME);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(List.class));
        verifyNoMoreInteractions(cityRepository, restTemplate);
    }

    @Test
    void findByName_NonExistingCity_APIReturnsEmptyResponse_ThrowsIncorrectCityNameException() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(IncorrectСityNameException.class, () ->
                cityService.findByName(CITY_NAME));

        verify(cityRepository, times(1)).findByName(CITY_NAME);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(List.class));
    }

    private Map<String, Object> createMockCityCoordinates() {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("lat", LATITUDE);
        coordinates.put("lon", LONGITUDE);
        coordinates.put("display_name", DISPLAY_NAME);
        return coordinates;
    }

    private List<Map<String, Object>> createMockApiResponse(Map<String, Object> coordinates) {
        return List.of(coordinates);
    }
}