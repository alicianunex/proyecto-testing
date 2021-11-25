package com.example.proyectotesting.controller.mvc;

import com.example.proyectotesting.controller.mvc.Pages.Driver;
import com.example.proyectotesting.controller.mvc.Steps.BorrarFabricanteSteps;
import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.ProductRepository;
import com.example.proyectotesting.service.ManufacturerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MVC Manufacturer Controller Test")
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class ManufacturerControllerTest {

    @Autowired
    MockMvc mvc;
    ProductRepository productRepository;
    ManufacturerService manufacturerService;

    @Test
    @DisplayName("returns the list of all manufacturers")
    void list() throws Exception {

        // TODO implement mock
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturerService = mock(ManufacturerService.class);
        when(manufacturerService.findAll()).thenReturn(manufacturers);

        //assumeTrue(manufacturerService.findAll().size()>0);
        mvc.perform(get("/manufacturers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("manufacturers"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().size(1))
                .andExpect(MockMvcResultMatchers.view().name("manufacturer-list"))
                .andExpect( forwardedUrl("/WEB-INF/views/manufacturer-list.jsp"));
    }

    @org.junit.jupiter.api.Nested
    @DisplayName("View method tests")
    public class View {

        @Test
        @DisplayName("Redirects when manufacturer id is null")
        void viewNullTest() throws Exception {

            // TODO Line not accessible id can't be null

            mvc.perform(get("/manufacturers/"+null+"/view"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("The manufacturer 1 is displayed correctly")
        void viewOKAdidasTest() throws Exception {

            mvc.perform(get("/manufacturers/1/view"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("manufacturer"))
                    .andExpect(MockMvcResultMatchers.view().name("manufacturer-view"))
                    .andExpect(forwardedUrl("/WEB-INF/views/manufacturer-view.jsp"));
        }

        @Test
        @DisplayName("if manufacturer id does not exists redirects")
        void viewNotPresentTest() throws Exception {

            mvc.perform(get("/manufacturers/99/view"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/manufacturers"))
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/manufacturers"));
        }

    }

    @org.junit.jupiter.api.Nested
    @DisplayName("Load form method tests")
    public class loadForm {

        @Test
        @DisplayName("Redirects when manufacturer id is null")
        void viewNullTest() throws Exception {

            // TODO Line not accessible id can't be null

            mvc.perform(get("/manufacturers/"+null+"/edit"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("The manufacturer 1 is displayed correctly")
        void viewOKAdidasTest() throws Exception {

            mvc.perform(get("/manufacturers/1/edit"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("manufacturer"))
                    .andExpect(MockMvcResultMatchers.view().name("manufacturer-edit"))
                    .andExpect(forwardedUrl("/WEB-INF/views/manufacturer-edit.jsp"));
        }

        @Test
        @DisplayName("if manufacturer id does not exists shows list")
        void viewNotPresentTest() throws Exception {

            mvc.perform(get("/manufacturers/99/edit"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(forwardedUrl("/WEB-INF/views/manufacturer-list.jsp"))
                    .andExpect(model().attribute("NOTIFICATION","No existe el fabricante solicitado."))
                    .andExpect(model().attributeExists("manufacturers"))
                    .andExpect(MockMvcResultMatchers.view().name("manufacturer-list"));
        }
    }

    @Test
    @DisplayName("show the edit page")
    void showFormTest() throws Exception {

        // TODO check mock

        productRepository = mock(ProductRepository.class);
        List<Product> products = new ArrayList<>();
        when(productRepository.findAllByManufacturerIdIsNull()).thenReturn(products);

        mvc.perform(get("/manufacturers/new"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/views/manufacturer-edit.jsp"))
                .andExpect(model().attributeExists("manufacturer"))
                .andExpect(model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("manufacturer-edit"));
        //verify(productRepository).findAllByManufacturerIdIsNull();

    }

    @Test
    @DisplayName("Saves the product and return to list")
   //@Disabled("Null id error!!")
    void saveTest() throws Exception {
        /*
         .param("cif", "667")
                .param("numEmployees", "655")
                .param("year", "year"))*/

        mvc.perform(post("/manufacturers"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manufacturers"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/manufacturers"));
    }

    @Test
    @DisplayName("Deletes the product and return to list")
    void deleteTest() throws Exception {
        mvc.perform(get("/manufacturers/99/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manufacturers"))
                .andExpect(view().name("redirect:/manufacturers"));
    }
/*
    @Test
    @DisplayName("Delete all the products and return to list")
    @Disabled("Cannot run in suite")
    void deleteAllTest() throws Exception {
        mvc.perform(get("/manufacturers/delete/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manufacturers"))
                .andExpect(view().name("redirect:/manufacturers"));

    }


    @Test
    @DisplayName("Delete all the products and return to list")
    void deleteAllTestThrows() throws Exception {

        manufacturerService = mock(ManufacturerService.class);
        when(manufacturerService.deleteAll()).thenReturn(true);

        mvc.perform(get("/manufacturers/delete/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manufacturers"))
                .andExpect(view().name("redirect:/manufacturers"));
    }

 */
}
