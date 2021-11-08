package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("State Product Tests")
public class ProductTests {

    @Test
    @DisplayName("Checks that the product is created correctly")
    void ProductTest() {
        // Cannot access attributes!
        new Product(){};
    }
}

