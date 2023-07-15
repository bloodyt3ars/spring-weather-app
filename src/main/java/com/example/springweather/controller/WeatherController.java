package com.example.springweather.controller;

import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.service.WeatherServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {

    private final WeatherServiceFacade weatherServiceFacade;

    public WeatherController(WeatherServiceFacade weatherServiceFacade) {
        this.weatherServiceFacade = weatherServiceFacade;
    }

    @GetMapping("{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable String cityName, @RequestParam(required = false) String serviceName)
            throws IncorrectСityNameException, IncorrectServiceNameException {
        Map<String, Object> weatherByCity;
        if (serviceName != null) {
            weatherByCity = weatherServiceFacade.getWeatherByCityAndService(cityName, serviceName);
        } else {
            weatherByCity = weatherServiceFacade.getWeatherByCity(cityName);
        }
        return new ResponseEntity<>(weatherByCity, HttpStatus.OK);

    }

    @ExceptionHandler({IncorrectServiceNameException.class, IncorrectСityNameException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
