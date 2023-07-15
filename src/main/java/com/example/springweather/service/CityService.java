package com.example.springweather.service;

import com.example.springweather.entity.City;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CityService {


    private final String CITY_API_URL = "https://nominatim.openstreetmap.org/search?format=json&accept-language=ru&city=";
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City findByName(String cityName) throws IncorrectСityNameException {
        City city = cityRepository.findByName(cityName);
        if (city != null) {
            return city;
        } else {
            Map<String, Object> coordinate = getCityCoordinateFromAPI(cityName);
            if (coordinate == null) {
                throw new IncorrectСityNameException();
            } else {
                city = createCity(coordinate);
                return cityRepository.save(city);
            }
        }
    }

    private Map<String, Object> getCityCoordinateFromAPI(String cityName) throws IncorrectСityNameException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = CITY_API_URL + cityName;
        List<Map<String, Object>> response = restTemplate.getForObject(apiUrl, List.class);
        if (response != null && !response.isEmpty()) {
            return (Map) response.get(0);
        }
        return null;
    }
    public City createCity(Map<String, Object> coordinates){
        City city = new City();
        String latitude = (String) coordinates.get("lat");
        String longitude = (String) coordinates.get("lon");
        String cityName = coordinates.get("display_name").toString().split(", ")[0];
        city = new City();
        city.setName(cityName);
        city.setLatitude(latitude);
        city.setLongitude(longitude);
        return city;
    }
}
