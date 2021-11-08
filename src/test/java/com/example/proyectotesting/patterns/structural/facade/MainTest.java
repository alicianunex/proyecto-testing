package com.example.proyectotesting.patterns.structural.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Facade is started correctly")
public class MainTest {
    @Test
    void mainTest(){
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
