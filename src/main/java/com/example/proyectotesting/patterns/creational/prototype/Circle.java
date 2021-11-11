package com.example.proyectotesting.patterns.creational.prototype;

public class Circle extends Shape{

	
	int radius;
	
	protected Circle(String color, int radius) {
		super(color);
		this.radius = radius;
	}

	@Override
	/**
	 * Copies this object and returns the copy
	 */
	public Shape copy() {
		return new Circle(this.getColor(), this.radius);
	}

}
