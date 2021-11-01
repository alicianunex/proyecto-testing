package com.example.proper.proyectotesting.patterns.behavioral.state;

public interface OrderState {

	void next(Order order);
	
	void previous(Order order);
}
