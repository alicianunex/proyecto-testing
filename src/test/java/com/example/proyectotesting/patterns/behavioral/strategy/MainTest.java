package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Strategy is started correctly")
public class MainTest {
    @Test
    void mainTest(){
        try {
            Main main = new Main();
            main.main(new String[1]);
            assertTrue(true);
        }catch (Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }
}
