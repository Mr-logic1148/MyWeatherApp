package com.weatherapp.myweatherapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

class WeatherServiceTest {

    @Mock
    private VisualcrossingRepository weatherRepo; // Mock the repository

    @InjectMocks
    private WeatherServiceImpl weatherService; // Inject the mock into the service

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testForecastByCity() {
        // Arrange
        CityInfo mockCityInfo = new CityInfo();
        mockCityInfo.setAddress("London");
        when(weatherRepo.getByCity("London")).thenReturn(mockCityInfo);

        // Act
        CityInfo result = weatherService.forecastByCity("London");

        // Assert
        assertNotNull(result);
        assertEquals("London", result.getAddress());
        verify(weatherRepo, times(1)).getByCity("London");
    }

    @Test
    void testGetDaylightHours() {
        // Arrange
        CityInfo mockCityInfo = new CityInfo();
        CityInfo.CurrentConditions currentConditions = new CityInfo.CurrentConditions();
        currentConditions.setSunrise("06:00");
        currentConditions.setSunset("18:00");
        mockCityInfo.setCurrentConditions(currentConditions);
        when(weatherRepo.getByCity("London")).thenReturn(mockCityInfo);

        // Act
        int daylightHours = weatherService.getDaylightHours("London");

        // Assert
        assertEquals(12, daylightHours); // 18:00 - 06:00 = 12 hours
        verify(weatherRepo, times(1)).getByCity("London");
    }

    @Test
    void testIsRaining() {
        // Arrange
        CityInfo mockCityInfo = new CityInfo();
        CityInfo.CurrentConditions currentConditions = new CityInfo.CurrentConditions();
        currentConditions.setConditions("Rain");
        mockCityInfo.setCurrentConditions(currentConditions);
        when(weatherRepo.getByCity("London")).thenReturn(mockCityInfo);

        // Act
        boolean isRaining = weatherService.isRaining("London");

        // Assert
        assertTrue(isRaining);
        verify(weatherRepo, times(1)).getByCity("London");
    }

    @Test
    void testIsNotRaining() {
        // Arrange
        CityInfo mockCityInfo = new CityInfo();
        CityInfo.CurrentConditions currentConditions = new CityInfo.CurrentConditions();
        currentConditions.setConditions("Sunny");
        mockCityInfo.setCurrentConditions(currentConditions);
        when(weatherRepo.getByCity("London")).thenReturn(mockCityInfo);

        // Act
        boolean isRaining = weatherService.isRaining("London");

        // Assert
        assertFalse(isRaining);
        verify(weatherRepo, times(1)).getByCity("London");
    }
}