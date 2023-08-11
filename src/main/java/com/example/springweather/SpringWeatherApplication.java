package com.example.springweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWeatherApplication.class, args);
        /*SpringApplication.run(SpringWeatherApplication.class, args).getBean(YandexWeatherService.class).getWeatherByLongitudeAndLatitude( "37.6174943", "55.7504461");*/
        /*SpringApplication.run(SpringWeatherApplication.class, args).getBean(OpenWeatherMapService.class).getWeatherByLongitudeAndLatitude( "37.6174943", "55.7504461");*/
        /*SpringApplication.run(SpringWeatherApplication.class, args).getBean(CityService.class).findByName("СПБ");*/
    }

}
