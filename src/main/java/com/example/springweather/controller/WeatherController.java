package com.example.springweather.controller;

import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.service.WeatherServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("weather")
@Tag(name = "weather", description = "В этом разделе можно получить сведение о погоде в желаемом городе")
public class WeatherController {

    private final WeatherServiceFacade weatherServiceFacade;

    public WeatherController(WeatherServiceFacade weatherServiceFacade) {
        this.weatherServiceFacade = weatherServiceFacade;
    }

    @Operation(summary = "Получить погоду по его названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все прошло успешно, город найден",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"City\":\"Санкт-Петербург\",\"lat\":\"59.938732\",\"lon\":\"30.316229\",\"weather\":{\"temperature\":\"24.43\",\"condition\":\"ясно\"}}"))}),
            @ApiResponse(responseCode = "404", description = "Incorrect city name",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Unknown service name",
                    content = @Content)})
    @GetMapping("{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable
                                        @Parameter(description = "Название города", example = "Санкт-Петербург")
                                        String cityName,
                                        @RequestParam(required = false)
                                        @Parameter(description = "Название сервиса погоды", example = "yandexweather", required = false)
                                        String serviceName)
            throws IncorrectСityNameException, IncorrectServiceNameException {
        Map<String, Object> weatherByCity;
        if (serviceName != null) {
            weatherByCity = weatherServiceFacade.getWeatherByCityAndService(cityName, serviceName);
        } else {
            weatherByCity = weatherServiceFacade.getWeatherByCity(cityName);
        }
        return new ResponseEntity<>(weatherByCity, HttpStatus.OK);

    }

    @ExceptionHandler(IncorrectServiceNameException.class)
    public ResponseEntity<String> handleIncorrectServiceNameException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IncorrectСityNameException.class)
    public ResponseEntity<String> handleIncorrectСityNameException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
