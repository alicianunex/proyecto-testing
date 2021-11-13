package com.example.proyectotesting.controller.mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;



/**
 * https://demoqa.com/buttons
 */
public class ProductBotones {

    private static final String URL = "https://demoqa.com/buttons";
    WebDriver driver;
    JavascriptExecutor js;


    @BeforeEach
    void setUp() {
        String dir = System.getProperty("user.dir"); // ruta del proyecto
        String driverUrl = "/controller/mvc/drivers/ChromeTest.java";
        String url = dir + driverUrl;
        System.setProperty("webdriver.chrome.driver", url);
        driver = new ChromeDriver(); // Google Chrome
        js = (JavascriptExecutor) driver;
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    void doubleClickTest() {

        // comprobar que no hay ningún texto
        assertEquals(0,  driver.findElements(By.id("doubleClickMessage")).size());

        // hacer doble click
        WebElement button = driver.findElement(By.id("doubleClickBtn"));

        // Este planteamiento no funciona para hacer double click
        // button.click();
        // button.click();
        Actions action = new Actions(driver);
        action.doubleClick(button).perform();

        // comprobar que si aparece un texto
        WebElement message = driver.findElement(By.id("doubleClickMessage"));
        assertEquals("You have done a double click", message.getText());

    }


    /**
     * Test para comprobar la acción botón click derecho
     */
    @Test
    @DisplayName("Clic derecho mostrar texto párrafo")
    void rightClickTest() {

        assertEquals(0,  driver.findElements(By.id("rightClickMessage")).size());

        // hacer doble click
        WebElement button = driver.findElement(By.id("rightClickBtn"));

        // Este planteamiento no funciona para hacer double click
        // button.click();
        // button.click();
        Actions action = new Actions(driver);
        action.contextClick(button).perform();

        // comprobar que si aparece un texto
        WebElement message = driver.findElement(By.id("rightClickMessage"));
        assertEquals("You have done a right click", message.getText());

    }

    @Test
    void clickTest() {

        assertEquals(0,  driver.findElements(By.id("dynamicClickMessage")).size());

        // hacer doble click
        WebElement button = driver.findElement(By.xpath("//button[text() = 'Click Me']"));
        button.click();

        // comprobar que si aparece un texto
        WebElement message = driver.findElement(By.id("dynamicClickMessage"));
        assertEquals("You have done a dynamic click", message.getText());

    }

}
