package com.example.springweather.controller;

import com.example.springweather.exception.ErrorResponse;
import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.service.WeatherServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {

    private final WeatherServiceFacade weatherService;

    public WeatherController(WeatherServiceFacade weatherServiceFacade) {
        this.weatherService = weatherServiceFacade;
    }

    @GetMapping("{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable String cityName, @RequestParam(required = false) String serviceName)
            throws IncorrectСityNameException, IncorrectServiceNameException {
        Map<String, String> weatherByCity;
        if (serviceName != null) {
            weatherByCity = weatherService.getWeatherByCityAndService(cityName, serviceName);
        } else {
            weatherByCity = weatherService.getWeatherByCity(cityName);
        }
        return new ResponseEntity<>(weatherByCity, HttpStatus.OK);

    }

    @ExceptionHandler({IncorrectServiceNameException.class, IncorrectСityNameException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
