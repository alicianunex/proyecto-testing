package com.example.proyectotesting.controller.mvc.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Driver {

    public static JavascriptExecutor js;
    public static WebDriver ChromewebDriver;

    public static void closeDriver() {ChromewebDriver.quit();}



}
