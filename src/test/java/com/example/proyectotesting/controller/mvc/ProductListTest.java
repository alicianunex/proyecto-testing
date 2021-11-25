package com.example.proyectotesting.controller.mvc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Product List Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ProductListTest {

    // http://localhost:8080/products
    // https://proyecto-testinggrupo2.herokuapp.com

    WebDriver firefoxwebDriver;
    WebDriver chromewebDriver;

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

        chromewebDriver.navigate().to("https://proyecto-testinggrupo2.herokuapp.com/products");
//        chromewebDriver.get("http://localhost:8080/products");
        chromewebDriver.manage().window().maximize();
        chromewebDriver.manage().timeouts().implicitlyWait(5, TimeUnit.MILLISECONDS);
        js = (JavascriptExecutor) chromewebDriver;

    /*
        // Chrome driver

     String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",driverUrl);

        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        chromewebDriver = new ChromeDriver();

        chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products");
        chromewebDriver.get("http://localhost:8080/products");
        chromewebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // Firefox driver

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

    /**
     *  Ver button is displayed correctly
     */
    @Test
    @DisplayName("Ver Button is functioning properly")
    void CheckVerButtonTest(){
        List<WebElement> buttons = chromewebDriver.findElements(By.xpath("//a [@class='btn btn-info']"));

        for (int count=0 ; count < 0 ; count++) {
            assertEquals("Ver", buttons.get(count).getText());

            js.executeScript("arguments[0].scrollIntoView();", buttons.get(count));
            buttons.get(count).click();
            assertEquals("Product View | Aswesome App", chromewebDriver.getTitle());
            assertTrue(chromewebDriver.findElement(By.cssSelector("h2")).getText().contains("Producto "));
            assertEquals("Categorías asociadas", chromewebDriver.findElement(By.cssSelector("h3")).getText());

            WebElement btnvolver = chromewebDriver.findElement(By.cssSelector("btn-info"));
            js.executeScript("arguments[0].scrollIntoView();", btnvolver);
            btnvolver.click();
//            chromewebDriver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
            assertEquals("Product List | Aswesome App", chromewebDriver.getTitle());
        }
    }

    /**
     *  Checks that the edit button is displayed correctly and functions properly
     */
    @Test
    @DisplayName("Edit button is displayed correctly and functions properly")
    void CheckEditarButtonTest(){

        List<WebElement> buttons = chromewebDriver.findElements(By.xpath("//a [@class='btn btn-success']"));

        for (int count=0 ; count < 0 ; count++) {

            assertEquals("Editar", buttons.get(count).getText());

            js.executeScript("arguments[0].scrollIntoView();", buttons.get(count));
            buttons.get(count).click();
            assertEquals("Product Edition | Aswesome App", chromewebDriver.getTitle());
            assertTrue(chromewebDriver.findElement(By.cssSelector("h2")).getText().contains("Product "));
            assertEquals("Fabricantes disponibles", chromewebDriver.findElements(By.cssSelector("h3")).get(0).getText());
            assertEquals("Categorías disponibles", chromewebDriver.findElements(By.cssSelector("h3")).get(1).getText());

            WebElement btnguardar = chromewebDriver.findElement(By.className("btn-success"));
            js.executeScript("arguments[0].scrollIntoView();", btnguardar);
            btnguardar.click();
//            chromewebDriver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
            assertEquals("Product List | Awesome App", chromewebDriver.getTitle());
        }
    }

    /**
     *  Delete button is displayed correctly and functions properly
     */
    @Test
    @DisplayName("Delete button is displayed correctly")
    void CheckBorrarButtonTest(){

        createnew();
        chromewebDriver.navigate().refresh();

        List<WebElement>  erasebuttons = chromewebDriver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
        int initialsize = erasebuttons.size();
        assumeTrue (initialsize > 0);

        for (WebElement button : erasebuttons) {
            assertEquals("Borrar", button.getText());
        }
        erasebuttons.get(erasebuttons.size()-1).click();

        assertEquals("Product List | Awesome App",chromewebDriver.getTitle());
        assertTrue(initialsize > chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)")).size());
    }

    private void createnew() {

//        chromewebDriver.get("http://localhost:8080/products");
        chromewebDriver.get("https://dashboard.heroku.com/apps/proyecto-testinggrupo2/deploy/github");

        chromewebDriver.findElement(By.className("btn-primary")).click();

        // fill data
        addStringData();
        List<WebElement> input = chromewebDriver.findElements(By.xpath("//input[not(@type='hidden')]"));

        for (int count = 0 ; count < input.size()-1 ; count++ ){
            if (count!=2) // text area
                input.get(count).sendKeys(outerobjdata.get(1).get(count).toString());
            else {
                WebElement textarea = chromewebDriver.findElement(By.cssSelector("#description"));
                textarea.sendKeys(outerobjdata.get(1).get(count).toString());
            }
        }

        WebElement buttonguardar = chromewebDriver.findElement(By.cssSelector("button"));
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
