package com.example.proyectotesting.patterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Chat Mediator object stores users
 * and sends messages between them
 */
public class Telegram implements ChatMediator{

	List<AbstractUser> users = new ArrayList<>();
	
	@Override
	/**
	 * Sends a message to the specified user
	 * param message The String containing the msg to send
	 * param user the recipient of the msg
	 */
	public void sendMessage(String message, AbstractUser user) {
		for (AbstractUser abstractUser : users) {
			if(abstractUser != user)
				abstractUser.receive(message);
		}
	}

	@Override
	/**
	 * Adds the user provided to the userList
	 */
	public void addUser(AbstractUser user) {
		this.users.add(user);
		
	}

	@Override
	/**
	 * removes the user provided from the userList
	 */
	public void removeUser(AbstractUser user) {
		this.users.remove(user);
	}

}
