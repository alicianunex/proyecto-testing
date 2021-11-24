package com.example.proyectotesting.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    @Test
    void setProducts() {
        Category category = new Category();
        List<Product> products = new ArrayList<>();
        List<Product> result = new ArrayList<>();
        category.setProducts(products);
        assertEquals(result, category.getProducts());
    }
}
