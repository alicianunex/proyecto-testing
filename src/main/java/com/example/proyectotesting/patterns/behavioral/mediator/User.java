package com.example.proyectotesting.patterns.behavioral.mediator;

/**
 * The final user of a particular chat mediator
 */
public class User extends AbstractUser{

	protected User(ChatMediator mediator, String name) {
		super(mediator, name);
	}

	@Override
	/**
	 * Sends a msg through the chat used by this user
	 */
	public void send(String message) {
		this.mediator.sendMessage(message, this);
	}

	@Override
	/**
	 * Confirms the reception of a msg
	 */
	public void receive(String message) {
		System.out.println("received!");
		
	}

}
