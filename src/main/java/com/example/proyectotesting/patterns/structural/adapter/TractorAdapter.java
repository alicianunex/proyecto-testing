package com.example.proyectotesting.patterns.structural.adapter;

public class TractorAdapter implements Movable{

	private Tractor tractor = new Tractor();
	
	@Override
	/**
	 * Shifts gears in the movable and
	 * adjusts speed accordingly
	 * @param quantity the increment of speed to apply
	 */
	public void speedUp(double quantity) {
		if (this.tractor.getSpeed() + quantity < 15) {
			this.tractor.changeMode(1);
		} else {
			this.tractor.changeMode(2);
		}
		
	}

}
