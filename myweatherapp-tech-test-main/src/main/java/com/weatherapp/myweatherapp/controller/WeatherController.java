package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  // TODO: given two city names, compare the length of the daylight hours and return the city with the longest day

  @GetMapping("/d-hours/{city1}/{city2}")
  public ResponseEntity<String> DaylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {

    long ci1 = weatherService.daylightHoursByCity(city1);
    long ci2 = weatherService.daylightHoursByCity(city2);

    if(ci1 > ci2){
      return ResponseEntity.ok(city1);
    } else{
      return ResponseEntity.ok(city2);
    }

  }
  
  // TODO: given two city names, check which city its currently raining in

  @GetMapping("/rain/{city1}/{city2}")
  public ResponseEntity<String> IsRaining(@PathVariable("city1") String city1, @PathVariable("city2") String city2){

    if(weatherService.IsRainingByCity(city1) && weatherService.IsRainingByCity(city2)){
      return ResponseEntity.ok("Raining in both cities: " + city1 + ", " + city2);
    } else if (weatherService.IsRainingByCity(city1)){
      return ResponseEntity.ok(city1);
    } else if(weatherService.IsRainingByCity(city2)){
      return ResponseEntity.ok(city2);
    }

    return ResponseEntity.ok("Not raining in either city");
  }
}
