package com.example.springweather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class YandexWeatherServiceTest {
    @Mock
    private RestTemplate restTemplate;
    private YandexWeatherService yandexWeatherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        yandexWeatherService = new YandexWeatherService(restTemplate);
    }

    @Test
    public void testGetWeatherByLongitudeAndLatitudeSuccessful() {

        String latitude = "55.7504461";
        String longitude = "37.6174943";
        String token = "test";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String apiUrl = "https://api.weather.yandex.ru/v2/forecast?lang=ru_RU&limit=1&hours=false&lat=20.456&lon=10.123";
        Map<String, Object> weatherData = new LinkedHashMap<>();
        weatherData.put("temp", 25.5);
        weatherData.put("condition", "sunny");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("fact", weatherData);

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), eq(entity), eq(Map.class)))
                .thenReturn(responseEntity);


        Map<String, String> weather = yandexWeatherService.getWeatherByLongitudeAndLatitude(longitude, latitude);


        assertEquals(2, weather.size());
        assertEquals("25.5", weather.get("temperature"));
        assertEquals("sunny", weather.get("condition"));


        verify(restTemplate, times(1)).exchange(eq(apiUrl), eq(HttpMethod.GET), eq(entity), eq(Map.class));
    }


}