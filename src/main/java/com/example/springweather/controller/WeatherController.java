package com.example.springweather.controller;

import com.example.springweather.service.WeatherServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {

    private final String NOT_FOUND = "Error 404 - Not Found. Вы можете получить ошибку 404, если данные с запрошенными" +
            " параметрами (`cityName`) не существуют в базе данных сервиса. Вы не должны повторять тот же запрос.";
    private final String BAD_REQUEST = "Error 400 - Bad Request. Вы можете получить ошибку 400, если в запросе " +
            "отсутствуют какие-либо обязательные параметры, либо некоторые параметры запроса имеют неверный формат " +
            "или значения выходят за пределы допустимого диапазона.";
    private final WeatherServiceImpl weatherService;

    public WeatherController(WeatherServiceImpl weatherServiceImpl) {
        this.weatherService = weatherServiceImpl;
    }

    @GetMapping("{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable String cityName, @RequestParam(required = false) String serviceName) {
        Map<String, String> weatherByCity;
        if (serviceName != null) {
            weatherByCity = weatherService.getWeatherByCityAndService(cityName, serviceName);
        } else {
            weatherByCity = weatherService.getWeatherByCity(cityName);
        }
        if (weatherByCity != null) {
            return new ResponseEntity<>(weatherByCity, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("Exception", NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        /*return new ResponseEntity<>("Exception", HttpStatus.BAD_REQUEST);*/
    }
}
