package com.example.proyectotesting.patterns.structural.facade.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NFC Sensor Tests")
class NFCSensorTest {

    NFCSensor nfcSensor;

    @BeforeEach
    void setUp() {nfcSensor = new NFCSensor();}

    @Test
    @DisplayName("Prints start")
    void start() {
        try {
            nfcSensor.start();
        }catch(Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Prints stop")
    void stop() {
        try {
            nfcSensor.stop();
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }
}