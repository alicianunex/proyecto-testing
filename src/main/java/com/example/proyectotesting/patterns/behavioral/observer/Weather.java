package com.example.proyectotesting.patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Weather obj and its observers
 * changes the weather using WeatherType
 * and notifies to the observers stored
 */
public class Weather {
 
    private WeatherType currentWeather;
    private List<WeatherObserver> observers;

    public Weather(){
        this.observers = new ArrayList<>();
    }

    public void addObserver(WeatherObserver obs){
        System.out.println("A�adido obs");
        this.observers.add(obs);
    }

    public void removeObserver(WeatherObserver obs){
        System.out.println("A�adido obs");
        this.observers.remove(obs);
    }

    /**
     * changes the weather
      */
    void changeWeather(WeatherType currentWeather){
        this.currentWeather = currentWeather;
        this.notifyObservers();
    }

    /** notificar del cambio de tiempo
     *
     */
    private void notifyObservers(){
        for (WeatherObserver observer: this.observers) {
            observer.update(this.currentWeather);
        }
    }
}
