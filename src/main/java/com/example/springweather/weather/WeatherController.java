package com.example.springweather.weather;

import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.exception.InternalErrorException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("weather")
@Tag(name = "weather", description = "В этом разделе можно получить сведение о погоде в желаемом городе")
public class WeatherController {
    private final WeatherServiceFacade weatherServiceFacade;

    public WeatherController(WeatherServiceFacade weatherServiceFacade) {
        this.weatherServiceFacade = weatherServiceFacade;
    }


    @Operation(description = "Получить погоду по названию города", responses = {
            @ApiResponse(responseCode = "200", description = "Все прошло успешно, город найден, погода получена", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WeatherDto.class))})
    })
    @GetMapping("{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable
                                        @Parameter(description = "Название города", example = "Санкт-Петербург")
                                        String cityName,
                                        @RequestParam(required = false)
                                        @Parameter(description = "Название сервиса погоды", example = "yandexweather", required = false)
                                        String serviceName)
            throws Exception {
        WeatherDto weatherByCity = serviceName != null
                ? weatherServiceFacade.getWeatherByCityAndService(cityName, serviceName)
                : weatherServiceFacade.getWeatherByCity(cityName);
        return new ResponseEntity<>(weatherByCity, HttpStatus.OK);
    }

    @ExceptionHandler(IncorrectServiceNameException.class)
    public ResponseEntity<String> handlerIncorrectServiceNameException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectСityNameException.class)
    public ResponseEntity<String> handlerIncorrectСityNameException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<String> handlerInternalErrorException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
