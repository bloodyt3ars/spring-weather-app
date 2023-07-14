package com.example.springweather;

import com.example.springweather.entity.City;
import com.example.springweather.service.CityService;
import com.example.springweather.service.OpenWeatherMapService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWeatherApplication.class, args);
        /*SpringApplication.run(SpringWeatherApplication.class, args).getBean(CityService.class).findByName("СПБ");
        SpringApplication.run(SpringWeatherApplication.class, args).getBean(OpenWeatherMapService.class).getTemperatureByCity(new City(1L, "Москва", "55.7504461", "37.6174943"));
*/    }

}
