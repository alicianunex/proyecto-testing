package com.example.proyectotesting.controller.mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductListTest {


    WebDriver webDriver;
    final String targeturl = "https://demo.openmrs.org/openmrs/login.htm";

    @BeforeEach
    void setUp() {
        String dir = System.getProperty("user.dir");
        String chromeUrl = "\\drivers\\chromedriver.exe";
        String driverurl = dir + chromeUrl;
        System.setProperty("webdriver.chrome.driver",driverurl);
        webDriver = new ChromeDriver(); // Chrome/Chromium
        webDriver.get(targeturl);
    }
    @AfterEach
    void tearDown() {webDriver.quit();}
    @Test
    @DisplayName("")
    void VerColumnasTest(){

        WebElement table = webDriver.findElement(By.cssSelector("table table-striped table-bordered tr:nth-child(2)"));
        //List<WebElement> columns = table.findElements(By.cssSelector("td"));



    }
}
