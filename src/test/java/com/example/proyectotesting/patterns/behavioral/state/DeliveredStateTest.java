package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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
    void next() {
        try {
            state.next(order);
            assertTrue(true);
        }catch(Exception error){
            assertTrue(false);
        }
    }

    @Test
    void previous() {
        state.previous(order);
        when(order.getId()).thenReturn(1L);
        verify(order).getId();
        verify(order).setState(any(ShippedState.class));
    }
}