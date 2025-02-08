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

  public long daylightHoursByCity(String city) {

    CityInfo.CurrentConditions conditions = forecastByCity(city).getCurrentConditions();
    LocalTime sunrise = conditions.getSunrise();
    LocalTime sunset = conditions.getSunset();

    return sunrise.until(sunset, ChronoUnit.MINUTES);
  }

  public Boolean IsRainingByCity(String city) {

    LocalDate now = LocalDate.now();

    for (CityInfo.Days day: forecastByCity(city).getDays()){
      String condition = day.getConditions();
      LocalDate date = day.getDate();
      if(condition.contains("Rain") && date.equals(now)){
        return true;
      }
    }

    return false;
  }
}
