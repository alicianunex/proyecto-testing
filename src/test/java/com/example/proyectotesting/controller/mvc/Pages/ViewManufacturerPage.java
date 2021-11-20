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

import static com.example.proyectotesting.controller.mvc.Pages.Driver.ChromewebDriver;
import static com.example.proyectotesting.controller.mvc.Pages.Driver.js;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ViewManufacturerPage {

    public static void getManufacturerView(Long id){

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        ChromewebDriver = new ChromeDriver();
        //ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/"+id+"/edit");
        ChromewebDriver.get("http://localhost:8080/manufacturers/"+id+"/edit");
        js = (JavascriptExecutor) ChromewebDriver;
    }

    public static void eraseNewManufacturerFromView() {

        WebElement buttonborrar = ChromewebDriver.findElement(By.className("btn-danger"));
        js.executeScript("arguments[0].scrollIntoView();", buttonborrar);
        buttonborrar.click();
    }
}
