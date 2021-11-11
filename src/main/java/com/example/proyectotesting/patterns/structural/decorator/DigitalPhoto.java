package com.example.proyectotesting.patterns.structural.decorator;

public class DigitalPhoto extends Photo{

	@Override
	/**
	 * Prints a msg to alert that
	 * the photo hasn't been modified
	 */
	public String show() {
		return "Normal photo without edit";
	}
	
	@Override
	/**
	 * Returns base cost of the photo
	 */
	public double cost() {
		return 15;
	}


}
