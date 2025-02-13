package com.weatherapp.myweatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService; // This should reference the INTERFACE

    @GetMapping("/forecast/{city}")
    public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {
        CityInfo ci = weatherService.forecastByCity(city);
        return ResponseEntity.ok(ci);
    }

    // Add the new endpoints here
    @GetMapping("/compare-daylight/{city1}/{city2}")
    public ResponseEntity<String> compareDaylight(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
        int daylightHoursCity1 = weatherService.getDaylightHours(city1);
        int daylightHoursCity2 = weatherService.getDaylightHours(city2);

        if (daylightHoursCity1 > daylightHoursCity2) {
            return ResponseEntity.ok(city1 + " has longer daylight hours.");
        } else if (daylightHoursCity2 > daylightHoursCity1) {
            return ResponseEntity.ok(city2 + " has longer daylight hours.");
        } else {
            return ResponseEntity.ok("Both cities have the same daylight hours.");
        }
    }

    @GetMapping("/check-rain/{city1}/{city2}")
    public ResponseEntity<String> checkRain(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
        boolean isRainingCity1 = weatherService.isRaining(city1);
        boolean isRainingCity2 = weatherService.isRaining(city2);

        if (isRainingCity1 && isRainingCity2) {
            return ResponseEntity.ok("It's raining in both cities.");
        } else if (isRainingCity1) {
            return ResponseEntity.ok("It's raining in " + city1 + ".");
        } else if (isRainingCity2) {
            return ResponseEntity.ok("It's raining in " + city2 + ".");
        } else {
            return ResponseEntity.ok("It's not raining in either city.");
        }
    }
}