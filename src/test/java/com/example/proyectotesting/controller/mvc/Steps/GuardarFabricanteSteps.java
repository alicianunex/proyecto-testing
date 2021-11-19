package com.example.proyectotesting.controller.mvc.Steps;


import com.example.proyectotesting.controller.mvc.Pages.IndexManufacturerPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.junit.CucumberOptions;
import  io.cucumber.java.en.When;

import static com.example.proyectotesting.controller.mvc.Pages.Driver.closeDriver;
import static com.example.proyectotesting.controller.mvc.Pages.EditManufacturerPage.*;
import static com.example.proyectotesting.controller.mvc.Pages.IndexManufacturerPage.*;


@CucumberOptions(features = "src/test/resources/Cucumber.Features/GuardarFabricante.feature")
public class GuardarFabricanteSteps{


    @Given("pagina de inicio")
    public void pagina_de_inicio() {

        getManufacturerIndex();
        clickonNuevoManufacturer();
    }


    @When("Hago click en guardar {string}")
    public void hago_click_en_guardar(String fabricante) {

        selectInput();
        rellenarInput(fabricante);
        clickonGuardar();
    }

    @Then("El fabricante se añade a DB {string}")
    public void el_fabricante_se_añade_a_db(String fabricante) {

        checkNewManufacturer(fabricante);
        IndexManufacturerPage.eraseNewManufacturer();
        closeDriver();
    }
}
