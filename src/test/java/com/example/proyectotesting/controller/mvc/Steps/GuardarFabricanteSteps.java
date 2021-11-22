package com.example.proyectotesting.controller.mvc.Steps;


import com.example.proyectotesting.controller.mvc.Pages.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.junit.CucumberOptions;
import  io.cucumber.java.en.When;

@CucumberOptions(
        features = "src/test/resources/Cucumber/Features")
public class GuardarFabricanteSteps{

    Driver driver = new Driver();

    IndexManufacturerPage index = new IndexManufacturerPage();
    EditManufacturerPage editM = new EditManufacturerPage();

    @Given("pagina de inicio")
    public void pagina_de_inicio() {

        index.getManufacturerIndex();
        index.clickonNuevoManufacturer();
    }

    @When("Hago click en guardar {string}")
    public void hago_click_en_guardar(String fabricante) {

        editM.SelectInput();
        editM.fillInput(fabricante);
        editM.ClickonGuardar();
    }

    @Then("El fabricante se añade a DB {string}")
    public void el_fabricante_se_añade_a_db(String fabricante) {

        index.checkNewManufacturer(fabricante);
        index.eraseNewManufacturerFromList();
        driver.closeDriver();
    }
}
