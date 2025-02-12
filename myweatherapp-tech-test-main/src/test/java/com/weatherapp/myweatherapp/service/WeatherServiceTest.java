package com.weatherapp.myweatherapp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {

  @Autowired
  WeatherService weatherService;

  /* Tests for daylightMinutesByCity method */

  @Test
  void testDaylightHoursLondon(){
    long minutes = weatherService.daylightMinutesByCity("London");
    assertEquals(579,minutes);
  }

  @Test
  void testDaylightHoursParis(){
    long minutes = weatherService.daylightMinutesByCity("Paris");
    assertEquals(593,minutes);
  }

  @Test
  void testDaylightHoursFakeCity(){
    long minutes = weatherService.daylightMinutesByCity("fake");
    assertEquals(-1,minutes);
  }


  /* Tests for IsRainingByCity method */

  @Test
  void testIsRainingLondon(){
    boolean raining = weatherService.isRainingByCity("London");
    assertEquals(true, raining);
  }

  @Test
  void testIsRainingDubai(){
    boolean raining = weatherService.isRainingByCity("Dubai");
    assertEquals(false, raining);
  }

  @Test
  void testIsRainingFakeCity(){
    Boolean raining = weatherService.isRainingByCity("fake");
    assertNull(raining);
  }
}