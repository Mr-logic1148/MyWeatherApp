package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;

public interface WeatherService {

    CityInfo forecastByCity(String city);

    // Add these two new methods
    int getDaylightHours(String city); // Returns daylight hours for a city
    boolean isRaining(String city);    // Returns true if it's raining in the city
}