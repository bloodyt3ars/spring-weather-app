package com.example.springweather.city;

import com.example.springweather.exception.IncorrectСityNameException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CityService {
    private final String CITY_API_URL = "https://nominatim.openstreetmap.org/search?format=json&accept-language=ru&city=";
    private final CityRepository repository;
    private final RestTemplate template;
    private final CityMapper mapper;


    @Transactional
    public CityEntity findByName(String cityName) throws IncorrectСityNameException {
        CityEntity city = repository.findByName(cityName).orElse(mapper.toEntity(getCityFromApi(cityName)));
        if (repository.exists(Example.of(city))) {
            return repository.findByName(city.getName()).orElseThrow(() -> new IncorrectСityNameException());
        } else {
            return repository.save(city);
        }
    }

    private CityDto getCityFromApi(String cityName) throws IncorrectСityNameException {
        String apiUrl = CITY_API_URL + cityName;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return template.getForObject(apiUrl, CityDto[].class)[0];
        } catch (Exception e) {
            throw new IncorrectСityNameException();
        }
    }
}
