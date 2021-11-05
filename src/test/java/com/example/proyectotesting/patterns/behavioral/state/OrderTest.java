package com.example.proyectotesting.patterns.behavioral.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderTest {

    Order order;

    @BeforeEach
    void setUp() {
        order = new Order(1L,LocalDateTime.now());
    }

    @Test
    void nextState() {
        order.setState(mock(ProcessingState.class));
        doNothing().when(order.getState()).next(order);
        order.nextState();
        verify(order.getState()).next(order);
    }

    @Test
    void previousState() {
        order.setState(mock(ProcessingState.class));
        doNothing().when(order.getState()).previous(order);
        order.previousState();
        verify(order.getState()).previous(order);
    }

    @Test
    void setId() {
        order.setId(2L);
        assertEquals(2L,order.getId());
    }

    @Test
    void setDate() {
        LocalDateTime time = LocalDateTime.now();
        order.setDate(time);
        assertEquals(time,order.getDate());
    }

    @Test
    void setProducts() {
        List<Product> products = new ArrayList<>();
        order.setProducts(products);
        assertEquals(products,order.getProducts());
    }

    @Test
    void setShippedState() {
        OrderState state = new ShippedState();
        order.setState(state);
        assertEquals(state, order.getState());
    }
}
