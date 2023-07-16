package com.example.springweather.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestPropertySource("/application-test.properties")
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherController weatherController;

    @Test
    public void testGetWeatherByCity() throws Exception {

        String cityName = "London";

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.City").value("Лондон"))
                .andExpect(jsonPath("$.lat").value("51.5073359"))
                .andExpect(jsonPath("$.lon").value("-0.12765"))
                .andExpect(jsonPath("$.weather.temperature").exists())
                .andExpect(jsonPath("$.weather.condition").exists());
    }

    @Test
    public void testGetWeatherByCityAndService() throws Exception {

        String cityName = "London";
        String openServiceName = "openweathermap";
        String yandexServiceName = "yandexweather";

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .param("serviceName", openServiceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.City").value("Лондон"))
                .andExpect(jsonPath("$.lat").value("51.5073359"))
                .andExpect(jsonPath("$.lon").value("-0.12765"))
                .andExpect(jsonPath("$.weather.temperature").exists())
                .andExpect(jsonPath("$.weather.condition").exists());

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .param("serviceName", yandexServiceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.City").value("Лондон"))
                .andExpect(jsonPath("$.lat").value("51.5073359"))
                .andExpect(jsonPath("$.lon").value("-0.12765"))
                .andExpect(jsonPath("$.weather.temperature").exists())
                .andExpect(jsonPath("$.weather.condition").exists());


    }

    @Test
    public void testGetWeatherByIncorrectCityName() throws Exception {

        String cityName = "РандомныйГородСНевернымНазванием";

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Incorrect city name"));
    }

    @Test
    public void testGetWeatherByCityAndIncorrectServiceName() throws Exception {

        String cityName = "London";
        String serviceName = "НеправильноеИмяСервиса";


        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .param("serviceName", serviceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown service name"));
    }

}