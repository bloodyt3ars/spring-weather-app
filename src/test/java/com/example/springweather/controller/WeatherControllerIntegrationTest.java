package com.example.springweather.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.springweather.exception.IncorrectServiceNameException;
import com.example.springweather.exception.IncorrectСityNameException;
import com.example.springweather.service.WeatherServiceFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WeatherServiceFacade weatherServiceFacade;

    @Test
    public void testGetWeatherByCity() throws Exception {
        // Prepare test data
        String cityName = "London";
        Map<String, Object> weatherData = new HashMap<>();
        weatherData.put("temperature", "25°C");
        weatherData.put("condition", "Sunny");

        // Mock the behavior of WeatherServiceFacade
        when(weatherServiceFacade.getWeatherByCity(cityName)).thenReturn(weatherData);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value("25°C"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condition").value("Sunny"));
    }

    @Test
    public void testGetWeatherByCityAndService() throws Exception {
        // Prepare test data
        String cityName = "London";
        String serviceName = "openweathermap";
        Map<String, Object> weatherData = new HashMap<>();
        weatherData.put("temperature", "25°C");
        weatherData.put("condition", "Sunny");

        // Mock the behavior of WeatherServiceFacade
        when(weatherServiceFacade.getWeatherByCityAndService(cityName, serviceName)).thenReturn(weatherData);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .param("serviceName", serviceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value("25°C"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condition").value("Sunny"));
    }

    @Test
    public void testGetWeatherByIncorrectCityName() throws Exception {
        // Prepare test data
        String cityName = "InvalidCity";

        // Mock the behavior of WeatherServiceFacade
        when(weatherServiceFacade.getWeatherByCity(cityName)).thenThrow(IncorrectСityNameException.class);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetWeatherByCityAndIncorrectServiceName() throws Exception {
        // Prepare test data
        String cityName = "London";
        String serviceName = "invalidservice";

        // Mock the behavior of WeatherServiceFacade
        when(weatherServiceFacade.getWeatherByCityAndService(cityName, serviceName)).thenThrow(IncorrectServiceNameException.class);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{cityName}", cityName)
                        .param("serviceName", serviceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}