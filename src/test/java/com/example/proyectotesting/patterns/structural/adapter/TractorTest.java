package com.example.proyectotesting.patterns.structural.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tractor Tests")
class TractorTest {

    Tractor tractor;

    @BeforeEach
    void setUp(){tractor = new Tractor();}

    @Test
    void setSpeed() {
        tractor.setSpeed(99D);
        assertEquals(99D,tractor.getSpeed());
    }

    @Test
    @DisplayName("Shifts to first gear")
    void changeMode1() {
        tractor.changeMode(1);
        assertEquals(5,tractor.getSpeed());
    }
    @Test
    @DisplayName("Shifts to second gear")
    void changeMode2() {
        tractor.changeMode(2);
        assertEquals(15,tractor.getSpeed());
    }
    @Test
    @DisplayName("Does nothing if the gear is not provided correctly")
    void changeMode0() {
        tractor.setSpeed(0);
        tractor.changeMode(99);
        assertEquals(0,tractor.getSpeed());
    }
}