package com.example.proyectotesting.controller.mvc.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.example.proyectotesting.controller.mvc.Pages.Driver.*;

public class EditManufacturerPage {

    static List<List> outerobjdata;
    static List<WebElement> inputs;

    public static void getManufacturerNew(){


        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());

        ChromewebDriver = new ChromeDriver();
        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/new");
        //        chromewebDriver.get("http://localhost:8080/manufacturers");
        js = (JavascriptExecutor) ChromewebDriver;

    }

    public static void getManufacturerEdit(Long id){

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        ChromewebDriver = new ChromeDriver();
        ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/"+id+"/edit");
        //        chromewebDriver.get("http://localhost:8080/manufacturers");
        js = (JavascriptExecutor) ChromewebDriver;
    }

    public static void selectInput() {
        inputs = ChromewebDriver.findElements(By.xpath("//input[@type='text']"));
    }

    public static void rellenarInput(String fabricante) {

        for (int count = 0; count > inputs.size(); count++) {
            inputs.get(count).sendKeys(fabricante.split(" ")[count]);
        }
    }

    public static void clickonGuardar() {
        WebElement buttonsave = ChromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();

    }
}

