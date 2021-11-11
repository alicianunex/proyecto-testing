package com.example.proyectotesting.patterns.structural.decorator.decoradores;


import com.example.proyectotesting.patterns.structural.decorator.Photo;

public class EffectRetro extends PhotoDecorator{

	public EffectRetro(Photo photo) {
		super(photo);
	}

	@Override
	/**
	 * Applies the retro effect to the photo
	 */
	public String show() {
		// decorar la foto
		this.photo.setBrightness(50);
		this.photo.setContrast(20);
		return this.photo.show() + " + Retro";
	}
	
	@Override
	/**
	 * Returns base cost + Vintage cost
	 */
	public double cost() {
		return this.photo.cost() + 20;
	}

}
