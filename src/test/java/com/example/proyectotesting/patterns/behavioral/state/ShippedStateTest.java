package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShippedStateTest {

    Order order;
    ShippedState state;

    @BeforeEach
    void setUp() {
        state = new ShippedState();
        order = mock(Order.class);
        doNothing().when(order).setState(new DeliveredState());
        doNothing().when(order).setState(new ProcessingState());
        when(order.getId()).thenReturn(1L);
    }

    @Test
    void next() {
        state.next(order);
        verify(order).getId();
        verify(order).setState(any(DeliveredState.class));
    }

    @Test
    void previous() {
        state.previous(order);
        verify(order).getId();
        verify(order).setState(any(ProcessingState.class));
    }
}