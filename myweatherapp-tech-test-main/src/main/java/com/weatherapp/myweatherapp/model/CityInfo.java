package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CityInfo {

  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  CurrentConditions currentConditions;

  public CurrentConditions getCurrentConditions(){
    return currentConditions;
  }

  @JsonProperty("days")
  List<Days> days;

  public List<Days> getDays(){
    return days;
  }

  public static class CurrentConditions {
    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("sunrise")
    String sunrise;

    @JsonProperty("sunset")
    String sunset;

    @JsonProperty("feelslike")
    String feelslike;

    @JsonProperty("humidity")
    String humidity;

    @JsonProperty("conditions")
    String conditions;

    public LocalTime getSunrise(){
      return LocalTime.parse(sunrise);
    }

    public LocalTime getSunset(){
      return LocalTime.parse(sunset);
    }

  }

  public static class Days {

    @JsonProperty("datetime")
    String date;

    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("tempmax")
    String maxTemperature;

    @JsonProperty("tempmin")
    String minTemperature;

    @JsonProperty("conditions")
    String conditions;

    @JsonProperty("description")
    String description;

    public String getConditions(){
      return conditions;
    }

    public LocalDate getDate(){
      return LocalDate.parse(date);
    }

  }

}
