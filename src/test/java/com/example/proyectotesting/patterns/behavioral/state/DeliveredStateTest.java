package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@DisplayName("Delivered State Tests")
class DeliveredStateTest {

    Order order;
    DeliveredState state;

    @BeforeEach
    void setUp() {
        state = new DeliveredState();
        order = mock(Order.class);
        doNothing().when(order).setState(new ShippedState());
    }

    @Test
    @DisplayName("Closes delivery")
    void next() {
        try {
            state.next(order);
            assertTrue(true);
        }catch(Exception error){
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Sets Delivery to ShippingState")
    void previous() {
        state.previous(order);
        when(order.getId()).thenReturn(1L);
        verify(order).getId();
        verify(order).setState(any(ShippedState.class));
    }
}