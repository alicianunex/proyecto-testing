package com.example.proyectotesting.patterns.behavioral.observer;

public class Computer implements WeatherObserver{
    @Override
    /**
     * Prints confirmation of weather change
     */
    public void update(WeatherType type) {
        System.out.println("Computer has been notified of weather change: " + type);
    }
}
