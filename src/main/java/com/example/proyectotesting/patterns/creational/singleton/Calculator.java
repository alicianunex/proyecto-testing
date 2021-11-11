package com.example.proyectotesting.patterns.creational.singleton;

public class Calculator {

	private static Calculator calculadora;
	
	public Calculator() {
		
	}
	
	public static Calculator getCalculator(){

		if(calculadora == null)
			calculadora = new Calculator();
		
		return calculadora;
	}


	/**
	 * Adds the two number provided
	 * and returns the resulting value
	 * */
	public int sum(int num1, int num2) {
		return num1 + num2;
	}
}
