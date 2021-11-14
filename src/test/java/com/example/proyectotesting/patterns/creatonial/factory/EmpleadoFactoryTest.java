package com.example.proyectotesting.patterns.creatonial.factory;

import com.example.proyectotesting.patterns.creational.factory.Empleado;
import com.example.proyectotesting.patterns.creational.factory.EmpleadoFactory;
import com.example.proyectotesting.patterns.creational.factory.EmpleadoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EmpleadoFactoryTest {

    EmpleadoFactory empleadoFactory;

    @BeforeEach
    void setUp() {
        empleadoFactory = new EmpleadoFactory();
    }

    @Test
    void getEmpleadoMecanico() {
        Empleado empleado=empleadoFactory.getEmpleado(EmpleadoType.MECANICO);
        assertEquals("Mecanico",empleado.getClass().getSimpleName());
    }
    @Test
    void getEmpleadoProgramador() {
        Empleado empleado=empleadoFactory.getEmpleado(EmpleadoType.PROGRAMADOR);
        assertEquals("Programador",empleado.getClass().getSimpleName());
    }

}