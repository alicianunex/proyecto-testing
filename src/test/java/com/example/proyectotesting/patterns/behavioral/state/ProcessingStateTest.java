package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@DisplayName("Processing State Tests")
class ProcessingStateTest {

    Order order;
    ProcessingState state;

    @BeforeEach
    void setUp() {
        state = new ProcessingState();
        order = mock(Order.class);
        doNothing().when(order).setState(new ShippedState());
    }

    @Test
    @DisplayName("Sets Delivery to ShippingState")
    void next() {
        state.next(order);
        verify(order).getId();
        verify(order).setState(any(ShippedState.class));
    }

    @Test
    @DisplayName("Sets Delivery to Root Value")
    void previous() {
        try {
            state.previous(order);
            assertTrue(true);
        }catch(Exception error){
            assertTrue(false);
        }

    }
}