package com.example.proyectotesting.patterns.behavioral.state;

public class ProcessingState implements OrderState {

	@Override
	/**
	 * Changes the state to Shipped
	 */
	public void next(Order order) {
		
		System.out.println(
				String.format("Updating Order %d from %s to %s.", 
				order.getId(), 
				this.getClass().getSimpleName(), 
				ShippedState.class.getSimpleName() 
				));
		
		order.setState(new ShippedState());
		

	}

	@Override
	/**
	 * Notifies process not started
	 */
	public void previous(Order order) {

		System.out.println("Root state");
	}

}
