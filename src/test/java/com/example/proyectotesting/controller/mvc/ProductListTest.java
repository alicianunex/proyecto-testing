package com.example.proyectotesting.controller.mvc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Product List Test")
public class ProductListTest {

    // http://localhost:8080/products

    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    @BeforeEach
    void setUp() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        chromewebDriver = new ChromeDriver(options);

//      String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",driverUrl);
        chromewebDriver = new ChromeDriver();
        chromewebDriver.get("http://localhost:8080/products");
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

    @Test
    @DisplayName("Displays the List of products with attributes")
    void CheckDataTest(){

        addStringData();

        List<WebElement>  table = chromewebDriver.findElements(By.cssSelector("tbody tr"));
        List<WebElement> columns;

        for (int row = 0; row < table.size(); row++) {
            if (row == 0)
                columns = table.get(row).findElements(By.xpath("//th"));
            else
                columns = chromewebDriver.findElements(By.xpath("//tbody/tr["+(row+1)+"]/td"));
            for (int column = 0; column < columns.size(); column++){
//                System.out.println(outerobjdata.get(row).get(column) + " __ " + columns.get(column).getText());
                    assertEquals(outerobjdata.get(row).get(column),columns.get(column).getText());
            }
        }
    }

    @Test
    @DisplayName("Title is displayed and stored correctly")
    void CheckTitletextTest(){
        assertEquals("Products Directory", chromewebDriver.
                findElement(By.cssSelector("h1")).getText());
        assertEquals("Product List | Awesome App", chromewebDriver.getTitle());
    }

    @Test
    @DisplayName("Manufacturer links are displayed correctly")
    void CheckManufacturerlinksTest(){

        List<WebElement>  tableanchor = chromewebDriver.findElements(By.cssSelector("tbody  tr:nth-child(5) > a"));
        for (WebElement count: tableanchor) {
                //System.out.println(count.getAttribute("href"));
                assertTrue(count.getAttribute("href").contains("/manufactures/"));

                if (count.getText() == "Adidas")
                   assertEquals("/manufacturers/1/view",count.getAttribute("href"));
                if (count.getText() == "Nike")
                    assertEquals("/manufacturers/3/view",count.getAttribute("href"));
                if (count.getText() == "")
                    assertEquals("/manufacturers//view",count.getAttribute("href"));
                else
                    System.out.println("Manufacturer not recognized");
                    assumeTrue(false);
            }
        }

    public void addStringData() {

        outerobjdata = new ArrayList<>();
        List<String> objdata = new ArrayList<>();
        objdata.add("Name");
        objdata.add("Price");
        objdata.add("Description");
        objdata.add("Quantity");
        objdata.add("Fabricante");
        objdata.add("Categorías");
        objdata.add("Actions");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Balón");
        objdata.add("10.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("2");
        objdata.add("Adidas");
        objdata.add("Libros Computación");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Mesa");
        objdata.add("99.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("8");
        objdata.add("Adidas");
        objdata.add("Libros Computación Hogar");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Botella");
        objdata.add("99.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("5");
        objdata.add("Adidas");
        objdata.add("Libros Moda");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("WebCam");
        objdata.add("99.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("12");
        objdata.add("Nike");
        objdata.add("");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Zapatillas");
        objdata.add("99.99");
        objdata.add("Lorem impsum dolor");
        objdata.add("12");
        objdata.add("");
        objdata.add("");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);
    }
}
