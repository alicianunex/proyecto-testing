package com.example.proyectotesting.patterns.creatonial.singleton;

import com.example.proyectotesting.patterns.creational.singleton.Invoice;
import com.example.proyectotesting.patterns.creational.singleton.Offer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void calculateZeroPrice() {
        List<Double> prices = new ArrayList<>();
        Invoice invoice = new Invoice();
        double invoiceResult= invoice.calculateTotalPrice();
        Offer offer = new Offer();
        double offerResult = offer.calculateTotalOffer();
        assertEquals(invoiceResult,offerResult);
    }

}