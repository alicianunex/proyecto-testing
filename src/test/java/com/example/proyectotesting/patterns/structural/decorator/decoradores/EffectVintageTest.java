package com.example.proyectotesting.patterns.structural.decorator.decoradores;

import com.example.proyectotesting.patterns.structural.decorator.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("EffectVintage PhotoDecorator Tests")
class EffectVintageTest {

    PhotoDecorator vintage;

    @BeforeEach
    void setUp() {
        Photo photo = mock(Photo.class);
        when(photo.show()).thenReturn("a");
        when(photo.cost()).thenReturn(1D);
        vintage = new EffectVintage(photo);
    }

    @Test
    @DisplayName("Returns a info message")
    void show() {assertEquals("a + Vintage", vintage.show());}

    @Test
    @DisplayName("Returns the cost of the photo")
    void cost() {assertEquals(16D, vintage.cost());}
}