package com.example.springweather.weather;

import com.example.springweather.exception.InternalErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public WeatherDto getWeatherByLongitudeAndLatitude(String longitude, String latitude) throws InternalErrorException {
        try {
            String apiUrl = WEATHER_API_URL + token + "&lat=" + latitude + "&lon=" + longitude;
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ObjectMapper objectMapper = new ObjectMapper();
            Map body = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class).getBody();
            JsonNode node = objectMapper.readTree(objectMapper.writeValueAsString(body));
            String description = node.get("current").get("weather").get(0).get("description").textValue();
            String temp = node.get("current").get("temp").toString();
            String lat = node.get("lat").toString();
            String lon = node.get("lon").toString();

            return new WeatherDto()
                    .setLatitude(lat)
                    .setLongitude(lon)
                    .setDescription(description)
                    .setTemperature(temp);
        } catch (Exception e) {
            throw new InternalErrorException();
        }
    }
}
