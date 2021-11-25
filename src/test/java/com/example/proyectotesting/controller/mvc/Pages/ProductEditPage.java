package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class ProductEditPage {

    private   List<String> objdata;
    private   List<WebElement> inputs;
    Driver driver = new Driver();

    public ProductEditPage() {
    }

    public void getProductsNew() {
        
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver.ChromewebDriver = new ChromeDriver(options);

//         driver.ChromewebDriver = new ChromeDriver();

         driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products/new");
//        driver.ChromewebDriver.get("http://localhost:8080/products/new");

        driver.js = (JavascriptExecutor) driver.ChromewebDriver;
    }

    public void createbalondata() {

        objdata = new ArrayList<>();
        objdata.add("Balón");
        objdata.add("10.99");
        objdata.add("2");
    }

    public void selectInput() {
        inputs =  driver.ChromewebDriver.findElements(By.xpath("//input[@type='text']"));
    }

    public void fillInput() {

        for (int count = 0; count < objdata.size(); count++) {
            inputs.get(count).sendKeys(objdata.get(count));
        }
         driver.ChromewebDriver.findElement(By.cssSelector("textarea")).sendKeys("Lorem impsum dolor");
    }
    public void fillcategoriesbalon() {
        Actions action = new Actions( driver.ChromewebDriver);
        List<WebElement> options =  driver.ChromewebDriver.findElements(By.cssSelector("#categories option"));
        driver.js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();

    }

    public void clickonGuardar() {
        WebElement buttonsave =  driver.ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        driver.js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();
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

    public void createmesadata() {

        objdata = new ArrayList<>();
        objdata.add("Mesa");
        objdata.add("99.99");
        objdata.add("8");
        //Libros // Computacion // Hogar
    }
    public   void fillcategoriesmesa() {
        Actions action = new Actions( driver.ChromewebDriver);
        List<WebElement> options =  driver.ChromewebDriver.findElements(By.cssSelector("#categories > option"));
        driver.js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        options.get(2).click(); // hogar
        action.perform();
        action.keyUp(Keys.CONTROL).perform();
    }

    public void createbotelladata() {

        objdata = new ArrayList<>();
        objdata.add("Botella");
        objdata.add("99.99");
        objdata.add("5");
        //Libros Moda

    }

    public void fillcategoriesbotella() {
        Actions action = new Actions( driver.ChromewebDriver);
        List<WebElement> options =  driver.ChromewebDriver.findElements(By.cssSelector("#categories > option"));
        driver.js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(3).click(); // moda
        action.perform();
        action.keyUp(Keys.CONTROL).perform();
    }

    public void createwebcamdata() {

        objdata = new ArrayList<>();
        objdata.add("WebCam");
        objdata.add("99.99");
        objdata.add("12");
    }

    public void fillcategorieswebcam() {}

    public void createzapatillasdata() {

        objdata = new ArrayList<>();
        objdata.add("Zapatillas");
        objdata.add("99.99");
        objdata.add("12");
    }

    public void fillcategorieszapatillas() {}
}
