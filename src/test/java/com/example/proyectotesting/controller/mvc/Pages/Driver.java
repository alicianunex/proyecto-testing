package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {

    public  JavascriptExecutor js;
    public  WebDriver ChromewebDriver;

    public Driver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
       ChromewebDriver = new ChromeDriver(options);
    }

    public void closeDriver() {ChromewebDriver.quit();}



}
