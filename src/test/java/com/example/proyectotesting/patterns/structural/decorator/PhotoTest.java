package com.example.proyectotesting.patterns.structural.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Photo Tests")
class PhotoTest {

    Photo photo;

    @BeforeEach
    void setUp() {
        photo = new DigitalPhoto();
    }

    @Test
    void setBrightness() {
        photo.setBrightness(20);
        assertEquals(20,photo.getBrightness());
    }

    @Test
    void setContrast() {
        photo.setContrast(20);
        assertEquals(20,photo.getContrast());
    }

    @Test
    void setSaturation() {
        photo.setSaturation(20);
        assertEquals(20,photo.getSaturation());
    }

    @Test
    void setBlur() {
        photo.setBlur(20);
        assertEquals(20,photo.getBlur());
    }
}