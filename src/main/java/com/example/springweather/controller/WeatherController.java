package com.example.springweather.controller;

import com.example.springweather.exception.ErrorResponse;
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
    public ResponseEntity<?> getWeather(@PathVariable String cityName, @RequestParam(required = false) String serviceName) {
        Map<String, String> weatherByCity;
        if (serviceName != null) {
            weatherByCity = weatherService.getWeatherByCityAndService(cityName, serviceName);
        } else {
            weatherByCity = weatherService.getWeatherByCity(cityName);
        }
        return new ResponseEntity<>(weatherByCity, HttpStatus.OK);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Exception", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Exception", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
