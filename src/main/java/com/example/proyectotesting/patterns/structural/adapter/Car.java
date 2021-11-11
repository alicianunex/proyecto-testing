package com.example.proyectotesting.patterns.structural.adapter;

public class Car implements Movable{

	private double speed;
	
	@Override
	/**
	 * adds to the atrib speed the cuantity provided
	 */
	public void speedUp(double quantity) {
		this.speed += quantity;
		
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	

}
