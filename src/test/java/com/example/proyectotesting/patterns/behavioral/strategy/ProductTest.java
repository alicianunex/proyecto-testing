package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {product = new Product("a",1D);}

    @Test
    void setSku() {
        product.setSku("sku");
        assertEquals("sku",product.getSku());
    }

    @Test
    void setPrice() {
        product.setPrice(9D);
        assertEquals(9D,product.getPrice());
    }
}