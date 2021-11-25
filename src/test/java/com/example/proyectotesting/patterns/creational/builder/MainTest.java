package com.example.proyectotesting.patterns.creational.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class MainTest {

    @Test
    void main() {
        try{
            com.example.proyectotesting.patterns.creational.builder.Main main = new Main();
            main.main(new String[1]);
            assertTrue(true);
        }catch (Exception error){
            error.printStackTrace();
            fail();
        }
    }
}
