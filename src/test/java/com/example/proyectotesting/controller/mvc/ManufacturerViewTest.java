package com.example.proyectotesting.controller.mvc;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Manufacturer View Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ManufacturerViewTest {

    // http://localhost:8080/manufacturers


    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {

        // TODO Phantom broswer for GitHub actions, not working throws CONNECTION ERROR
/*
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        chromewebDriver = new ChromeDriver(options);

 */

        String dir = System.getProperty("user.dir");

//        String driverUrl = "C:\\data\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver",driverUrl);
        Path path = Paths.get("C:\\data\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",path.toString());
        chromewebDriver = new ChromeDriver();
        chromewebDriver.get("http://localhost:8080/manufacturers");
//        chromewebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // TODO Firefox driver setup

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

    /**
     *  Checks if the Title of the page is OK in the head tag and in the display
     */
    @Test
    @DisplayName("Title in Adidas page is displayed and stored correctly")
    void CheckTitleAdidasTextTest(){

        accessFromProducts("Adidas");


        assertEquals("Fabricante 1", chromewebDriver.
                findElement(By.cssSelector("h2")).getText());
        assertEquals("Manufacturer View | Aswesome App", chromewebDriver.getTitle());
    }

    /**
     *  Checks if the Title of the page is OK in the head tag and in the display
     */
    @Test
    @DisplayName("Title is displayed and stored correctly")
    void CheckTitleNikeTextTest(){

        accessFromManufacturer(1);

        assertEquals("Fabricante 3", chromewebDriver.
                findElement(By.cssSelector("h2")).getText());
        assertEquals("Manufacturer View | Aswesome App", chromewebDriver.getTitle());
    }

    /**
     * SubTitles are displayed  correctly
     */
    @Test
    @DisplayName("SubTitles are displayed  correctly")
    void CheckSubtitleAdidasTextTest(){

        accessFromProducts("Adidas");

        assertEquals("Fabricante 1", chromewebDriver.
                findElement(By.cssSelector("h2")).getText());
        assertEquals("Manufacturer View | Aswesome App", chromewebDriver.getTitle());
    }

    /**
     * Subtitles are displayed correctly
     */
    @Test
    @DisplayName("Subtitles are displayed  correctly")
    void CheckSubtitleNikeTextTest(){

        accessFromManufacturer(1);

        List<WebElement> titles = chromewebDriver.findElements(By.cssSelector("h3"));
        assertEquals("Dirección",titles.get(0).getText());
        assertEquals("Productos fabricados",titles.get(1).getText());
    }

    /**
     * Checks that the header and the info is displayed correctly
     */
    @Test
    @DisplayName("Checks that the header and the info is displayed correctly")
    void CheckDataAdidasTest(){

        addStringData();

        accessFromProducts("Adidas");

        List<WebElement> string_data = chromewebDriver.findElements(By.cssSelector("div p"));

        for (int count = string_data.size()-1; count > -1; count-- ){
            assertEquals(outerobjdata.get(0).get(count),string_data.get(count).getText());
        }
    }

    /**
     * Checks that the header and the info is displayed correctly
     */
    @Test
    @DisplayName("Checks that data for Nike is displayed correctly")
    void CheckDataNikeTest(){

        addStringData();

        accessFromManufacturer(1);

        List<WebElement> string_data = chromewebDriver.findElements(By.cssSelector("div p"));

        for (int count = 0 ; count < string_data.size(); count++ ){
            System.out.println(outerobjdata.get(1).get(count) +" __ " +string_data.get(count).getText());
            assertEquals(outerobjdata.get(1).get(count),string_data.get(count).getText());
        }
    }

    /**
     *   Enters manufacturer/view from button in /manufacturers")
     * @param index of the manufacturer to access linked page
     * */
    private void accessFromManufacturer(int index){

        List<WebElement>  verbutton = chromewebDriver.findElements(By.cssSelector("td:last-child a:first-child"));

//        js.executeScript("arguments[0].scrollIntoView();", verbutton);
        verbutton.get(index).click();

    }

    /**
     *   Enters manufacturer/view from button in /manufacturers")
     * @param name of the manufacturer to access linked page
     * */
    private void accessFromProducts(String name){

        chromewebDriver.get("http://localhost:8080/products");

        if (name.contains("Adidas")) {
            WebElement webElement = chromewebDriver.findElements(By.xpath("//td/a[contains(@href, 'view')]")).get(0);
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }
        else if (name.contains("Nike")){
            WebElement webElement = chromewebDriver.findElement(By.cssSelector("tr:nth-child(5) td:nth-child(5) a"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        }
        else
            System.out.println("Manufacturer not recognized");
}

    @Test
    @DisplayName(" Asserts that the product list is displayed correctly")
    void checkAdidasProductList(){

        accessFromProducts("Adidas");

        List<String> stringdata = new ArrayList<>();
        stringdata.add("Balón (10.99 €)");
        stringdata.add("Mesa (99.99 €)");
        stringdata.add("Botella (99.99 €)");
        stringdata.add("Zapatillas (99.99 €)");

        List<WebElement> prodlist = chromewebDriver.findElements(By.xpath("//li/a"));

        for (int count = 0; count < prodlist.size(); count++){
            assertEquals(stringdata.get(count),prodlist.get(count).getText());
            assertTrue(prodlist.get(count).getAttribute("href").contains("/products")
            && prodlist.get(count).getAttribute("href").contains("/view"));
        }
    }

    /**
     *  Ver button is displayed correctly
     */
    @Test
    @DisplayName("Ver Button is functioning properly")
    void CheckVerButtonTest(){

        accessFromProducts("Adidas");

        WebElement button = chromewebDriver.findElement(By.cssSelector(".mt-5 > .btn-info:first-child"));
        assertEquals("Volver",button.getText());
        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();

        assertEquals("Manufacturer List | Awesome App",chromewebDriver.getTitle());

    }

    /**
     *  Checks that the edit button is displayed correctly and functions properly")
     */
    @Test
    @DisplayName("Edit button is displayed correctly and functions properly")
    void CheckEditarButtonTest(){

        accessFromProducts("Adidas");

        WebElement button = chromewebDriver.findElement(By.className("btn-success"));

        assertEquals("Editar",button.getText());

        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();
        assertEquals("Manufacturer Edition | Aswesome App",chromewebDriver.getTitle());

        assertEquals("Adidas",chromewebDriver.findElement(By.cssSelector("#name")).getAttribute("value"));
        WebElement btnguardar = chromewebDriver.findElement(By.className("btn-success"));

        js.executeScript("arguments[0].scrollIntoView();",btnguardar);
        btnguardar.click();
    }

    /**
     *  Delete button is displayed correctly
     */
    @Test
    @DisplayName("Delete button is displayed correctly")
    void zCheckBorrarButtonTest(){

        createnew("Adidas");

        List<WebElement>  erasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        int initialsize = erasebuttons.size();
        assumeTrue (initialsize > 0);

        accessFromManufacturer(erasebuttons.size()-1);

        WebElement button = chromewebDriver.findElement(By.className("btn-danger"));
        assertEquals("Borrar",button.getText());
        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();

        assertEquals("Manufacturer List | Awesome App",chromewebDriver.getTitle());
        assertTrue(initialsize > chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)")).size());
    }

    private void createnew(String manufacturer) {

        int manufacturerindex;
        if (manufacturer.contains ("Adidas") )
             manufacturerindex = 0;
        else if (manufacturer.contains ("Nike"))
            manufacturerindex = 1;
        else
            manufacturerindex = 0;
        chromewebDriver.get("http://localhost:8080/manufacturers");

        addStringData();

        chromewebDriver.findElement(By.className("btn-primary")).click();

        List<WebElement> adidasinput = chromewebDriver.findElements(By.cssSelector("input"));

        for (int count = 0 ; count < 7 ; count++ ){

            String[] splitted = outerobjdata.get(manufacturerindex).get(count).toString().split(": ");
            adidasinput.get(count).sendKeys(splitted[1]);
        }

        WebElement buttonguardar = chromewebDriver.findElement(By.cssSelector("button"));

        js.executeScript("arguments[0].scrollIntoView();", buttonguardar);

        buttonguardar.click();
    }

    /**
     *  Checks that the create button is displayed correctly and functions properly")
     */
    @Test
    @DisplayName("Crear Producto button is displayed correctly and functions properly")
    void CheckCrearButtonTest(){

        accessFromProducts("Nike");

        WebElement button = chromewebDriver.findElement(By.cssSelector(".mt-5 a:last-child"));

        assertEquals("Crear producto",button.getText());

        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();
        assertEquals("Product Edition | Aswesome App",chromewebDriver.getTitle());

        assertEquals("",chromewebDriver.findElement(By.xpath("//input[@name='name']")).getAttribute("value"));
        WebElement btnguardar = chromewebDriver.findElement(By.className("btn-info"));

        js.executeScript("arguments[0].scrollIntoView();",btnguardar);
        btnguardar.click();
    }


    /**
     * Creates a List with the manufacturer data
     * to compare with info shown in the webpage
     */
    public void addStringData() {

        outerobjdata = new ArrayList<>();
        List<String> objdata = new ArrayList<>();

        objdata.add("Nombre: Adidas");
        objdata.add("CIF: 2343235325G");
        objdata.add("Nº Empleados: 60000");
        objdata.add("Calle: 1949");
        objdata.add("Código postal: 1949");
        objdata.add("Ciudad: 1949");
        objdata.add("País: 1949");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();

        objdata.add("Nombre: Nike");
        objdata.add("CIF: 2343235325G");
        objdata.add("Nº Empleados: 60000");
        objdata.add("Calle: 1977");
        objdata.add("Código postal: 1977");
        objdata.add("Ciudad: 1977");
        objdata.add("País: 1977");
        outerobjdata.add(objdata);
    }
}
