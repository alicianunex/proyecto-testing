package com.example.proper.proyectotesting.patterns.structural.facade;


import com.example.proper.proyectotesting.patterns.structural.facade.pieces.SmartPhone;

public class Main {
	
	public static void main(String[] args) {
		
		// crear un smartphone
		SmartPhone oneplus = SmartPhoneFacade.startSmartPhone();
	}

}
