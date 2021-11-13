package com.example.proyectotesting.patterns.creatonial.builder;

import com.example.proyectotesting.patterns.creational.builder.User;
import com.example.proyectotesting.patterns.structural.adapter.Main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean married;

    User user;
    @BeforeEach
    void setUp() {
        user = mock(User.class);
        this.user = new User(1L,"Juan","López","juanlopez@hotmail.com", true);
        when(user.getId()).thenReturn(1L);
    }
    
}