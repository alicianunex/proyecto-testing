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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Manufacturer List Test")
public class ManufacturerListTest {

    // http://localhost:8080/manufacturers

    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {



//      String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",driverUrl);
        chromewebDriver = new ChromeDriver();
        chromewebDriver.get("http://localhost:8080/manufactures");
        /*
        String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver",driverUrl);
        firefoxwebDriver = new FirefoxDriver();
        */

        js = (JavascriptExecutor) chromewebDriver;

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
        assertEquals("Listado de fabricantes", chromewebDriver.
                findElement(By.cssSelector("h1")).getText());
        assertEquals("Manufacturer List | Awesome App", chromewebDriver.getTitle());
    }

    @Test
    @DisplayName("Product links are displayed correctly")
    void CheckVerButtonTest(){

        List<WebElement>  tableanchor = chromewebDriver.findElements(By.cssSelector("tbody  tr:last-child > a"));
        for (WebElement count: tableanchor) {
            //System.out.println(count.getAttribute("href"));
            assertTrue(count.getAttribute("href").contains("/manufactures/") && count.getAttribute("href").contains("/view"));
            count.click();
            assertTrue(chromewebDriver.getCurrentUrl().contains("/manufactures/") && chromewebDriver.getCurrentUrl().contains("/view"));

            WebElement buttonver = chromewebDriver.findElements(By.className("btn btn-info")).get(0);
            js.executeScript("arguments[0].scrollIntoView();", buttonver);
            buttonver.click();
        }
    }

    @Test
    @DisplayName("Product links are displayed correctly")
    void CheckManufacturerlinksTest(){

        List<WebElement>  tableanchor = chromewebDriver.findElements(By.cssSelector("tbody  tr:nth-child(7) > a"));
        for (WebElement count: tableanchor) {
                //System.out.println(count.getAttribute("href"));
                assertTrue(count.getAttribute("href").contains("/manufactures/"));

                if (count.getText() == "Balón")
                   assertEquals("/products/9/view",count.getAttribute("href"));
                if (count.getText() == "Mesa")
                    assertEquals("/products/10/view",count.getAttribute("href"));
                if (count.getText() == "Botella")
                    assertEquals("/products/11/view",count.getAttribute("href"));
            if (count.getText() == "WebCam")
                assertEquals("/products/12/view",count.getAttribute("href"));
                else
                    System.out.println("Product not recognized");
                    assumeTrue(false);
            }
        }

    public void addStringData() {

        outerobjdata = new ArrayList<>();
        List<String> objdata = new ArrayList<>();
        objdata.add("Name");
        objdata.add("CIF");
        objdata.add("Nº Empleados");
        objdata.add("Año fundación");
        objdata.add("Calle");
        objdata.add("País");
        objdata.add("Productos");
        objdata.add("Actions");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Adidas");
        objdata.add("2343235325G");
        objdata.add("60000");
        objdata.add("1949");
        objdata.add("Calle falsa");
        objdata.add("Spain");
        objdata.add("Balón Mesa Botella");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Nike");
        objdata.add("2343235325G");
        objdata.add("60000");
        objdata.add("1977");
        objdata.add("Calle verdadera");
        objdata.add("Spain");
        objdata.add("WebCam");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

    }
}
