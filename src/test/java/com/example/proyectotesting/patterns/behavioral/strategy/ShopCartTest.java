package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopCartTest {

    ShopCart shopCart;
    Product product;

    @BeforeEach
    void setUp(){
        shopCart = new ShopCart();
        product = mock(Product.class);
    }

    @Test
    void addProduct() {
        try {
            shopCart.addProduct(product);
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            System.out.println("Error while adding");
            assertTrue(false);
        }
    }

    @Test
    void removeProduct() {
        try {
            shopCart.addProduct(product);
            shopCart.removeProduct(product);
            assertTrue(true);
        }catch(Exception error){
            error.printStackTrace();
            System.out.println("Error while removing");
            assertTrue(false);
        }
    }

    @Test
    void pay() {
        PaymentStrategy payment = (mock(PayPalStrategy.class));
        doNothing().when(payment).pay(anyDouble());
        shopCart.pay(payment);
        verify(payment).pay(anyDouble());
    }
}