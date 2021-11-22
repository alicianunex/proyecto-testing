package com.example.proyectotesting.controller.mvc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Product View Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ProductViewTest {

    // http://localhost:8080/manufacturers
    // https://proyecto-testinggrupo2.herokuapp.com

    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        chromewebDriver = new ChromeDriver(options);
/*
        String dir = System.getProperty("user.dir");

//        String driverUrl = "C:\\data\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver",driverUrl);
        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        chromewebDriver = new ChromeDriver();

 */
        //chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers");
        chromewebDriver.get("http://localhost:8080/manufacturers");
        js = (JavascriptExecutor) chromewebDriver;
//        chromewebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // TODO Firefox driver setup

        /*
        String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver",driverUrl);
        firefoxwebDriver = new FirefoxDriver();
        */
    }

    @AfterEach
    void tearDown() {
//      firefoxwebDriver.quit();
        chromewebDriver.quit();
    }


    @Nested
    @DisplayName("Check titles and subtitles")
    public class CheckHeaders {

        /**
         *  Checks if the Title of the page is OK in the head tag and in the display
         */
        @Test
        @DisplayName("Title in view page is displayed and stored correctly")
        void CheckTitleTextTest () {

            accessFromProducts(0);
            assertTrue(chromewebDriver.
                    findElement(By.cssSelector("h2")).getText().contains("Producto "));
            assertEquals("Product View | Aswesome App", chromewebDriver.getTitle());
        }

        /**
         * SubTitles are displayed  correctly
         */
        @Test
        @DisplayName("SubTitles are displayed  correctly")
        void CheckSubtitleTextTest () {

            accessFromProducts(0);

            assertTrue(chromewebDriver.
                    findElement(By.cssSelector("h3")).getText().contains("Categorías asociadas"));

            List<WebElement> subtitles = chromewebDriver.findElements(By.xpath("//b"));
            List<String> subtitlelist = Arrays.asList("Nombre", "Descripción", "Precio", "Cantidad", "Fabricante");
            for (int count= 0 ;count < subtitles.size() ;count++) {
                assertTrue(subtitles.get(count).getText().contains(subtitlelist.get(count)));
            }
        }
    }

    /**
     *   Enters product/view from anchor in /manufacturers")
     * @param name of the product to access linked page
     * */
    private void accessFromManufacturer(String name){

        chromewebDriver.get("http://localhost:8080/manufacturers");

        if (name.toLowerCase().contains("balon")) {
            WebElement webElement = chromewebDriver.findElement(By.xpath("//a[contains(text(),'Balón')]"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }else if (name.toLowerCase().contains("mesa")) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Mesa')]")).get(1);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }else if (name.toLowerCase().contains("botella")) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Botella')]")).get(2);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }else if (name.toLowerCase().contains("webcam")) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Webcam')]")).get(3);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }else if (name.toLowerCase().contains("zapatillas")) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Zapatillas')]")).get(4);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        } else {
            System.out.println("Product not recognized");
            List<WebElement> webElement = chromewebDriver.findElements(By.xpath("//a[contains(@href,'/view')]"));
            js.executeScript("arguments[0].scrollIntoView();", webElement.get(webElement.size() - 1));
            webElement.get(webElement.size() - 1).click();
        }

    }

    /**
     *   Enters product/view from button in /products")
     * @param index of the manufacturer to access linked page
     * */
    private void accessFromProducts(int index){

//        chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products");
        chromewebDriver.get("http://localhost:8080/products");

        if (index < 5) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Ver')]")).get(index);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        } else {
            System.out.println("Product not recognized");
            List<WebElement> webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Ver')]"));
            js.executeScript("arguments[0].scrollIntoView();", webElement.get(webElement.size() - 1));
            webElement.get(webElement.size() - 1).click();
        }
    }

}
