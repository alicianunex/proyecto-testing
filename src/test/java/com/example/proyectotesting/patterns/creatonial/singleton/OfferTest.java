package com.example.proyectotesting.patterns.creatonial.singleton;

import com.example.proyectotesting.patterns.creational.singleton.Calculator;
import com.example.proyectotesting.patterns.creational.singleton.Offer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfferTest {
    Calculator calculator;
    @Test
    void OfferOkTest(){
        List<Double> prices = new ArrayList<>();
        Calculator calculator = new Calculator();

        Offer offer = new Offer();
        double result= offer.calculateTotalOffer();

        assertTrue(result==0d);


    }
}