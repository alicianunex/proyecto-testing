package com.example.proyectotesting.patterns.behavioral.mediator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    User user = new User(new Telegram(),"name");

    @Test
    void send() {
        user.mediator = mock(Telegram.class);
        doNothing().when(user.mediator).sendMessage("msg",user);
        user.send("msg");
        verify(user.mediator).sendMessage("msg",user);
    }

    @Test
    void receive() {assertDoesNotThrow(() ->user.receive("msg"));}
}