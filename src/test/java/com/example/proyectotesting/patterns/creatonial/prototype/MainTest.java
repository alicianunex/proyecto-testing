package com.example.proyectotesting.patterns.creatonial.prototype;

import com.example.proyectotesting.patterns.creational.prototype.Circle;
import com.example.proyectotesting.patterns.creational.prototype.Shape;
import com.example.proyectotesting.patterns.creational.prototype.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class MainTest {
    @BeforeEach
    void setUp() {
        try {
            com.example.proyectotesting.patterns.creational.prototype.Main main = new Main();
            main.main(new String[1]);
            assertTrue(true);
        }catch (Exception error){
            error.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void main() {
        Circle circle;
        Shape forma;
        Main.main(new String[] {});
    }

}