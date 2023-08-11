package com.example.springweather.weather;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WeatherDto {
    @Schema(description = "Название города", example = "Санкт-Петербург")
    private String city;

    @Schema(description = "Широта", example = "59.938732")
    private String latitude;

    @Schema(description = "Широта", example = "30.316229")
    private String longitude;

    @Schema(description = "Температура", example = "21.04")
    private String temperature;

    @Schema(description = "Описание", example = "Небольшая облачность")
    private String description;
}
