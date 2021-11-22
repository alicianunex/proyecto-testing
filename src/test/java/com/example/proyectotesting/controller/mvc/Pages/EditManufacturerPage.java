package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class EditManufacturerPage {

      List<List> outerobjdata;
      List<WebElement> inputs;
      Driver driver = new Driver();


    public EditManufacturerPage() {
    }

    public void getManufacturerNew() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
         driver.ChromewebDriver = new ChromeDriver(options);

//         driver.ChromewebDriver = new ChromeDriver();
//        Path path = Paths.get("C:\\data\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver",path.toString());

//         driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/new");
         driver.ChromewebDriver.get("http://localhost:8080/manufacturers/new");
        driver.js = (JavascriptExecutor)  driver.ChromewebDriver;
    }

    public void getManufacturerEdit(Long id) {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
         driver.ChromewebDriver = new ChromeDriver(options);

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());

//         driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/" + id + "/edit");
         driver.ChromewebDriver.get("http://localhost:8080/manufacturers/" + id + "/edit");

        driver.js = (JavascriptExecutor)  driver.ChromewebDriver;
    }

    public void SelectInput() {
        inputs =  driver.ChromewebDriver.findElements(By.xpath("//input[@type='text']"));
    }

    public void fillInput(String fabricante) {

        String[] keys = fabricante.split(" ");
        List<String> keysdef = new ArrayList<>();

        for (int count = 0; count < keys.length; count++) {
            keysdef.add(keys[count]);
        }
        keysdef.set(4, (keysdef.get(4) + " " + keysdef.get(5)));
        keysdef.remove(5);

        for (int count = 0; count < keysdef.size(); count++) {
            inputs.get(count).sendKeys(keysdef.get(count));
        }
    }

    public void ClickonGuardar() {
        WebElement buttonsave =  driver.ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        driver.js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();
    }

    public void fillOption(String fabricante) {
        List<WebElement> options =  driver.ChromewebDriver.findElements(By.xpath("//option"));
        Actions action = new Actions( driver.ChromewebDriver);

        if (fabricante.toLowerCase().contains("adidas")) {
            action.keyDown(Keys.CONTROL);
            options.get(0).click();
            options.get(1).click();
            options.get(2).click();
            action.perform();
            action.keyUp(Keys.CONTROL).perform();
        } else if (fabricante.toLowerCase().contains("nike")){
            action.keyDown(Keys.CONTROL);
            options.get(3).click();
            action.perform();
            action.keyUp(Keys.CONTROL).perform();
        }
    }
}





