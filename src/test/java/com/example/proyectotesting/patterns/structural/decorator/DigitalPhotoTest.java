package com.example.proyectotesting.patterns.structural.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DigitalPhoto (Photo) Tests")
class DigitalPhotoTest {

    DigitalPhoto digitalPhoto;

    @BeforeEach
    void setUp() {digitalPhoto = new DigitalPhoto();}

    @Test
    @DisplayName("Returns a info message")
    void show() {assertEquals("Normal photo without edit",digitalPhoto.show());}

    @Test
    @DisplayName("Returns the cost of the photo")
    void cost() {assertEquals(15D,digitalPhoto.cost());}
}