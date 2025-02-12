package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * Tests the WeatherController class
 */
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc is used to simulate HTTP requests

    @MockBean
    private WeatherService weatherService;  // Mock the WeatherService

    /* Tests for DaylightHours method */

    // Test if Daylight Hours works generically
    @Test
    void testDaylightHours() throws Exception {
        String city1 = "London";
        String city2 = "Paris";

        // Mock the service calls to return specific values
        when(weatherService.daylightMinutesByCity(city1)).thenReturn(600L);  // Mock 10 hours for London
        when(weatherService.daylightMinutesByCity(city2)).thenReturn(720L);  // Mock 12 hours for Paris

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/d-hours/{city1}/{city2}", city1, city2))
                .andExpect(status().isOk())
                .andExpect(content().string(city2));  // Expect Paris to have the longest daylight hours
    }

    // Tests if DaylightHours works if one city is fake
    @Test 
    void testDaylightHoursOneFakeCity() throws Exception {
        String city1 = "London";
        String city2 = "Fake";

        // Mock the service calls to return specific values
        when(weatherService.daylightMinutesByCity(city1)).thenReturn(600L);  // Mock 10 hours for London
        when(weatherService.daylightMinutesByCity(city2)).thenReturn(-1L);  // Mock -1 for the fake city

        mockMvc.perform(get("/d-hours/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No data for city: "+ city2));
    }

    // Tests if DaylightHours works if one both cities fake
    @Test 
    void testDaylightHoursTwoFakeCities() throws Exception{
        String city1 = "Fake";
        String city2 = "Fake2";

        // Mock the service calls to return specific values
        when(weatherService.daylightMinutesByCity(city1)).thenReturn(-1L);  // Mock -1 for fake city
        when(weatherService.daylightMinutesByCity(city2)).thenReturn(-1L);  // Mock -1 for the fake city

        mockMvc.perform(get("/d-hours/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No data for both cities: "+ city1 + ", " + city2));
    }

    // Tests if DaylightHours works if both cities are the same.
    @Test
    void testDaylightHoursSameCities() throws Exception{
        String city1 = "London";
        String city2 = "London";

        mockMvc.perform(get("/d-hours/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Both cities are the same: " + city1));
    }

    // Tests if DaylightHours works if the daylight hours are the exact same.
    @Test
    void testDaylightHoursSameTimes() throws Exception {
        String city1 = "London";
        String city2 = "Paris";

        // Mock the service calls to return specific values
        when(weatherService.daylightMinutesByCity(city1)).thenReturn(600L);  // Mock 10 hours for London
        when(weatherService.daylightMinutesByCity(city2)).thenReturn(600L);  // Mock 10 hours for Paris

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/d-hours/{city1}/{city2}", city1, city2))
                .andExpect(status().isOk())
                .andExpect(content().string("Both cities have the same daylight hours: " + city1 + ", " + city2));  // Expect Paris to have the longest daylight hours
    }   

    /* Tests for isRaining method */
    
    // Tests if isRaining works geenrically.
    @Test
    void testIsRaining() throws Exception {
        String city1 = "London";
        String city2 = "Dubai";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(true);  // Mock true for London
        when(weatherService.isRainingByCity(city2)).thenReturn(false);  // Mock false for Dubai

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isOk())
                .andExpect(content().string(city1));  // Expect London to be raining
    }

    // Tests if isRaining works if both the cities are the same.
    @Test
    void testIsRainingSameCitiesRaining() throws Exception {
        String city1 = "London";
        String city2 = "London";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(true);  // Mock true for London

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Both cities are the same: " + city1 + " and it is raining."));  // Expect London to be raining
    }

    // Test is isRaining works if the cities are the same.
    @Test
    void testIsRainingSameCitiesNotRaining() throws Exception {
        String city1 = "Dubai";
        String city2 = "Dubai";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(false);  // Mock false for Dubai

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Both cities are the same: " + city1 + " and it is not raining."));  // Expect London to be raining
    }

    // Test if isRaining works is there is one fake city.
    @Test
    void testIsRainingFakeCity() throws Exception {
        String city1 = "Dubai";
        String city2 = "fake";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(false);  // Mock false for Dubai
        when(weatherService.isRainingByCity(city2)).thenReturn(null);  // Mock null for fake city

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No rain data for city: "+ city2));  // Expect London to be raining
    }


    // Test if isRaining works for two fake cities.
    @Test
    void testIsRainingTwoFakeCities() throws Exception {
        String city1 = "fake";
        String city2 = "fake2";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(null);  // Mock null for fake city
        when(weatherService.isRainingByCity(city2)).thenReturn(null);  // Mock null for fake city 2

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No rain data for both cities: "+ city1 + ", " + city2));  // Expect London to be raining
    }


    // Test is isRaining works if both the cities are fake ad the same.
    @Test
    void testIsRainingTwoSameFakeCities() throws Exception {
        String city1 = "fake";
        String city2 = "fake";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(null);  // Mock null for fake city

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Both cities are the same: " + city1 + " and there is no rain data"));  // Expect London to be raining
    }


    // Test if isRaining works if it is currently raining in both cities.
    @Test
    void testIsRainingBothCitiesRaining() throws Exception {
        String city1 = "London";
        String city2 = "Paris";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(true);  // Mock true for London
        when(weatherService.isRainingByCity(city2)).thenReturn(true);  // Mock true for Paris

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isOk())
                .andExpect(content().string("Raining in both cities: " + city1 + ", " + city2));  // Expect London to be raining
    }


    // Test if isRaining works if in both cities it is not raining.
    @Test
    void testIsRainingBothCitiesNotRaining() throws Exception {
        String city1 = "London";
        String city2 = "Paris";

        // Mock the service calls to return specific values
        when(weatherService.isRainingByCity(city1)).thenReturn(false);  // Mock false for London
        when(weatherService.isRainingByCity(city2)).thenReturn(false);  // Mock false for Paris

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/rain/{city1}/{city2}", city1, city2))
                .andExpect(status().isOk())
                .andExpect(content().string("Not raining in either city"));  // Expect London to be raining
    }
}
