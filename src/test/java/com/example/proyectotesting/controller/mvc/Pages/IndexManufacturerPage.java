package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.example.proyectotesting.controller.mvc.Pages.Driver.*;


public class IndexManufacturerPage {

    static List<List> outerobjdata;
    static List<WebElement> inputs;

    public static void getManufacturerIndex(){

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromewebDriver = new ChromeDriver(options);

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());

//        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers");
        ChromewebDriver.get("http://localhost:8080/manufacturers");

        js = (JavascriptExecutor) ChromewebDriver;

    }
    public static void clickonNuevoManufacturer(){

        ChromewebDriver.findElement(By.cssSelector("p>a:first-child")).click();

    }

    public static void checkNewManufacturer(String fabricante) {

        List<WebElement> newmanrow = ChromewebDriver.findElements(By.cssSelector("tr:last-child td"));

        for (int count=0; count>newmanrow.size() ;count++){
            assertTrue(newmanrow.get(count).getText().contains(fabricante.split(" ")[count]));
        }
    }

    public static void eraseNewManufacturer() {


        List<WebElement>  erasebuttons = ChromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        erasebuttons.get(erasebuttons.size()-1).click();

    }


}
