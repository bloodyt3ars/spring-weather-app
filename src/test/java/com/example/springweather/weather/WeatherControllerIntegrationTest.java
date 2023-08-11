package com.example.springweather.weather;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestPropertySource("/application-test.properties")
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WeatherController weatherController;
    private static final String API_WEATHER = "/weather".concat("/{cityName}");

    @Test
    public void testGetWeatherByCity() throws Exception {

        String cityName = "Лондон";
        this.mockMvc.perform(get(API_WEATHER, cityName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Лондон"))
                .andExpect(jsonPath("$.latitude").value("51.5073"))
                .andExpect(jsonPath("$.longitude").value("-0.1277"))
                .andExpect(jsonPath("$.temperature").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void testGetWeatherByCityAndService() throws Exception {

        String cityName = "London";
        String openServiceName = "openweathermap";
        String yandexServiceName = "yandexweather";

        mockMvc.perform(get(API_WEATHER, cityName)
                        .queryParam("serviceName", openServiceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Лондон"))
                .andExpect(jsonPath("$.latitude").value("51.5073"))
                .andExpect(jsonPath("$.longitude").value("-0.1277"))
                .andExpect(jsonPath("$.temperature").exists())
                .andExpect(jsonPath("$.description").exists());

        mockMvc.perform(get(API_WEATHER, cityName)
                        .queryParam("serviceName", yandexServiceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Лондон"))
                .andExpect(jsonPath("$.latitude").value("51.5073359"))
                .andExpect(jsonPath("$.longitude").value("-0.12765"))
                .andExpect(jsonPath("$.temperature").exists())
                .andExpect(jsonPath("$.description").exists());


    }

    @Test
    public void testGetWeatherByIncorrectCityName() throws Exception {

        String cityName = "РандомныйГородСНевернымНазванием";

        mockMvc.perform(get(API_WEATHER, cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Incorrect city name"));
    }

    @Test
    public void testGetWeatherByCityAndIncorrectServiceName() throws Exception {

        String cityName = "London";
        String serviceName = "НеправильноеИмяСервиса";


        mockMvc.perform(get(API_WEATHER, cityName)
                        .queryParam("serviceName", serviceName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown service name"));
    }

}