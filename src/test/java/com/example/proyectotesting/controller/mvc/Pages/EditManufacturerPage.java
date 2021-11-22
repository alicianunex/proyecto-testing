package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.example.proyectotesting.controller.mvc.Pages.Driver.*;

public class EditManufacturerPage {

    static List<List> outerobjdata;
    static List<WebElement> inputs;

    public static void getManufacturerNew() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromewebDriver = new ChromeDriver(options);

//        ChromewebDriver = new ChromeDriver();
//        Path path = Paths.get("C:\\data\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver",path.toString());

//        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/new");
        ChromewebDriver.get("http://localhost:8080/manufacturers/new");
        js = (JavascriptExecutor) ChromewebDriver;
    }

    public static void getManufacturerEdit(Long id) {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromewebDriver = new ChromeDriver(options);

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());

//        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/" + id + "/edit");
        ChromewebDriver.get("http://localhost:8080/manufacturers/" + id + "/edit");

        js = (JavascriptExecutor) ChromewebDriver;
    }

    public static void editSelectInput() {
        inputs = ChromewebDriver.findElements(By.xpath("//input[@type='text']"));
    }

    public static void fillInput(String fabricante) {

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

    public static void editClickonGuardar() {
        WebElement buttonsave = ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();
    }

    public static void fillOption(String fabricante) {
        List<WebElement> options = ChromewebDriver.findElements(By.xpath("//option"));
        Actions action = new Actions(ChromewebDriver);

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





