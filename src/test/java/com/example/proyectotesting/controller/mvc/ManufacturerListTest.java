package com.example.proyectotesting.controller.mvc;

import com.example.proyectotesting.ProyectoTestingApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Manufacturer List Test")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public class ManufacturerListTest {

    // http://localhost:8080/manufacturers


    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {

//        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--headless");
//        chromewebDriver = new ChromeDriver(options);

        String dir = System.getProperty("user.dir");
        String driverUrl = "C:\\data\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",driverUrl);
        chromewebDriver = new ChromeDriver();
        chromewebDriver.get("http://localhost:8080/manufacturers");
        chromewebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

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
     *     @DisplayName("Displays the List of products with attributes")
     */
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

    /**
     *     @DisplayName("Title is displayed and stored correctly")
     */
    @Test
    @DisplayName("Title is displayed and stored correctly")
    void CheckTitletextTest(){

        new WebDriverWait(chromewebDriver, 4)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

        assertEquals("Listado de fabricantes", chromewebDriver.
                findElement(By.cssSelector("h1")).getText());
        assertEquals("Manufacturer List | Awesome App", chromewebDriver.getTitle());
    }

    /**
     *     @DisplayName("Details button is displayed correctly")
     */
    @Test
    @DisplayName("Details button is displayed correctly")
    void CheckVerButtonTest(){

        List<WebElement>  verbutton = chromewebDriver.findElements(By.cssSelector("td:last-child a:first-child"));
        for (int count = 0; count < verbutton.size(); count++) {
            List<WebElement>  innerverbuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:first-child"));

            assertTrue(innerverbuttons.get(count).getAttribute("href").contains("/manufacturers/")
                    && innerverbuttons.get(count).getAttribute("href").contains("/view"));
            innerverbuttons.get(count).click();
            assertTrue(chromewebDriver.getCurrentUrl().contains("/manufacturers/") && chromewebDriver.getCurrentUrl().contains("/view"));

            WebElement buttonver = chromewebDriver.findElements(By.className("btn-info")).get(0);
            js.executeScript("arguments[0].scrollIntoView();", buttonver);
            buttonver.click();
        }
    }

    /**
     *     @DisplayName("Edit button is displayed correctly")
     */
    @Test
    @DisplayName("Edit button is displayed correctly")
    void CheckEditarButtonTest(){

        List<WebElement>  editbuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(2)"));
        for (int count = 0; count < editbuttons.size(); count++) {
            List<WebElement>  innereditbuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(2)"));

            assertTrue(innereditbuttons.get(count).getAttribute("href").contains("/manufacturers/")
                    && innereditbuttons.get(count).getAttribute("href").contains("/edit"));

            js.executeScript("arguments[0].scrollIntoView();", innereditbuttons.get(count));
            innereditbuttons.get(count).click();
            assertTrue(chromewebDriver.getCurrentUrl().contains("/manufacturers/")
                    && chromewebDriver.getCurrentUrl().contains("/edit"));

            WebElement buttonsave = chromewebDriver.findElement(By.xpath("//button[@type='submit']"));
            js.executeScript("arguments[0].scrollIntoView();", buttonsave);
            buttonsave.click();
        }
    }

    /**
     * @DisplayName("Checks the add manufacturer button")
     */
    @Test
    @DisplayName("Checks the add manufacturer button")
    void addNewManufacturer(){
        assertTrue(false);

    }

    /**
     * @DisplayName("Checks the add manufacturer button")
     */
    @Test
    @DisplayName("Checks the remove all manufacturers button")
    void removeAllManufacturers(){
        assertTrue(false);

    }


    /**
     *     @DisplayName("Product links are displayed correctly")
     */
    @Test
    @DisplayName("Product links are displayed correctly")
    void CheckManufacturerlinksTest(){

        List<WebElement>  tableanchor = chromewebDriver.findElements(By.cssSelector("td:nth-child(7) a"));
        for (WebElement count: tableanchor) {
                //System.out.println(count.getAttribute("href"));
            assertTrue(count.getAttribute("href").contains("/products/")
                        && count.getAttribute("href").contains("/view"));

            if (count.getText().equalsIgnoreCase("Balón"))
                assertTrue(count.getAttribute("href").contains("/products/9/view"));
            else if (count.getText().equalsIgnoreCase("Mesa"))
                assertTrue(count.getAttribute("href").contains("/products/10/view"));
            else if (count.getText().equalsIgnoreCase("Botella"))
                assertTrue(count.getAttribute("href").contains("/products/11/view"));
            else if (count.getText().equalsIgnoreCase("WebCam"))
                assertTrue(count.getAttribute("href").contains("/products/12/view"));
            else {
                System.out.println("Product not recognized");
                assumeTrue(false);
            }
        }
    }
    /**
     *  @DisplayName("Delete button is displayed correctly")
     */
    @Test
    @DisplayName("zDelete button is displayed correctly")
    void zCheckBorrarButtonTest(){

        List<WebElement>  erasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        int initialsize = erasebuttons.size();

        System.out.println(initialsize);
        while (erasebuttons.size()>0) {

            assertTrue(erasebuttons.get(0).getAttribute("href").contains("/manufacturers/")
                    && erasebuttons.get(0).getAttribute("href").contains("/delete"));
            erasebuttons.get(0).click();

            System.out.println(chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)")).size());
            assertTrue(initialsize > chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)")).size());

        }
    }

    /**
     * Creates a List with the manufacturer data
     * to compare with info shown in the webpage
     */
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
