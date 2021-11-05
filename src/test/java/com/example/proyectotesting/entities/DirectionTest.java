package com.example.proyectotesting.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DirectionTest {

    Direction direction;

    @BeforeEach
    void setup(){
        direction = new Direction("street","postalCode","city","country");
    }

    @Test
    void getManufacturer() {assertNull(direction.getManufacturer());}

    @Test
    void setManufacturer() {

        Manufacturer manufacturer = new Manufacturer();
        direction.setManufacturer(manufacturer);

        assertEquals(manufacturer,direction.getManufacturer());
    }
}