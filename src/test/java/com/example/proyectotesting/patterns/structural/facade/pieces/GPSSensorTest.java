package com.example.proyectotesting.patterns.structural.facade.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GPS Sensor Tests")
class GPSSensorTest {

    GPSSensor gpsSensor;

    @BeforeEach
    void setUp() {gpsSensor = new GPSSensor();}

    @Test
    @DisplayName("Prints start")
    void start() {
        try {
            gpsSensor.start();
        }catch(Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Prints stop")
    void stop() {
        try {
            gpsSensor.stop();
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }
}