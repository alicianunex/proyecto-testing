package com.example.proyectotesting.controller.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MVC Home Controller Test")
public class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Home redirects to /products")
    void redirectTests() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        HomeController homeController = new HomeController();
        assertTrue(homeController.redirectToProducts().contains("redirect:/products"));

    }

}

