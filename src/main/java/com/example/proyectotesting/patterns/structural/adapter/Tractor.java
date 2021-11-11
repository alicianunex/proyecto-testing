package com.example.proyectotesting.patterns.structural.adapter;

/**
 * Vehicle with speed
 */
public class Tractor {
	
	private double speed;
	
	
	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	/**
	 * Shifts gears in the movable and
	 * adjusts speed accordingly
	 * @param mode the gear to shift to
	 */
	public void changeMode(int mode) {
		switch (mode) {
		case 1:
			this.speed = 5;
			break;
		case 2:
			this.speed = 15;
			break;

		default:
			break;
		}
		
	}


}
