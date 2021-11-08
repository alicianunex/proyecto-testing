package com.example.proyectotesting.patterns.behavioral.mediator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Telegram Chat mediator Tests")
class TelegramTest {

    Telegram telegram;
    AbstractUser user;

    @BeforeEach
    void setUp() {
        telegram = new Telegram();
        user = new User(new Telegram(),"name");
    }

    @Test
    @DisplayName("sends a message to a user")
    void sendMessage() {

        user = new User(new Telegram(),"name2");
        User usermock = mock(User.class);
        telegram.addUser(usermock);

        doNothing().when(usermock).receive("msg");
        telegram.sendMessage("msg",user);
        verify(usermock).receive("msg");
    }

    @Test
    @DisplayName("Adds a user to the list")
    void addUser() {
        int initialcount =telegram.users.size();
        telegram.addUser(user);
        assertTrue(initialcount < telegram.users.size());
    }

    @Test
    @DisplayName("Removes a user from the list")
    void removeUser() {
        int initialcount =telegram.users.size();
        telegram.addUser(user);
        telegram.removeUser(user);
        assertTrue(initialcount == telegram.users.size());

    }
}