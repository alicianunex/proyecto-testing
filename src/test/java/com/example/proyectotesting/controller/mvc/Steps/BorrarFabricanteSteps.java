package com.example.proyectotesting.controller.mvc.Steps;

import com.example.proyectotesting.controller.mvc.Pages.IndexManufacturerPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;

import static com.example.proyectotesting.controller.mvc.Pages.Driver.closeDriver;
import static com.example.proyectotesting.controller.mvc.Pages.EditManufacturerPage.*;
import static com.example.proyectotesting.controller.mvc.Pages.IndexManufacturerPage.*;
import static com.example.proyectotesting.controller.mvc.Pages.ViewManufacturerPage.eraseNewManufacturerFromView;

@CucumberOptions(
        features = "src/test/resources/Cucumber/Features")
public class BorrarFabricanteSteps {
    
    @Given("pagina de inicio {string}")
    public void crear_nuevo_fabricante(String fabricante) {

        getManufacturerIndex();
        clickonNuevoManufacturer();
        selectInput();
        fillInput(fabricante);
        clickonGuardar();
    }

    @And("Pagina de ver {string}")
    public void pagina_de_ver() {

        clickOnVerManufacturer();
    }

    @When("Hago click en borrar desde lista")
    public void hago_click_en_borrar_desde_lista() {

        eraseNewManufacturerFromList();
    }

    @When("Hago click en borrar desde ver")
    public void hagoClickEnBorrarDesdeVer() {

        eraseNewManufacturerFromView();
    }

    @When("Hago click en borrar todos los fabricantes")
    public void hagoClickEnBorrarTodosLosFabricantes() {

        clickDeleteAllManufacturers();
    }

    @Then("El fabricante se ha borrado de DB {string}")
    public void el_fabricante_se_ha_borrado_de_db() {

        checkErasedManufacturer();
        closeDriver();
    }

    @Then("Todos los fabricantes se han borrado de DB {string}")
    public void todosLosFabricantesSeHanBorradoDeDB(String fabricante) {

        checkEmptyTable();
        recreateManufacturers();
    }
}