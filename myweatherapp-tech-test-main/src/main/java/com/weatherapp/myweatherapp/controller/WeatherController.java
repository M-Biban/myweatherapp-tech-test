package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  /**
   * This method compares the length of the daylight hours of the given
   * cities and returns the city with the longest day.
   * 
   * @param city1 First city to be compared.
   * @param city2 Second city to be compared.
   * @return The city with the longest day.
   */
  @GetMapping("/d-hours/{city1}/{city2}")
  public ResponseEntity<String> daylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {

    // Checks if the two cities are the same
    if (city1.equals(city2)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both cities are the same: " + city1);
    }

    long ci1 = weatherService.daylightMinutesByCity(city1);
    long ci2 = weatherService.daylightMinutesByCity(city2);

    /* If there is an error in retrieving data for the cities it is checked here */
    if(ci1 == -1 && ci2 == -1){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data for both cities: "+ city1 + ", " + city2);
    } else if(ci1 == -1){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data for city: "+ city1);
    } else if(ci2 == -1){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data for city: " + city2);
    }

    if(ci1 == ci2){
      return ResponseEntity.ok("Both cities have the same daylight hours: " + city1 + ", " + city2);
    } else if(ci1 > ci2){
      return ResponseEntity.ok(city1);
    } else{
      return ResponseEntity.ok(city2);
    }

  }


  /**
   * This method checks in which of the two given cities it is raining
   * in. If it is both or neither that is also handled appropriately.
   * 
   * @param city1 First city to be checked.
   * @param city2 Second city to be checked.
   * @return Which city/cities it is currently raining.
   */
  @GetMapping("/rain/{city1}/{city2}")
  public ResponseEntity<String> isRaining(@PathVariable("city1") String city1, @PathVariable("city2") String city2){

    Boolean rainc1 = weatherService.isRainingByCity(city1);

    // checks if the cities are the same
    if (city1.equals(city2)) {
      if(rainc1 == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both cities are the same: " + city1 + " and there is no rain data");
      } if(rainc1){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both cities are the same: " + city1 + " and it is raining.");
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both cities are the same: " + city1 + " and it is not raining.");
      }
    }

    Boolean rainc2 = weatherService.isRainingByCity(city2);

    if(rainc1 == null && rainc2 == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rain data for both cities: "+ city1 + ", " + city2);
    } else if(rainc1 == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rain data for city: "+ city1);
    } else if(rainc2 == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rain data for city: "+ city2);
    }

    if(rainc1 && rainc2){
      return ResponseEntity.ok("Raining in both cities: " + city1 + ", " + city2);
    } else if (rainc1){
      return ResponseEntity.ok(city1);
    } else if(rainc2){
      return ResponseEntity.ok(city2);
    }

    return ResponseEntity.ok("Not raining in either city");
  }
}
