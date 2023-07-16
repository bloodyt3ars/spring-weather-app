package com.example.springweather.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Weather",
                description = "Проект по получению данных о погоде по названию города", version = "1.0.0",
                contact = @Contact(
                        name = "Antonov Vladimir",
                        url = "https://t.me/bloodyt3ars"
                )
        )
)
public class OpenApiConfig {
}
