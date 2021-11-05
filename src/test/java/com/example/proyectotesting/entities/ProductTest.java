package com.example.proyectotesting.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTest {

    Product product;

    @BeforeEach
    void setup(){
        product = new Product("street","postalCode",2,2.3D,null);
    }

    @Test
    void setCategoriesTest() {
        List<Category> array = new ArrayList<>();

        product.setCategories(array);
        assertEquals(array,product.getCategories());
    }

    @Test
    void setidTest() {
        product.setId(999L);
        assertEquals(999L,product.getId());
    }

    @Test
    void setNameTest() {product.setName("n");
        assertEquals("n",product.getName());
    }

    @Test
    void setDescriptionTest() {
        product.setDescription("d");
        assertEquals("d",product.getDescription());
    }

    @Test
    void setQuantityTest() {product.setQuantity(0);
        assertEquals(0,product.getQuantity());
    }

    @Test
    void setPriceTest() {
        product.setPrice(9D);
        assertEquals(9D,product.getPrice());
    }

    @Test
    void getManufacturer() {assertNull(product.getManufacturer());}

    @Test
    void setManufacturer() {

        Manufacturer manufacturer = new Manufacturer();
        product.setManufacturer(manufacturer);

        assertEquals(manufacturer,product.getManufacturer());
    }
    @Test
    void ToStringTest() {product.toString();}
}