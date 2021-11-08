package com.example.proyectotesting.patterns.behavioral.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Weather Tests")
class WeatherTest {

    Weather weather;
    WeatherObserver weatherObserverphone;
    WeatherObserver weatherObservercomputer;

    @BeforeEach
    void setUp() {
        weather = new Weather();
        weatherObserverphone = new SmartPhone();
        weatherObservercomputer = new Computer();
    }

    @Test
    @DisplayName("Adds an observer to the list")
    void addObserver() {

        try {
            weather.addObserver(weatherObserverphone);
            weather.removeObserver(weatherObserverphone);
            assertTrue(true);
        } catch (Exception error) {
            error.printStackTrace();
            System.out.println("Error adding Observer");
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Removes a weather observer from the list")
    void removeObserver() {

        try {
            weather.addObserver(weatherObservercomputer);
            weather.removeObserver(weatherObservercomputer);
            assertTrue(true);
        } catch (Exception error) {
            error.printStackTrace();
            System.out.println("Error removing Observer");
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Changes the weather and notifies it to the observers")
    void changeWeather() {
        weather.addObserver(weatherObservercomputer);
        weather.addObserver(weatherObserverphone);

        try {
            weather.changeWeather(WeatherType.SUNNY);
            weather.changeWeather(WeatherType.CLOUDY);
            weather.changeWeather(WeatherType.RAINY);
            assertTrue(true);
        } catch (Exception error) {
            error.printStackTrace();
            System.out.println("Error changing weather");
            assertTrue(false);
        }
    }
}