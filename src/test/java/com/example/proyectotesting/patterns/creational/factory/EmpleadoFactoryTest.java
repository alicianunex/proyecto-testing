package com.example.proyectotesting.patterns.creational.factory;

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
    @Test
    void getDefault() {
        assertThrows(IllegalArgumentException.class,
                () -> empleadoFactory.getEmpleado(EmpleadoType.valueOf("aa")));
    }

}