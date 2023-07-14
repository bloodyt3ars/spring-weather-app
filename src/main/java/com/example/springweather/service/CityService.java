package com.example.springweather.service;

import com.example.springweather.entity.City;
import com.example.springweather.repository.CityRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CityService {


    private final String CITY_API_URL = "https://nominatim.openstreetmap.org/search?format=json&accept-language=ru&city=";
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City findByName(String cityName) throws IllegalArgumentException{
        City city = cityRepository.findByName(cityName);
        if (city != null) {
            return city;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = CITY_API_URL + cityName;
            List<Map<String, Object>> response = restTemplate.getForObject(apiUrl, List.class);
            if (response.size()==0){
                throw new IllegalArgumentException("Incorrect city name");
            }
            else {
                Map<String, Object> dataMap = (Map) response.get(0);
                String latitude = (String) dataMap.get("lat");
                String longitude = (String) dataMap.get("lon");
                cityName = dataMap.get("display_name").toString().split(", ")[0];
                city = new City();
                city.setName(cityName);
                city.setLatitude(latitude);
                city.setLongitude(longitude);
                return cityRepository.save(city);
            }
        }
    }


}
