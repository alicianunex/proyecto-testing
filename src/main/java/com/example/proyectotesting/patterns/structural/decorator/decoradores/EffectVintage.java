package com.example.proyectotesting.patterns.structural.decorator.decoradores;


import com.example.proyectotesting.patterns.structural.decorator.Photo;

public class EffectVintage extends PhotoDecorator{

	public EffectVintage(Photo photo) {
		super(photo);
	}

	@Override
	/**
	 * Applies the vintage effect to the photo
	 */
	public String show() {
		// decorar la foto
		this.photo.setBrightness(90);
		this.photo.setContrast(20);
		this.photo.setBlur(5);
		return this.photo.show() + " + Vintage";
	}
	
	@Override
	/**
	 * Returns base cost + Vintage cost
	 */
	public double cost() {
		return this.photo.cost() + 15;
	}

}
