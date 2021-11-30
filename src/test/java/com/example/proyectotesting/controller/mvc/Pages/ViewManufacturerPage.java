package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ViewManufacturerPage {

    Driver driver = new Driver();

    public ViewManufacturerPage() {
    }

    public   void getManufacturerView(Long id){

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver.ChromewebDriver = new ChromeDriver(options);

//
//        Path path = Paths.get("C:\\data\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver",path.toString());
//        driver.ChromewebDriver = new ChromeDriver();

//        driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/"+id+"/edit");
        driver.ChromewebDriver.get("http://localhost:8080/manufacturers/"+id+"/edit");
        driver.js = (JavascriptExecutor) driver.ChromewebDriver;
    }

    public   void eraseNewManufacturerFromView() {

        WebElement buttonborrar = driver.ChromewebDriver.findElement(By.cssSelector(".btn-danger"));
        driver.js.executeScript("arguments[0].scrollIntoView();", buttonborrar);
        buttonborrar.click();
    }
}
