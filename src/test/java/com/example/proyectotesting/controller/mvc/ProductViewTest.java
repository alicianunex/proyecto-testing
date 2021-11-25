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
import java.util.concurrent.TimeUnit;

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
        chromewebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

        //chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers");
        chromewebDriver.get("http://localhost:8080/products");
        js = (JavascriptExecutor) chromewebDriver;
        createnew();
        List<WebElement> verbtn = chromewebDriver.findElements(By.xpath("//a[@class='btn btn-info']"));
        accessFromProducts(verbtn.size() - 1);


/*
        String dir = System.getProperty("user.dir");

//        String driverUrl = "C:\\data\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver",driverUrl);
        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        chromewebDriver = new ChromeDriver();

 */

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
        chromewebDriver.get("http://localhost:8080/products");
        List<WebElement> erasebuttons = chromewebDriver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
        erasebuttons.get(erasebuttons.size() - 1).click();
        //      firefoxwebDriver.quit();
        chromewebDriver.quit();
    }

    @Nested
    @DisplayName("Check titles and subtitles")
    public class CheckHeaders {

        /**
         * Checks if the Title of the page is OK in the head tag and in the display
         */
        @Test
        @DisplayName("Title in view page is displayed and stored correctly")
        void CheckTitleTextTest() {

            assertTrue(chromewebDriver.
                    findElement(By.cssSelector("h2")).getText().contains("Producto "));
            assertEquals("Product View | Aswesome App", chromewebDriver.getTitle());
        }

        /**
         * SubTitles are displayed  correctly
         */
        @Test
        @DisplayName("SubTitles are displayed correctly")
        void CheckSubtitleTextTest() {

            assertTrue(chromewebDriver.
                    findElement(By.cssSelector("h3")).getText().contains("Categorías asociadas"));

            List<WebElement> subtitles = chromewebDriver.findElements(By.xpath("//b"));
            List<String> subtitlelist = Arrays.asList("Nombre", "Descripción", "Precio", "Cantidad", "Fabricante");

            for (int count = 0; count < subtitles.size(); count++) {
                assertTrue(subtitles.get(count).getText().contains(subtitlelist.get(count)));
            }
        }
    }

    /**
     * Data is displayed correctly
     */
    @Test
    @DisplayName("Data is displayed correctly")
    void CheckDataTextTest() {

        List<WebElement> subtitles = chromewebDriver.findElements(By.xpath("//p"));
        List<String> datalist = Arrays.asList("Balón", "Lorem ipsum dolor", "10.99", "2", "Adidas");

        for (int count = 0; count < subtitles.size(); count++) {
            System.out.println(subtitles.get(count).getText() + " __ " + datalist.get(count));
            assertTrue(subtitles.get(count).getText().contains(datalist.get(count)));
        }
    }

    /**
     * Ver button is displayed correctly
     */
    @Test
    @DisplayName("Checks the categorias data is displayed")
    void CheckCategoriasTest() {

        List<WebElement> categorias = chromewebDriver.findElements(By.xpath("//span"));
        String[] categoriastext = outerobjdata.get(1).get(4).toString().split(" ");
        for (int count = 0; count < categorias.size(); count++) {
            assertEquals(categoriastext[count], categorias.get(count).getText());
        }
    }

    @Nested
    @DisplayName("Check buttons")
    public class buttons {

        /**
         * Ver button is displayed correctly
         */
        @Test
        @DisplayName("Ver Button is functioning properly")
        void CheckVerButtonTest() {

            WebElement button = chromewebDriver.findElement(By.xpath("//a [@class='btn btn-info']"));

            assertEquals("Volver", button.getText());
            js.executeScript("arguments[0].scrollIntoView();", button);
            button.click();

            assertEquals("Product List | Awesome App", chromewebDriver.getTitle());
            assertTrue(chromewebDriver.findElement(By.cssSelector("h1")).getText().contains("Products Directory"));
        }

        /**
         * Checks that the edit button is displayed correctly and functions properly
         */
        @Test
        @DisplayName("Edit button is displayed correctly and functions properly")
        void CheckEditarButtonTest() {

            WebElement button = chromewebDriver.findElement(By.xpath("//a [@class='btn btn-success']"));

            assertEquals("Editar", button.getText());

            js.executeScript("arguments[0].scrollIntoView();", button);
            button.click();

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


        /**
         * Delete button is displayed correctly and functions properly
         */
        @Test
        @DisplayName("Delete button is displayed correctly")
        void CheckBorrarButtonTest() {

            chromewebDriver.get("http://localhost:8080/products");

            // count rows
            List<WebElement> erasebuttons = chromewebDriver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
            int initialsize = erasebuttons.size();
            assumeTrue(initialsize > 0);

            // clicks button
            List<WebElement> verbtn = chromewebDriver.findElements(By.xpath("//a[@class='btn btn-info']"));
            accessFromProducts(verbtn.size() - 1);

            WebElement erasebutton = chromewebDriver.findElement(By.xpath("//a [@class='btn btn-danger']"));
            js.executeScript("arguments[0].scrollIntoView();", erasebutton);
            erasebutton.click();

            // checks nº of products
            assertEquals("Product List | Awesome App", chromewebDriver.getTitle());
            erasebuttons = chromewebDriver.findElements(By.xpath("//td/a [@class='btn btn-danger']"));
            assertTrue(initialsize > erasebuttons.size());

            createnew();

        }
    }



    private void createnew() {

        chromewebDriver.get("http://localhost:8080/products");
        //chromewebDriver.get("https://dashboard.heroku.com/apps/proyecto-testinggrupo2/deploy/github");

        chromewebDriver.findElement(By.className("btn-primary")).click();

        // fill data
        addStringData();
        List<WebElement> input = chromewebDriver.findElements(By.xpath("//input[not(@type='hidden')]"));

        for (int count = 0 ; count < input.size() ; count++ ){
            input.get(count).sendKeys(outerobjdata.get(1).get(count).toString());
        }
        WebElement textarea = chromewebDriver.findElement(By.cssSelector("#description"));
        textarea.sendKeys("Lorem ipsum dolor");

        WebElement buttonguardar = chromewebDriver.findElement(By.cssSelector("button"));
        js.executeScript("arguments[0].scrollIntoView();", buttonguardar);
        buttonguardar.click();
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

        WebElement webElement = chromewebDriver.findElements(By.xpath("//a[contains(text(),'Ver')]")).get(index);
        js.executeScript("arguments[0].scrollIntoView();", webElement);
        webElement.click();
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
        objdata.add("2");
        objdata.add("Adidas");
        objdata.add("Libros Computación");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Mesa");
        objdata.add("99.99");
        objdata.add("8");
        objdata.add("Adidas");
        objdata.add("Libros Computación Hogar");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("Botella");
        objdata.add("99.99");
        objdata.add("5");
        objdata.add("Adidas");
        objdata.add("Libros Moda");
        objdata.add("Ver Editar Borrar");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();
        objdata.add("WebCam");
        objdata.add("99.99");
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
