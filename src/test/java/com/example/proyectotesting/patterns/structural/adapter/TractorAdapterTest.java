package com.example.proyectotesting.patterns.structural.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tractor Adapter Movable Tests")
class TractorAdapterTest {

    TractorAdapter tractorAdapter;

    @Test
    @DisplayName("Speeds up to 1st gear")
    void speedUp1() {
        try {
            tractorAdapter = new TractorAdapter();
            tractorAdapter.speedUp(1);
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            fail();
        }
    }
    @Test
    @DisplayName("Speeds up to 2nd gear")
    void speedUp2() {
        try {
            tractorAdapter = new TractorAdapter();
            tractorAdapter.speedUp(20);
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }
}