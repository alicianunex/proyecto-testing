package com.example.proyectotesting.patterns.creatonial.prototype;

import com.example.proyectotesting.patterns.creational.prototype.Circle;
import com.example.proyectotesting.patterns.creational.prototype.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CircleTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void copy() {
        int radius = 1;
        Circle newCircle = new Circle("red",radius );
        
        assertEquals("red", newCircle.getColor());

        Shape forma = newCircle.copy();
    }
}