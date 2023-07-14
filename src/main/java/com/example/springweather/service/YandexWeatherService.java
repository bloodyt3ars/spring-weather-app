package com.example.springweather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class YandexWeatherService implements WeatherService {
    @Value(value = "${weather.yandexweather.token}")
    private  String token;
    private final String WEATHER_API_URL = "https://api.weather.yandex.ru/v2/forecast?" +
            "lang=ru_RU" +
            "&limit=1" +
            "&hours=false";


    @Override
    public Map getWeatherByLongitudeAndLatitude(String longitude, String latitude) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", token);
        HttpEntity entity = new HttpEntity<>(headers);
        String apiUrl = WEATHER_API_URL + "&lat=" + latitude + "&lon=" + longitude;
        ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
        if (mapResponseEntity.getStatusCode().is2xxSuccessful()) {
            Map currentWeatherMap = (Map) mapResponseEntity.getBody().get("fact");
            String temperature = String.valueOf(currentWeatherMap.get("temp"));
            String condition = String.valueOf(currentWeatherMap.get("condition"));
            Map<String, String> weatherMap = new LinkedHashMap<>();
            weatherMap.put("temperature", temperature);
            weatherMap.put("condition", condition);
            return weatherMap;
        } else {
            return null;
        }
    }
}