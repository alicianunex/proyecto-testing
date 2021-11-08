package com.example.proyectotesting.controller.mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ProductListTest {

    WebDriver webDriver;

    @BeforeEach
    void setUp() {
        String dir = System.getProperty("user.dir");
        String driverUrl = "/drivers/geckodriver.exe";
        String url = dir + driverUrl;
        System.setProperty("webdriver.gecko.driver", url);
        webDriver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Test
    @DisplayName("")
    void VerFilasTest(){

        webDriver.findElement(By.className("table table-striped table-bordered"")


    }

    @Test
    @DisplayName("")
    void ProductDisplayTest(){

        webDriver.findElement(By.)


    }
}
