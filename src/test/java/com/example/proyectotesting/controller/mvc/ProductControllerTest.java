package com.example.proyectotesting.controller.mvc;

import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.CategoryRepository;
import com.example.proyectotesting.repository.ManufacturerRepository;
import com.example.proyectotesting.repository.ProductRepository;
import com.example.proyectotesting.service.ManufacturerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MVC Product Controller Test")
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductRepository repository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Returns the list of products")
    void obtenerListaTest() throws Exception {

        // TODO access method !!

        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    void obtenerFormularioTest() throws Exception {

        mvc.perform(get("/products/new"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/views/product-edit.jsp"))
                .andExpect(model().attributeExists("manufacturers"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.view().name("product-edit"));
    }

    @Test
    @DisplayName("Saves the product and return to list")
    void crearProductoTest() throws Exception {

        mvc.perform(post("/products"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @org.junit.jupiter.api.Nested
    @DisplayName("Ver Producto method tests")
    public class verProducto {

        @Test
        @DisplayName("Redirects when product id is null")
        void verNullTest() throws Exception {

            // TODO Line not accessible id can't be null

            mvc.perform(get("/products/"+null+"/view"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("The product 9 is displayed correctly")
        void verOK1Test() throws Exception {

            mvc.perform(get("/products/10/view"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("product"))
                    .andExpect(MockMvcResultMatchers.view().name("product-view"))
                    .andExpect(forwardedUrl("/WEB-INF/views/product-view.jsp"));
        }

        @Test
        @DisplayName("if product id does not exists redirects")
        void verNotPresentTest() throws Exception {

            mvc.perform(get("/products/99/view"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/products"));
        }
    }

    @org.junit.jupiter.api.Nested
    @DisplayName(" Editar producto method tests")
    public class editarProducto {

        @Test
        @DisplayName("Redirects when product id is null")
        void editarNullTest() throws Exception {

            // TODO Line not accessible id can't be null

            mvc.perform(get("/products/" + null + "/edit"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("The product 10 is displayed correctly")
        void editarOK1Test() throws Exception {

            mvc.perform(get("/products/10/edit"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("product"))
                    .andExpect(MockMvcResultMatchers.view().name("product-edit"))
                    .andExpect(forwardedUrl("/WEB-INF/views/product-edit.jsp"));
        }

        @Test
        @DisplayName("if product id does not exists shows list")
        void editarNotPresentTest() throws Exception {
            mvc.perform(get("/products/99/edit"))
                    .andExpect(status().is3xxRedirection())
                    //.andExpect(model().attributeExists("error"))
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/products"));
        }
    }

    @Test
    @DisplayName("Deletes the product and return to list")
    void borrarProductoTest() throws Exception {

        Manufacturer manufacturer = new Manufacturer("", "", 23, 32);
        manufacturerRepository.save(manufacturer);
        Product product = new Product("", "", 423, 2312D, manufacturer);
        repository.save(product);

        try {
            mvc.perform(get("/products/9/delete"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/products"))
                    .andExpect(view().name("redirect:/products"));
        } catch (Exception error) {
            System.out.println("No products in DB!!");
            error.printStackTrace();
            assertTrue(false);
        }
    }
/*
    @Test
    @Disabled("Cannot run in suite")
    @DisplayName("Delete all the products and return to list")
    void borrarProductosTest() throws Exception {
        mvc.perform(get("/products/delete/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(view().name("redirect:/products"));
    }

 */

    /*
    @Test
    @DisplayName("Delete all the products and return to list")
    void deleteAllTestThrows() throws Exception {

//        ProductRepository productRepository= mock(ProductRepository.class);
//        doNothing().when(productRepository).deleteAll();

        mvc.perform(get("/products/delete/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(view().name("redirect:/products"));
//        verify(productRepository).deleteAll();
    }

     */

    @Test
    @DisplayName("Creates a new product with a linked manufacturer")
    void formWithManufacturerTest() throws Exception {

        mvc.perform(get("/products/new/manufacturer/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("manufacturer"))
                .andExpect(view().name("product-edit-withmanufacturer"));
    }
}