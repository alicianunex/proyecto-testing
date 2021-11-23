package com.example.proyectotesting.controller.mvc.Steps;

import com.example.proyectotesting.controller.mvc.Pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import org.openqa.selenium.chrome.ChromeDriver;

@CucumberOptions(
        features = "src/test/resources/Cucumber/Features")
public class BorrarFabricanteSteps {

    Driver driver = new Driver();
    IndexManufacturerPage index = new IndexManufacturerPage();
    EditManufacturerPage editM = new EditManufacturerPage();
    ProductEditPage editP = new ProductEditPage();
    ViewManufacturerPage view = new ViewManufacturerPage();

    @Given("Crear nuevo fabricante {string}")
    public void crearNuevoFabricante(String fabricante) {

        index.getManufacturerIndex();
        index.clickonNuevoManufacturer();
        editM.SelectInput();
        editM.fillInput(fabricante);
        editM.ClickonGuardar();
        driver.closeDriver();
    }

    @Given("Pagina de inicio")
    public void paginaDeInicio() {index.getManufacturerIndex();}

    @And("Pagina de ver")
    public void paginaDeVer() {

        index.getManufacturerIndex();
        index.clickOnVerManufacturer();
    }

    @When("Hago click en borrar desde lista")
    public void hago_click_en_borrar_desde_lista() {

        index.getManufacturerIndex();
        index.eraseNewManufacturerFromList();
    }

    @When("Hago click en borrar desde ver")
    public void hagoClickEnBorrarDesdeVer() {view.eraseNewManufacturerFromView();}

    @When("Hago click en borrar todos los fabricantes")
    public void hagoClickEnBorrarTodosLosFabricantes() {

        index.getManufacturerIndex();
        index.clickDeleteAllManufacturers();
    }

    @Then("El fabricante se ha borrado de DB")
    public void elFabricanteSeHaBorradoDeDB() {

        index.checkErasedManufacturer();
        driver.closeDriver();
    }

    @Then("Todos los fabricantes se han borrado de DB")
    public void todosLosFabricantesSeHanBorradoDeDB() {

        index.checkEmptyTable();
        driver.closeDriver();
    }

    @And("Se vuelven a generar los fabricantes")
    public void seVuelvenAGenerarLosFabricantes() {

        String fabricanteadidas = "Adidas 2343235325G 60000 1949 Calle falsa 33010 Leon Spain";
        String fabricantenike = "Nike 2343235325G 60000 1977 Calle verdadera 11322 Madrid Spain";

        // Create manufacturers
        editM.getManufacturerNew();
            editM.SelectInput();
            editM.fillInput(fabricanteadidas);
            //fillOption(fabricante);
            editM.ClickonGuardar();
        driver.closeDriver();

        editM.getManufacturerNew();
            editM.SelectInput();
            editM.fillInput(fabricantenike);
            //fillOption(fabricante);
            editM.ClickonGuardar();
        driver.closeDriver();

        // Create Products
        editP.getProductsNew();
            editP.createbalondata();
            editP.selectInput();
            editP.fillInput();
            editP.selectManufacturer("adidas");
            editP.fillcategoriesbalon();
            editP.clickonGuardar();
        driver.closeDriver();

        editP.getProductsNew();
            editP.createmesadata();
            editP.selectInput();
            editP.fillInput();
            editP.selectManufacturer("adidas");
            editP.fillcategoriesmesa();
            editP.clickonGuardar();
        driver.closeDriver();

        editP.getProductsNew();
            editP.createbotelladata();
            editP.selectInput();
            editP.fillInput();
            editP.selectManufacturer("adidas");
            editP.fillcategoriesbotella();
            editP.clickonGuardar();
        driver.closeDriver();

        editP.getProductsNew();
            editP.createwebcamdata();
            editP.selectInput();
            editP.fillInput();
            editP.selectManufacturer("nike");
            editP.fillcategorieswebcam();
            editP.clickonGuardar();
        driver.closeDriver();

        index.getManufacturerIndex();
            index.checkInitialManufacturers();
        driver.closeDriver();
    }


}