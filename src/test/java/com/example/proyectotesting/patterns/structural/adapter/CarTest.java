package com.example.proyectotesting.patterns.structural.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Car Movable Tests")
class CarTest {

    Car car;

    @BeforeEach
    void setUp(){car = new Car();}

    @Test
    void setSpeed() {
        car.setSpeed(99D);
        assertEquals(99D,car.getSpeed());
    }

    @Test
    @DisplayName("Adds specified speed")
    void SpeedUp() {
        double initialspeed = car.getSpeed();
        car.speedUp(1);
        assertEquals(initialspeed+1,car.getSpeed());
    }

}