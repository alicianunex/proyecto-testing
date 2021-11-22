package com.example.proyectotesting.controller.mvc.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Driver {

    public  JavascriptExecutor js;
    public  WebDriver ChromewebDriver;

    public Driver() {
    }

    public void closeDriver() {ChromewebDriver.quit();}



}
