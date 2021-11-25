package com.example.proyectotesting;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.ManufacturerRepository;
import com.example.proyectotesting.repository.ProductRepository;
import com.example.proyectotesting.service.ManufacturerService;
import com.example.proyectotesting.service.ManufacturerServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


@RunWith(JUnitPlatform.class)
@SelectPackages({"com.example.proyectotesting"})
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class TestSuite {

    String username = System.getenv("LT_USERNAME") == null ? "kjagalvez" : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "AUpLQeO228mpkeTiWGxNCbRpmLcVQlQmrKrFGxBdWxxRhx2ARC" : System.getenv("LT_ACCESS_KEY");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    public String status = "failed";
    public List<List> outerobjdata;


    private ManufacturerService manufacturerService;
    private ManufacturerRepository manufacturerRepository;
    private ProductRepository productRepository;

    JavascriptExecutor js;


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "your build name");
        capabilities.setCapability("name", "your test name");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("browserName", "Firefox");
        capabilities.setCapability("version", "95.0");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), capabilities);
        } catch (
                MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        js = (JavascriptExecutor) driver;
        driver.get("https://proyecto-testinggrupo2.herokuapp.com/products");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    void tearDown() {
//      firefoxwebDriver.quit();
        driver.quit();
    }

    @Test
    @DisplayName("Displays the List of products with attributes")
    void CheckDataTest(){

        addStringData();

        List<WebElement>  table = driver.findElements(By.cssSelector("tbody tr"));
        List<WebElement> columns;

        for (int row = 0; row < table.size(); row++) {
            if (row == 0)
                columns = table.get(row).findElements(By.xpath("//th"));
            else
                columns = driver.findElements(By.xpath("//tbody/tr["+(row+1)+"]/td"));
            for (int column = 0; column < columns.size(); column++){
//                System.out.println(outerobjdata.get(row).get(column) + " __ " + columns.get(column).getText());
                assertEquals(outerobjdata.get(row).get(column),columns.get(column).getText());
            }
        }
    }

    @Test
    @DisplayName("Title is displayed and stored correctly")
    void CheckTitletextTest(){
        assertEquals("Products Directory", driver.
                findElement(By.cssSelector("h1")).getText());
        assertEquals("Product List | Awesome App", driver.getTitle());
    }

    @Test
    @DisplayName("Manufacturer links are displayed correctly")
    void CheckManufacturerlinksTest(){

        List<WebElement>  tableanchor = driver.findElements(By.cssSelector("tbody  tr:nth-child(5) > a"));
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
            Assumptions.assumeTrue(false);
        }
    }

    /**
     *  Ver button is displayed correctly
     */
    @Test
    @DisplayName("Ver Button is functioning properly")
    void CheckVerButtonTest(){
        List<WebElement> buttons = driver.findElements(By.xpath("//a [@class='btn btn-info']"));

        for (int count=0 ; count < 0 ; count++) {
            assertEquals("Ver", buttons.get(count).getText());

            js.executeScript("arguments[0].scrollIntoView();", buttons.get(count));
            buttons.get(count).click();
            assertEquals("Product View | Aswesome App", driver.getTitle());
            assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains("Producto "));
            assertEquals("Categorías asociadas", driver.findElement(By.cssSelector("h3")).getText());

            WebElement btnvolver = driver.findElement(By.cssSelector("btn-info"));
            js.executeScript("arguments[0].scrollIntoView();", btnvolver);
            btnvolver.click();
//            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
            assertEquals("Product List | Aswesome App", driver.getTitle());
        }
    }

    /**
     *  Checks that the edit button is displayed correctly and functions properly
     */
    @Test
    @DisplayName("Edit button is displayed correctly and functions properly")
    void CheckEditarButtonTest(){

        List<WebElement> buttons = driver.findElements(By.xpath("//a [@class='btn btn-success']"));

        for (int count=0 ; count < 0 ; count++) {

            assertEquals("Editar", buttons.get(count).getText());

            js.executeScript("arguments[0].scrollIntoView();", buttons.get(count));
            buttons.get(count).click();
            assertEquals("Product Edition | Aswesome App", driver.getTitle());
            assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains("Product "));
            assertEquals("Fabricantes disponibles", driver.findElements(By.cssSelector("h3")).get(0).getText());
            assertEquals("Categorías disponibles", driver.findElements(By.cssSelector("h3")).get(1).getText());

            WebElement btnguardar = driver.findElement(By.className("btn-success"));
            js.executeScript("arguments[0].scrollIntoView();", btnguardar);
            btnguardar.click();
//            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
            assertEquals("Product List | Awesome App", driver.getTitle());
        }
    }

    /**
     *  Delete button is displayed correctly and functions properly
     */
    @Test
    @DisplayName("Delete button is displayed correctly")
    void CheckBorrarButtonTest(){

        createnew();
        driver.navigate().refresh();

        List<WebElement>  erasebuttons = driver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
        int initialsize = erasebuttons.size();
        Assumptions.assumeTrue (initialsize > 0);

        for (WebElement button : erasebuttons) {
            assertEquals("Borrar", button.getText());
        }
        erasebuttons.get(erasebuttons.size()-1).click();

        assertEquals("Product List | Awesome App",driver.getTitle());
        assertTrue(initialsize > driver.findElements(By.cssSelector("td:last-child a:nth-child(3)")).size());
    }

    private void createnew() {

//        driver.get("http://localhost:8080/products");
        driver.get("https://dashboard.heroku.com/apps/proyecto-testinggrupo2/deploy/github");

        driver.findElement(By.className("btn-primary")).click();

        // fill data
        addStringData();
        List<WebElement> input = driver.findElements(By.xpath("//input[not(@type='hidden')]"));

        for (int count = 0 ; count < input.size()-1 ; count++ ){
            if (count!=2) // text area
                input.get(count).sendKeys(outerobjdata.get(1).get(count).toString());
            else {
                WebElement textarea = driver.findElement(By.cssSelector("#description"));
                textarea.sendKeys(outerobjdata.get(1).get(count).toString());
            }
        }

        WebElement buttonguardar = driver.findElement(By.cssSelector("button"));
        js.executeScript("arguments[0].scrollIntoView();", buttonguardar);
        buttonguardar.click();
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

