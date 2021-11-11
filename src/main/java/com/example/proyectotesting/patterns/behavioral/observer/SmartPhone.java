package com.example.proyectotesting.patterns.behavioral.observer;

public class SmartPhone implements WeatherObserver{
    @Override
    /**
     * Prints confirmation of weather change
     */
    public void update(WeatherType type) {
        System.out.println("Smartphone has been notified of weather change: " + type);
    }
}
