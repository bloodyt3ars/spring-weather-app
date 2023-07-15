package com.example.springweather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WeatherServiceAnnotation(value = "openweathermap")
@Service
public class OpenWeatherMapService implements WeatherService {
    @Value(value = "${weather.openweathermap.token}")
    private String token;

    private String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/onecall?" +
            "units=metric" +
            "&lang=ru" +
            "&exclude=minutely,hourly,daily,alerts" +
            "&appid=";
    private final RestTemplate restTemplate;

    public OpenWeatherMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map getWeatherByLongitudeAndLatitude(String longitude, String latitude) {
        String apiUrl = WEATHER_API_URL + token + "&lat=" + latitude + "&lon=" + longitude;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
        if (mapResponseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> currentWeatherMap = (Map) restTemplate.getForObject(apiUrl, Map.class).get("current");
            String temperature = String.valueOf(currentWeatherMap.get("temp"));
            String condition = (String) ((Map) ((List) currentWeatherMap.get("weather")).get(0)).get("description");
            Map<String, String> weatherMap = new LinkedHashMap<>();
            weatherMap.put("temperature", temperature);
            weatherMap.put("condition", condition);
            return weatherMap;
        } else {
            return null;
        }
    }
}
