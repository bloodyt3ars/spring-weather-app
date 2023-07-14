package com.example.springweather.service;

import com.example.springweather.entity.City;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenWeatherMapService implements WeatherService {
    private final String TOKEN = "cfbaaf9ee8f8faf75e7e55587ae3637d";

    private final String CITY_API_URL = "https://api.openweathermap.org/data/2.5/onecall?units=metric&lang=ru&exclude=minutely,hourly,daily,alerts&appid=" + TOKEN;

    @Override
    public Map getTemperatureByCity(City city) {
        Map<String, String> responseMap = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String longitude = city.getLongitude();
        String latitude = city.getLatitude();
        String apiUrl = CITY_API_URL + "&lat=" + latitude + "&lon=" + longitude;
        Map currentWeatherMap = (Map) restTemplate.getForObject(apiUrl, Map.class).get("current");
        String temperature = String.valueOf(currentWeatherMap.get("temp"));
        List weatherList = (List) currentWeatherMap.get("weather");
        Map descriptionMap = (Map) weatherList.get(0);
        String description = (String) descriptionMap.get("description");
        responseMap.put("City", city.getName());
        responseMap.put("lat", latitude);
        responseMap.put("lon", longitude);
        responseMap.put("temp", temperature);
        responseMap.put("description", description);
        return responseMap;
    }
}
