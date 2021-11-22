package com.example.proyectotesting.controller.mvc.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class IndexManufacturerPage {

      List<List> outerobjdata;
      Driver driver = new Driver();


    public IndexManufacturerPage() {
    }

    public void getManufacturerIndex(){

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver.ChromewebDriver = new ChromeDriver(options);

//        Path path = Paths.get("C:\\data\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver",path.toString());
//        driver.ChromewebDriver = new ChromeDriver();

//        driver.ChromewebDriver.get("https://proyecto-testinggrupo2.herokuapp.com/manufacturers");
        driver.ChromewebDriver.get("http://localhost:8080/manufacturers");
        driver.js = (JavascriptExecutor) driver.ChromewebDriver;
    }

    public void clickonNuevoManufacturer(){

        driver.ChromewebDriver.findElement(By.cssSelector("p>a:first-child")).click();
    }
    
    public void clickOnVerManufacturer(){

        List<WebElement>  verbuttons = driver.ChromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(1)"));
//        js.executeScript("arguments[0].scrollIntoView();", verbuttons);
        verbuttons.get(verbuttons.size()-1).click();
    }

    public void checkNewManufacturer(String fabricante) {

        List<WebElement> newmanrow = driver.ChromewebDriver.findElements(By.cssSelector("tr:last-child td"));

        for (int count=0; count < newmanrow.size() ;count++){
            assertTrue(newmanrow.get(count).getText().contains(fabricante.split(" ")[count]));
        }
    }

    public void eraseNewManufacturerFromList() {

        List<WebElement>  erasebuttons = driver.ChromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        erasebuttons.get(erasebuttons.size()-1).click();
    }

    public void checkErasedManufacturer() {
        List<WebElement> erasebuttons = driver.ChromewebDriver.findElements(By.cssSelector("td:last-child a:nth-child(3)"));
        assertEquals(2,erasebuttons.size());
    }

    public void clickDeleteAllManufacturers() {

        driver.ChromewebDriver.findElement(By.xpath("//p/a[@class='btn btn-danger']")).click();
    }

    public void checkEmptyTable() {

        assertTrue(driver.ChromewebDriver.findElements(By.xpath("//tr")).size()<2);
    }

    public void checkInitialManufacturers() {

        addStringData();

        List<WebElement> table = driver.ChromewebDriver.findElements(By.cssSelector("tbody tr"));
        List<WebElement> columns;

        for (int row = 0; row < table.size(); row++) {
            if (row == 0)
                columns = table.get(row).findElements(By.xpath("//th"));
            else
                columns = driver.ChromewebDriver.findElements(By.xpath("//tbody/tr["+(row+1)+"]/td"));
            for (int column = 0; column < columns.size(); column++){
//                System.out.println(outerobjdata.get(row).get(column) + " __ " + columns.get(column).getText());
                assertEquals(outerobjdata.get(row).get(column),columns.get(column).getText());
            }
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
