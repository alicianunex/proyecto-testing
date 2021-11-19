package com.example.proyectotesting.patterns.creatonial.prototype;

import com.example.proyectotesting.patterns.creational.prototype.Circle;
import com.example.proyectotesting.patterns.creational.prototype.Shape;
import com.example.proyectotesting.patterns.structural.adapter.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MainTest {
    @BeforeEach
    void setUp() {
    }


    @Test
    void main() {
        Circle circle;
        Shape forma;
        Main.main(new String[] {});
    }

}