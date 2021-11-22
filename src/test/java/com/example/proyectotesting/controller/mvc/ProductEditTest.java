package com.example.proyectotesting.controller.mvc;

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

import static com.example.proyectotesting.controller.mvc.Pages.Driver.ChromewebDriver;
import static com.example.proyectotesting.controller.mvc.Pages.Driver.js;

public class ProductEditTest {

    public static List<String> objdata;
    public static List<WebElement> inputs;

    public static void getProductsNew() {


        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromewebDriver = new ChromeDriver(options);

//        ChromewebDriver = new ChromeDriver();

//        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products/new);
        ChromewebDriver.get("http://localhost:8080/products/new");

        js = (JavascriptExecutor) ChromewebDriver;
    }

    public static void createbalondata() {

        objdata = new ArrayList<>();
        objdata.add("Balón");
        objdata.add("10.99");
        objdata.add("2");
    }

    public static void selectInput() {
        inputs = ChromewebDriver.findElements(By.xpath("//input[@type='text']"));
    }

    public static void fillInput() {

        for (int count = 0; count < objdata.size(); count++) {
            inputs.get(count).sendKeys(objdata.get(count));
        }
        ChromewebDriver.findElement(By.cssSelector("textarea")).sendKeys("Lorem impsum dolor");
    }
    public static void fillcategoriesbalon() {
        Actions action = new Actions(ChromewebDriver);
        List<WebElement> options = ChromewebDriver.findElements(By.cssSelector("#categories option"));
        js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();

    }

    public static void clickonGuardar() {
        WebElement buttonsave = ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();
    }

    public static void selectManufacturer(String fabricante) {
        WebElement option = ChromewebDriver.findElement(By.cssSelector("#manufacturer"));
        js.executeScript("arguments[0].scrollIntoView();", option);


        if (fabricante.toLowerCase().contains("nike")) {
            WebElement select = ChromewebDriver.findElement(By.xpath("//label[@for='manufacturer']"));
            select.click();
            Actions action = new Actions(ChromewebDriver);
            action.sendKeys(Keys.ENTER).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        }
    }

    public static void createmesadata() {

        objdata = new ArrayList<>();
        objdata.add("Mesa");
        objdata.add("99.99");
        objdata.add("8");
        //Libros // Computacion // Hogar
    }
    public static void fillcategoriesmesa() {
        Actions action = new Actions(ChromewebDriver);
        List<WebElement> options = ChromewebDriver.findElements(By.cssSelector("#categories > option"));
        js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();
    }

    public static void createbotelladata() {

        objdata = new ArrayList<>();
        objdata.add("Botella");
        objdata.add("99.99");
        objdata.add("5");
        //Libros Moda

    }

    public static void fillcategoriesbotella() {
        Actions action = new Actions(ChromewebDriver);
        List<WebElement> options = ChromewebDriver.findElements(By.cssSelector("#categories > option"));
        js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();
    }

    public static void createwebcamdata() {

        objdata = new ArrayList<>();
        objdata.add("WebCam");
        objdata.add("99.99");
        objdata.add("12");

    }

    public static void fillcategorieswebcam() {
        Actions action = new Actions(ChromewebDriver);
        List<WebElement> options = ChromewebDriver.findElements(By.cssSelector("#categories > option"));
        js.executeScript("arguments[0].scrollIntoView();", options.get(options.size()-1));

        // TODO for(options) if (.gettext contains libros) then .click
        action.keyDown(Keys.CONTROL);
        options.get(0).click(); // libros
        options.get(1).click(); // computacion
        action.perform();
        action.keyUp(Keys.CONTROL).perform();
    }


}
