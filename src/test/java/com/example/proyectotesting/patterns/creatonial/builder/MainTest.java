package com.example.proyectotesting.patterns.creatonial.builder;

import com.example.proyectotesting.patterns.behavioral.iterator.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class MainTest {

    @Test
    void main() {
        try{
            com.example.proyectotesting.patterns.behavioral.iterator.Main main = new Main();
            main.main(new String[1]);
            assertTrue(true);
        }catch (Exception error){
            error.printStackTrace();
            fail();
        }
    }
}
