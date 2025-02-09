package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public long daylightMinutesByCity(String city) {

    try {
      CityInfo.CurrentConditions conditions = forecastByCity(city).getCurrentConditions();
      LocalTime sunrise = conditions.getSunrise();
      LocalTime sunset = conditions.getSunset();
      return sunrise.until(sunset, ChronoUnit.MINUTES);
    } catch (Exception e) {
      return -1; //This will be handled in the controller
    }
    
  }

  public Boolean isRainingByCity(String city) {

    LocalDate now = LocalDate.now();

    try {
      for (CityInfo.Days day: forecastByCity(city).getDays()){
        String condition = day.getConditions();
        LocalDate date = day.getDate();
        if(condition.contains("Rain") && date.equals(now)){
          return true;
        }
      }
    } catch (Exception e) {
      return null;
    }

    return false;
  }
}
