package com.example.proyectotesting.patterns.structural.decorator.decoradores;

import com.example.proyectotesting.patterns.structural.decorator.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("EffectRetro (PhotoDecorator)")
class EffectRetroTest {

    PhotoDecorator retro;

    @BeforeEach
    void setUp() {
        Photo photo = mock(Photo.class);
        when(photo.show()).thenReturn("a");
        when(photo.cost()).thenReturn(1D);
        retro = new EffectRetro(photo);
    }

    @Test
    @DisplayName("Returns a info message")
    void show() {assertEquals("a + Retro", retro.show());}

    @Test
    @DisplayName("Returns the cost of the photo")
    void cost() {assertEquals(21D, retro.cost());}
}