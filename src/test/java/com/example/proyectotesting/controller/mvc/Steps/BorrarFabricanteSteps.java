package com.example.proyectotesting.controller.mvc.Steps;

import com.example.proyectotesting.controller.mvc.Pages.ProductEditPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;

import static com.example.proyectotesting.controller.mvc.Pages.Driver.closeDriver;
import static com.example.proyectotesting.controller.mvc.Pages.EditManufacturerPage.*;
import static com.example.proyectotesting.controller.mvc.Pages.IndexManufacturerPage.*;
import static com.example.proyectotesting.controller.mvc.Pages.ViewManufacturerPage.eraseNewManufacturerFromView;
import static com.example.proyectotesting.controller.mvc.Pages.ProductEditPage.*;

@CucumberOptions(
        features = "src/test/resources/Cucumber/Features")
public class BorrarFabricanteSteps {

    @Given("Crear nuevo fabricante {string}")
    public void crearNuevoFabricante(String fabricante) {

        getManufacturerIndex();
        clickonNuevoManufacturer();
        editSelectInput();
        fillInput(fabricante);
        editClickonGuardar();
        closeDriver();
    }

    @Given("Pagina de inicio")
    public void paginaDeInicio() {
        getManufacturerIndex();
    }

    @And("Pagina de ver")
    public void paginaDeVer() {

        getManufacturerIndex();
        clickOnVerManufacturer();
    }

    @When("Hago click en borrar desde lista")
    public void hago_click_en_borrar_desde_lista() {

        getManufacturerIndex();
        eraseNewManufacturerFromList();
    }

    @When("Hago click en borrar desde ver")
    public void hagoClickEnBorrarDesdeVer() {eraseNewManufacturerFromView();}

    @When("Hago click en borrar todos los fabricantes")
    public void hagoClickEnBorrarTodosLosFabricantes() {

        getManufacturerIndex();
        clickDeleteAllManufacturers();
    }

    @Then("El fabricante se ha borrado de DB")
    public void elFabricanteSeHaBorradoDeDB() {

        checkErasedManufacturer();
        closeDriver();
    }

    @Then("Todos los fabricantes se han borrado de DB")
    public void todosLosFabricantesSeHanBorradoDeDB() {

        checkEmptyTable();
        closeDriver();
    }

    @And("Se vuelven a generar los fabricantes")
    public void seVuelvenAGenerarLosFabricantes() {
        String fabricanteadidas = "Adidas 2343235325G 60000 1949 Calle falsa 33010 Leon Spain";
        String fabricantenike = "Nike 2343235325G 60000 1977 Calle verdadera 11322 Madrid Spain";

        // Create manufacturers
        getManufacturerNew();
            editSelectInput();
            fillInput(fabricanteadidas);
            //fillOption(fabricante);
            editClickonGuardar();
        closeDriver();

        getManufacturerNew();
            editSelectInput();
            fillInput(fabricantenike);
            //fillOption(fabricante);
            editClickonGuardar();
        closeDriver();

        // Create Products
        getProductsNew();
            createbalondata();
            ProductEditPage.selectInput();
            ProductEditPage.fillInput();
            ProductEditPage.selectManufacturer("adidas");
            ProductEditPage.fillcategoriesbalon();
            ProductEditPage.clickonGuardar();
        closeDriver();

        getProductsNew();
            createmesadata();
            ProductEditPage.selectInput();
            ProductEditPage.fillInput();
            ProductEditPage.selectManufacturer("adidas");
            ProductEditPage.fillcategoriesmesa();
            ProductEditPage.clickonGuardar();
        closeDriver();

        getProductsNew();
            createbotelladata();
            ProductEditPage.selectInput();
            ProductEditPage.fillInput();
            ProductEditPage.selectManufacturer("adidas");
            ProductEditPage.fillcategoriesbotella();
            ProductEditPage.clickonGuardar();
        closeDriver();

        getProductsNew();
            createwebcamdata();
            ProductEditPage.selectInput();
            ProductEditPage.fillInput();
            ProductEditPage.selectManufacturer("nike");
            ProductEditPage.fillcategorieswebcam();
            ProductEditPage.clickonGuardar();
        closeDriver();

        getManufacturerIndex();
            checkInitialManufacturers();
        closeDriver();
    }


}