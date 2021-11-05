package com.example.proyectotesting.patterns.behavioral.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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