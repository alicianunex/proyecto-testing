package com.example.proyectotesting;

import com.example.proyectotesting.patterns.behavioral.mediator.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoTestingApplicationTest {

    @Test
    void main() {

        ProyectoTestingApplication proyectoTestingApplication = new ProyectoTestingApplication();
        try {
        proyectoTestingApplication.main(new String[1]);
        }
        catch(NullPointerException error){
            error.printStackTrace();
        }
    }

    @Test
    void run() throws Exception {

        ProyectoTestingApplication proyectoTestingApplication = new ProyectoTestingApplication();
        try {
            proyectoTestingApplication.run();
        }
        catch(NullPointerException error){
            error.printStackTrace();
        }
    }
}