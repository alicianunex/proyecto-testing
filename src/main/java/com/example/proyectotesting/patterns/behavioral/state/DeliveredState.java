package com.example.proyectotesting.patterns.behavioral.state;

public class DeliveredState implements OrderState {

	@Override
	/**
	 * Prints termination of delivery process
	 */
	public void next(Order order) {
		System.out.println("Order delivered, ends!");

	}

	@Override
	/**
	 * Changes the state to Shipped
	 */
	public void previous(Order order) {
		System.out.println(
				String.format("Updating Order %d from %s to %s.", 
				order.getId(), 
				this.getClass().getSimpleName(), 
				ShippedState.class.getSimpleName() 
				));
		
		order.setState(new ShippedState());
	}

}
