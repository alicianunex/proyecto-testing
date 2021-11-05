package com.example.proyectotesting.patterns.behavioral.mediator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.invoke.StringConcatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelegramTest {

    Telegram telegram;
    AbstractUser user;

    @BeforeEach
    void setUp() {
        telegram = new Telegram();
        user = new User(new Telegram(),"name");
    }

    @Test
    void sendMessage() {

        user = new User(new Telegram(),"name2");
        User usermock = mock(User.class);
        telegram.addUser(usermock);

        doNothing().when(usermock).receive("msg");
        telegram.sendMessage("msg",user);
        verify(usermock).receive("msg");
    }

    @Test
    void addUser() {
        int initialcount =telegram.users.size();
        telegram.addUser(user);
        assertTrue(initialcount < telegram.users.size());
    }

    @Test
    void removeUser() {
        int initialcount =telegram.users.size();
        telegram.addUser(user);
        telegram.removeUser(user);
        assertTrue(initialcount == telegram.users.size());

    }
}