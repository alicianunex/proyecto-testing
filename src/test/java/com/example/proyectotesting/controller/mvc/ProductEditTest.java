package com.example.proyectotesting.controller.mvc;

import com.example.proyectotesting.controller.mvc.Pages.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductEditTest {

    public  List<String> objdata;
    public  List<WebElement> inputs;
    Driver driver = new Driver();


    @BeforeEach
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver.ChromewebDriver = new ChromeDriver(options);

//         driver.ChromewebDriver = new ChromeDriver();

//         driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products/new);
         driver.ChromewebDriver.get("http://localhost:8080/products/new");

        driver.js = (JavascriptExecutor)  driver.ChromewebDriver;
    }

    @AfterEach
    void tearDown() {
        driver.ChromewebDriver.get("http://localhost:8080/products");
        List<WebElement> erasebuttons = driver.ChromewebDriver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
        erasebuttons.get(erasebuttons.size() - 1).click();
        //      firefoxwebDriver.quit();
        driver.closeDriver();
    }

    @Test
    @DisplayName("Text Data is passed correctly")
    public void fillInput() {

        objdata = new ArrayList<>();
        objdata.add("Balón");
        objdata.add("10.99");
        objdata.add("2");


        inputs = driver.ChromewebDriver.findElements(By.xpath("//input[@type='text']"));

        for (int count = 0; count < objdata.size(); count++) {
                inputs.get(count).sendKeys(objdata.get(count));
        }
        driver.ChromewebDriver.findElement(By.cssSelector("textarea")).sendKeys("Lorem impsum dolor");

        // saves
        clickonGuardar();

        objdata = new ArrayList<>();
        objdata.add("Balón");
        objdata.add("10.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("2");

        // checks data
        List<WebElement> columns = driver.ChromewebDriver.findElements(By.cssSelector("tbody tr td"));
        for (int count = 0; count < objdata.size(); count++)
            assertEquals(objdata.get(count), columns.get(count).getText());
    }

    @Test
    public void fillcategories() {
        Actions action = new Actions( driver.ChromewebDriver);
        List<WebElement> options =  driver.ChromewebDriver.findElements(By.cssSelector("#categories option"));
        driver.js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();

        clickonGuardar();

        List<WebElement> column = driver.ChromewebDriver.findElements(By.xpath("//span"));
        assertEquals("Computación",column.get(column.size()-1).getText());
        assertEquals("Libros",column.get(column.size()-2).getText());

    }

    public void selectManufacturer(String fabricante) {
        WebElement option =  driver.ChromewebDriver.findElement(By.cssSelector("#manufacturer"));
        driver.js.executeScript("arguments[0].scrollIntoView();", option);


        if (fabricante.toLowerCase().contains("nike")) {
            WebElement select =  driver.ChromewebDriver.findElement(By.xpath("//label[@for='manufacturer']"));
            select.click();
            Actions action = new Actions( driver.ChromewebDriver);
            action.sendKeys(Keys.ENTER).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        }
    }

    @Test
    @DisplayName("Checks the Guardar button")
    public void clickonGuardar() {
        WebElement buttonsave =  driver.ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        driver.js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();
    }

}
