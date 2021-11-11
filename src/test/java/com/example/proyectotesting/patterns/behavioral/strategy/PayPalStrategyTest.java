package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PayPal Strategy Payment Strategy Tests")
class PayPalStrategyTest {

    PayPalStrategy payPalStrategy;

    @BeforeEach
    void setUp() {
        payPalStrategy = new PayPalStrategy("a","b", "c");
    }

    @Test
    void setApiTest() {
        payPalStrategy.setApi("Ccv");
        assertEquals("Ccv",payPalStrategy.getApi());
    }

    @Test
    void setUser() {
        payPalStrategy.setUser("Ccv");
        assertEquals("Ccv",payPalStrategy.getUser());
    }

    @Test
    void setToken() {
        payPalStrategy.setToken("Token");
        assertEquals("Token",payPalStrategy.getToken());
    }

    @Test
    @DisplayName("Does nothing")
    void pay() {
        try {
            payPalStrategy.pay(2.0);
            assertTrue(true);
        }catch(Exception error){
            assertTrue(false);
        }
    }
}