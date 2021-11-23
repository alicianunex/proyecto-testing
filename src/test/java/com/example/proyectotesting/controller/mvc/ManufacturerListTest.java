package com.example.proyectotesting.controller.mvc;

import com.example.proyectotesting.controller.mvc.Pages.*;
import com.example.proyectotesting.controller.mvc.Steps.BorrarFabricanteSteps;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Selenium Manufacturer List Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ManufacturerListTest {

    // http://localhost:8080/manufacturers
//   https://proyecto-testinggrupo2.herokuapp.com

    static WebDriver firefoxwebDriver;
    static WebDriver chromewebDriver;

    List<List> outerobjdata;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {

        // TODO Phantom broswer for GitHub actions, not working throws CONNECTION ERROR

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

        chromewebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



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
     *    Displays the List of products with attributes
     */
    @Test
    @DisplayName("Displays the List of products with attributes")
    void CheckDataTest(){

        addStringData();

        List<WebElement>  table = chromewebDriver.findElements(By.cssSelector("tbody tr"));
        List<WebElement> columns;

        for (int row = 0; row < table.size()-1; row++) {
                columns = chromewebDriver.findElements(By.xpath("//tbody/tr["+(row)+"]/td"));
            for (int column = 0; column < columns.size(); column++){
//                System.out.println(outerobjdata.get(row).get(column) + " __ " + columns.get(column).getText());
                    assertEquals(outerobjdata.get(row).get(column),columns.get(column).getText());
            }
        }
    }

    /**
     *Title is displayed and stored correctly
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
     * Details button is displayed correctly
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
     * Edit button is displayed correctly
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
     * Checks the add manufacturer button
     */
    @Test
    @DisplayName("Checks the add manufacturer button")
    void addNewManufacturer(){

        chromewebDriver.findElement(By.cssSelector("p>a:first-child")).click();
        assertTrue(chromewebDriver.getCurrentUrl().contains("/manufacturers/new"));

        WebElement buttonsave = chromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", buttonsave);
        buttonsave.click();

        assertTrue(chromewebDriver.getCurrentUrl().contains("/manufacturers"));

        // erase new manufacturer
        List<WebElement>  erasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        erasebuttons.get(erasebuttons.size()-1).click();
    }

    /**
     * Checks the remove all manufacturers button
     * Disabled to prevent other tests from failing
     */
    @Test
    @Disabled("Cannot run in suite until recreate is finished")
    @DisplayName("Checks the remove all manufacturers button")
    void removeAllManufacturers(){


        int initialsize = chromewebDriver.findElements(By.cssSelector("tbody tr")).size();

        chromewebDriver.findElement(By.xpath("//p/a[@class='btn btn-danger']")).click();

        assertTrue(initialsize > chromewebDriver.findElements(By.cssSelector("tbody tr")).size());
        assertEquals(1, chromewebDriver.findElements(By.cssSelector("tbody tr")).size());

      // Recreate manufacturers with Selenium

        BorrarFabricanteSteps borrarFabricanteSteps = new BorrarFabricanteSteps();
        borrarFabricanteSteps.paginaDeInicio();
        borrarFabricanteSteps.seVuelvenAGenerarLosFabricantes();

        chromewebDriver.get("http://localhost:8080/manufacturers");
        assertTrue(chromewebDriver.findElements(By.xpath("//tr")).size() == 3);
    }

    /**
     * Product links are displayed correctly
     */
    @Test
    @DisplayName("Product links are displayed correctly")
    void CheckManufacturerlinksTest(){

        List<WebElement>  tableanchor = chromewebDriver.findElements(By.cssSelector("td:nth-child(7) a"));
        for (int count=0; count < tableanchor.size()-1; count++) {
                //System.out.println(count.getAttribute("href"));
            List<WebElement>  innertableanchor = chromewebDriver.findElements(By.cssSelector("td:nth-child(7) a"));
            assertTrue(innertableanchor.get(count).getAttribute("href").contains("/products/")
                        && innertableanchor.get(count).getAttribute("href").contains("/view"));

            if (innertableanchor.get(count).getText().equalsIgnoreCase("Balón"))
                assertTrue(innertableanchor.get(count).getAttribute("href").contains("/products/9/view"));

            else if (innertableanchor.get(count).getText().equalsIgnoreCase("Mesa"))
                assertTrue(innertableanchor.get(count).getAttribute("href").contains("/products/10/view"));
            else if (innertableanchor.get(count).getText().equalsIgnoreCase("Botella"))
                assertTrue(innertableanchor.get(count).getAttribute("href").contains("/products/11/view"));
            else if (innertableanchor.get(count).getText().equalsIgnoreCase("WebCam"))
                assertTrue(innertableanchor.get(count).getAttribute("href").contains("/products/12/view"));
            else {
                System.out.println("Product not recognized");
                assumeTrue(false);
            }
            /*
            innertableanchor.get(count).click();
            assertTrue(chromewebDriver.getCurrentUrl().contains("/view"));

//          chromewebDriver.back();
            js.executeScript("window.history.go(-1)");

             */
        }
    }

    /**
     *  Delete button is displayed correctly
     */
    @Test
    @DisplayName("Delete button is displayed correctly")
    void zCheckBorrarButtonTest() throws Exception{

        createnew("Adidas");
        createnew("Nike");

//        new WebDriverWait(chromewebDriver, 10)
//                .until(ExpectedConditions.visibilityOfAllElements());
        List<WebElement>  erasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));

        int initialsize = erasebuttons.size();
        assumeTrue(erasebuttons.size()>1);

        for (int i=0; i<2; i++) {

            List<WebElement> innererasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));

            assertTrue(innererasebuttons.get(innererasebuttons.size()-1).getAttribute("href").contains("/manufacturers/")
                    && innererasebuttons.get(innererasebuttons.size()-1).getAttribute("href").contains("/delete"));
            innererasebuttons.get(innererasebuttons.size()-1).click();

            erasebuttons = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));

            assertTrue(initialsize > erasebuttons.size());
        }
    }

    private void createnew(String manufacturer) {

        int manufacturerindex;
        if (manufacturer.contains ("Adidas") )
            manufacturerindex = 0;
        else if (manufacturer.contains ("Nike"))
            manufacturerindex = 1;
        else
            manufacturerindex = 99;

        chromewebDriver.get("http://localhost:8080/manufacturers");
        //chromewebDriver.get("https://dashboard.heroku.com/apps/proyecto-testinggrupo2/deploy/github");

        addStringData();

        chromewebDriver.findElement(By.className("btn-primary")).click();

        List<WebElement> input = chromewebDriver.findElements(By.xpath("//input[not(@type='hidden')]"));

        for (int count = 0 ; count < input.size()-1 ; count++ ){
            input.get(count).sendKeys(outerobjdata.get(manufacturerindex).get(count).toString());
        }

        List<WebElement> options = chromewebDriver.findElements(By.xpath("//option"));


        // TODO Create products in catch !!
        try {
            js.executeScript("arguments[0].scrollIntoView();", options.get(3));

            Actions action = new Actions(chromewebDriver);

            if (manufacturerindex == 1) {
                action.keyDown(Keys.CONTROL);
                options.get(0).click();
                options.get(1).click();
                options.get(2).click();
                action.perform();
            } else {
                action.keyDown(Keys.CONTROL);
                options.get(3).click();
                action.perform();
            }
            action.keyUp(Keys.CONTROL).perform();
        }catch(IndexOutOfBoundsException error){error.printStackTrace();}

        WebElement buttonguardar = chromewebDriver.findElement(By.cssSelector("button"));

        js.executeScript("arguments[0].scrollIntoView();", buttonguardar);

        buttonguardar.click();
    }

    /**
     * Creates a List with the manufacturer data
     * to compare with info shown in the webpage
     */
    public void addStringData() {

        outerobjdata = new ArrayList<>();
        List<String> objdata = new ArrayList<>();

        objdata.add("Adidas");
        objdata.add("2343235325G");
        objdata.add("60000");
        objdata.add("1949");
        objdata.add("Calle falsa");
        objdata.add("Spain");
        objdata.add("Balón Mesa Botella");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();

        objdata.add("Nike");
        objdata.add("2343235325G");
        objdata.add("60000");
        objdata.add("1977");
        objdata.add("Calle verdadera");
        objdata.add("Spain");
        objdata.add("Webcam");
        outerobjdata.add(objdata);
    }
}
