package com.example.proyectotesting.controller.mvc.drivers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirefoxTest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        String dir = System.getProperty("user.dir");
        String driverUrl = "/drivers/geckodriver.exe";
        String url = dir + driverUrl;
        System.setProperty("webdriver.gecko.driver", url);
        driver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void test1(){
        driver.get("https://github.com/mozilla/geckodriver");
        String title = driver.getTitle();
        assertEquals("GitHub - mozilla/geckodriver: WebDriver for Firefox", title);
    }

    @Test
    void test2(){
        driver.get("https://github.com/mozilla");
        String title = driver.getTitle();
        assertEquals("Mozilla · GitHub", title);
    }
}






