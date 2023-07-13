package com.example.springweather.service;

import com.example.springweather.entity.City;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OpenWeatherMapService implements WeatherService {
    /*lat={lat}&lon={lon}&exclude={part}&*/
    private final String TOKEN = "d0d0d6e1373286a357f48b4cc78afbf1";
    private final String CITY_API_URL = "https://api.openweathermap.org/data/3.0/onecall?appid="+TOKEN;

    @Override
    public Double getTemperatureByCity(City city) {
        RestTemplate restTemplate = new RestTemplate();
        String longitude = city.getLongitude();
        String latitude = city.getLatitude();
        String apiUrl = CITY_API_URL +"&lat=" +latitude+"&lon="+longitude;
        restTemplate.getForObject(apiUrl, Map.class);
        return null;
    }
}
