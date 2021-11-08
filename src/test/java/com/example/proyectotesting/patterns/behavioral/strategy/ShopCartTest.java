package com.example.proyectotesting.patterns.behavioral.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Shop Cart Tests")
class ShopCartTest {

    ShopCart shopCart;
    Product product;

    @BeforeEach
    void setUp(){
        shopCart = new ShopCart();
        product = mock(Product.class);
    }

    @Test
    @DisplayName("Adds a product to the list")
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
    @DisplayName("Removes a product from the list")
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
    @DisplayName("Prints confirmation of payment and the amount paid")
    void pay() {
        PaymentStrategy payment = (mock(PayPalStrategy.class));
        doNothing().when(payment).pay(anyDouble());
        shopCart.pay(payment);
        verify(payment).pay(anyDouble());
    }
}