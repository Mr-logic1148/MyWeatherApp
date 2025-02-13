package com.weatherapp.myweatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    VisualcrossingRepository weatherRepo;

    @Override
    public CityInfo forecastByCity(String city) {
        return weatherRepo.getByCity(city);
    }

    @Override
    public int getDaylightHours(String city) {
        CityInfo cityInfo = weatherRepo.getByCity(city);
        // Use getters to access the fields
        String sunrise = cityInfo.getCurrentConditions().getSunrise();
        String sunset = cityInfo.getCurrentConditions().getSunset();
        // Calculate daylight hours (difference between sunset and sunrise)
        return calculateDaylightHours(sunrise, sunset);
    }

    @Override
    public boolean isRaining(String city) {
        CityInfo cityInfo = weatherRepo.getByCity(city);
        // Use getters to access the fields
        return cityInfo.getCurrentConditions().getConditions().toLowerCase().contains("rain");
    }

    // Helper method to calculate daylight hours
    private int calculateDaylightHours(String sunrise, String sunset) {
        // Parse sunrise and sunset times (e.g., "06:00" and "18:00")
        int sunriseHour = Integer.parseInt(sunrise.split(":")[0]);
        int sunsetHour = Integer.parseInt(sunset.split(":")[0]);
        return sunsetHour - sunriseHour;
    }
}