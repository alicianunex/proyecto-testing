package com.example.proyectotesting.controller.mvc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Selenium Manufacturer Edit Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ManufacturerEditTest {

    // http://localhost:8080/manufacturers
//    https://proyecto-testinggrupo2.herokuapp.com


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
        chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers");

        // Chrome setup

//        String dir = System.getProperty("user.dir");
//
////        String driverUrl = "C:\\data\\chromedriver.exe";
////        System.setProperty("webdriver.chrome.driver",driverUrl);
//        Path path = Paths.get("C:\\data\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver",path.toString());
//        chromewebDriver = new ChromeDriver();
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
    void CheckTitleAdidasTextTest() {

        accessFromProducts("Adidas");

        assertEquals("Fabricante 1", chromewebDriver.
                findElement(By.cssSelector("h2")).getText());
        assertEquals("Manufacturer Edition | Aswesome App", chromewebDriver.getTitle());

        exit();
    }

    /**
     * Clicks on the Guardar button of the /edit HTML
     */
    private void exit() {

        WebElement button = chromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", button);
        Actions actions = new Actions(chromewebDriver);
        actions.click(button).perform();

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
        assertEquals("Manufacturer Edition | Aswesome App", chromewebDriver.getTitle());

        exit();
    }

    /**
     * Subtitles are displayed correctly
     */
    @Test
    @DisplayName("Subtitles are displayed correctly")
    void CheckSubtitleAdidasTextTest(){

        accessFromProducts("Adidas");

        assertEquals("Fabricante 1", chromewebDriver.
                findElement(By.cssSelector("h2")).getText());
        assertEquals("Manufacturer Edition | Aswesome App", chromewebDriver.getTitle());

        exit();
    }

    /**
     * Subtitles are displayed correctly
     */
    @Test
    @DisplayName("Subtitles are displayed correctly")
    void CheckSubtitleNikeTextTest(){

        accessFromManufacturer(1);

        List<WebElement> titles = chromewebDriver.findElements(By.cssSelector("h3"));
        assertEquals("Datos de fabricante",titles.get(0).getText());
        assertEquals("Datos de dirección",titles.get(1).getText());

        exit();
    }

    /**
     * Checks that the header and the info is displayed correctly
     */
    @Test
    @DisplayName("Checks that the header and the info of Adidas is displayed correctly")
    void CheckDataAdidasTest(){

        addStringData();
        accessFromProducts("Adidas");

        List<WebElement> string_data = chromewebDriver.findElements(By.cssSelector("input"));

        for (int count = string_data.size()-1; count > -1; count-- ){
            assertEquals(outerobjdata.get(0).get(count),string_data.get(count).getAttribute("value"));
        }

        assertEquals(4,chromewebDriver.findElements(By.xpath("//option")).size());

        assertEquals(3,chromewebDriver.findElements(By.xpath("//option[@selected='selected']")).size());

        exit();
    }

    /**
     * Checks that the header and the info of Nike is displayed correctly")
     */
    @Test
    @DisplayName("Checks that data for Nike is displayed correctly")
    void CheckDataNikeTest(){

        addStringData();
        accessFromProducts("Nike");

        List<WebElement> string_data = chromewebDriver.findElements(By.cssSelector("input"));

        for (int count = string_data.size()-1; count > -1; count-- ){
            assertEquals(outerobjdata.get(1).get(count),string_data.get(count).getAttribute("value"));
        }

        assertEquals(2,chromewebDriver.findElements(By.xpath("//option")).size());

        assertEquals(1,chromewebDriver.findElements(By.xpath("//option[@selected='selected']")).size());

        exit();
    }

    /**
     *   Enters manufacturer/view from button in /manufacturers
     * @param index of the manufacturer to access linked page
     * */
    private void accessFromManufacturer(int index){

        List<WebElement>  editarbutton = chromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(2)"));

//        js.executeScript("arguments[0].scrollIntoView();", verbutton);
        editarbutton.get(index).click();

    }

    /**
     *   Enters manufacturer/view from button in /manufacturers
     * @param name of the manufacturer to access linked page
     * */
    private void accessFromProducts(String name) {

        chromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/products");

        if (name.contains("Adidas")) {
            WebElement webElement = chromewebDriver.findElement(By.cssSelector("tr:nth-child(2) td:nth-child(5) a"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        } else if (name.contains("Nike")) {
            WebElement webElement = chromewebDriver.findElement(By.cssSelector("tr:nth-child(5) td:nth-child(5) a"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);
            webElement.click();
        } else
            System.out.println("Manufacturer not recognized");

        WebElement button = chromewebDriver.findElement(By.className("btn-success"));
        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();

    }

    @Test
    @DisplayName(" Asserts that the option menu functions properly")
    void checkAdidasOptionMenu() {

        accessFromProducts("Adidas");

        assertEquals(3,chromewebDriver.findElements(By.xpath("//option[@selected='selected']")).size());

        Actions action = new Actions(chromewebDriver);

        adidasSelectZapatillas(action);

        saveEditManufacturer();
        action.keyUp(Keys.CONTROL).perform();
        changeToNewTab();


        List<WebElement> products = chromewebDriver.findElements(By.cssSelector("tr:nth-child(2) td span"));
        assertEquals(1,products.size());

        // reset products
        accessFromManufacturer(0);
        adidasSelectZapatillas(action);
        saveEditManufacturer();
        action.keyUp(Keys.CONTROL).perform();
        changeToNewTab();

    }

    /**
     * Changes to a new tab opened before the calls
     */
    private void changeToNewTab() {
        ArrayList<String> tabs = new ArrayList<>(chromewebDriver.getWindowHandles());
        String handleName = tabs.get(1);
        chromewebDriver.switchTo().window(handleName);
        System.setProperty("current.window.handle", handleName);
    }

    /**
     * Once inside the /edit page
     * saves the changes scrolling and clicking on the Guardar btn
     */
    private void saveEditManufacturer() {
        WebElement button = chromewebDriver.findElement(By.xpath("//button[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", button);
        Actions actions = new Actions(chromewebDriver);
        actions.click(button).perform();
    }

    /**
     *  If called an odd number of times
     *      Selects only zapatillas in the adidas Manufacturer
     * OR if called an even number of times
     *      Reassigns the original products to Adidas
     * @param action
     */
    private void adidasSelectZapatillas(Actions action){

        List<WebElement> options = chromewebDriver.findElements(By.xpath("//option"));
        js.executeScript("arguments[0].scrollIntoView();", options.get(3));

        action.keyDown(Keys.CONTROL);
        options.get(0).click();
        options.get(1).click();
        options.get(2).click();
        options.get(3).click();
        action.perform();
    }

    /**
     *  Ver button is displayed correctly
     */
    @Test
    @DisplayName("Guardar Button is functioning properly")
    void CheckGuardarButtonTest(){

        accessFromProducts("Adidas");
        exit();
        assertEquals("Manufacturer List | Awesome App", chromewebDriver.getTitle());
    }

    @Test
    @DisplayName("Creates a new manufacturer from scratch")
    void CreateNewManufacturer(){

        chromewebDriver.findElement(By.xpath("//a[contains(@href,'new')]")).click();
        assertEquals("https://proyecto-testinggrupo2.herokuapp.com/manufacturers/new", chromewebDriver.getCurrentUrl());

        // Load obj data
        List<String>datamanufacturer = new ArrayList<>();
        datamanufacturer.add("Examplename");
        datamanufacturer.add("1234");
        datamanufacturer.add("001");
        datamanufacturer.add("72");
        datamanufacturer.add("streetex");
        datamanufacturer.add("cityL");
        datamanufacturer.add("France");

        // fills the inputs
        List<WebElement> inputs =chromewebDriver.findElements(By.xpath("//input[@type='text']"));

        for (int count=0; count>inputs.size() ;count++){
            inputs.get(count).sendKeys(datamanufacturer.get(count));
        }

        exit(); //saves

        //checks the row's data
        List<WebElement> newmanrow =chromewebDriver.findElements(By.cssSelector("tr:last-child td"));

        for (int count=0; count>newmanrow.size() ;count++){
            assertTrue(newmanrow.get(count).getText().contains(datamanufacturer.get(count)));
        }

        //Erase the registry
        chromewebDriver.findElement(By.cssSelector("tr:last-child td:last-child a:last-child")).click();

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
        objdata.add("33010");
        objdata.add("León");
        objdata.add("Spain");
        objdata.add("1");
        objdata.add("1");
        outerobjdata.add(objdata);

        objdata = new ArrayList<>();

        objdata.add("Nike");
        objdata.add("2343235325G");
        objdata.add("60000");
        objdata.add("1977");
        objdata.add("Calle verdadera");
        objdata.add("11322");
        objdata.add("Madrid");
        objdata.add("Spain");
        objdata.add("1");
        objdata.add("3");
        outerobjdata.add(objdata);
    }
}
