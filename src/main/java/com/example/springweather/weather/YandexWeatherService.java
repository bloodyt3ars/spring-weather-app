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

@WeatherServiceAnnotation(value = "yandexweather")
@Service
public class YandexWeatherService implements WeatherService {
    @Value(value = "${weather.yandexweather.token}")
    private String token;
    private final String WEATHER_API_URL = "https://api.weather.yandex.ru/v2/forecast?" +
            "lang=ru_RU" +
            "&limit=1" +
            "&hours=false";
    private final RestTemplate template;

    public YandexWeatherService(RestTemplate template) {
        this.template = template;
    }


    @Override
    public WeatherDto getWeatherByLongitudeAndLatitude(String longitude, String latitude) throws InternalErrorException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Yandex-API-Key", token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            String apiUrl = WEATHER_API_URL + "&lat=" + latitude + "&lon=" + longitude;
            Map body = template.exchange(apiUrl, HttpMethod.GET, entity, Map.class).getBody();
            JsonNode node = objectMapper.readTree(objectMapper.writeValueAsString(body));
            String description = node.get("fact").get("condition").textValue();
            String temp = node.get("fact").get("temp").toString();
            String lat = node.get("info").get("lat").toString();
            String lon = node.get("info").get("lon").toString();

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
