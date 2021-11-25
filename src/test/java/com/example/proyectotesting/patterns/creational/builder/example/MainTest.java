package com.example.proyectotesting.patterns.creational.builder.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MainTest {

    @Test
    void main() {
        try{
            Main main = new Main();
            main.main(new String[1]);
            assertTrue(true);
        }catch (Exception error){
            error.printStackTrace();
            fail();
        }
    }
}
