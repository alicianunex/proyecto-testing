package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardStrategyTest {

    CreditCardStrategy creditCardStrategy;

    @BeforeEach
    void setUp() {
        creditCardStrategy = new CreditCardStrategy("a","b","c","d");
    }

    @Test
    void setName() {
        creditCardStrategy.setName("name");
        assertEquals("name",creditCardStrategy.getName());
    }

    @Test
    void setCardNumber() {
        creditCardStrategy.setCardNumber("999");
        assertEquals("999",creditCardStrategy.getCardNumber());
    }

    @Test
    void setCcv() {
        creditCardStrategy.setCcv("Ccv");
        assertEquals("Ccv",creditCardStrategy.getCcv());
    }

    @Test
    void setDateOfExpiry() {
        creditCardStrategy.setDateOfExpiry("Date");
        assertEquals("Date",creditCardStrategy.getDateOfExpiry());
    }

    @Test
    void pay() {
        try {
            creditCardStrategy.pay(2.0);
            assertTrue(true);
        }catch(Exception error){
            assertTrue(false);
        }
    }
}