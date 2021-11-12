
package com.example.proyectotesting.controller.rest;
import com.example.proyectotesting.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRestControllerTest {

    private static final String Category_URL = "/api/category";
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    @Test
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }


    @Nested
    public class findTests {
        @Test
        void findAll() {
            createDataCategories();
            createDataCategories();


            ResponseEntity<Category[]> respuesta = testRestTemplate.getForEntity(Category_URL, Category[].class);

            assertEquals(200, respuesta.getStatusCodeValue());
            assertEquals(HttpStatus.OK, respuesta.getStatusCode());
            assertTrue(respuesta.hasBody());
            assertNotNull(respuesta.getBody());

            List<Category> categories = List.of(respuesta.getBody());

            assertNotNull(categories);
            assertTrue(categories.size() >= 2);


        }

        @Test
        void findOneCategory() {
            Category category = createDataCategories();

            ResponseEntity<Category> respuesta =
                    testRestTemplate.getForEntity(Category_URL + "/" + category.getId(), Category.class);

            assertEquals(200, respuesta.getStatusCodeValue());
            assertEquals(HttpStatus.OK, respuesta.getStatusCode());
            assertTrue(respuesta.hasBody());

            Category responseCategory = respuesta.getBody();

            assertNotNull(responseCategory);
            assertNotNull(responseCategory.getId());
            assertEquals(category.getId(), responseCategory.getId());

        }


        @Test
        void findOneNotFound() {
            ResponseEntity<Category> responseNotFound =
                    testRestTemplate.getForEntity(Category_URL + "/777", Category.class);

            assertEquals(404, responseNotFound.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
            assertFalse(responseNotFound.hasBody());
        }


    }


    @Nested
    class deletedTest{
        @Test
        void deleteByIdSuccess() {
            Category category = createDataCategories();
            String archive = Category_URL + "/" + category.getId();
            ResponseEntity<Category> response = testRestTemplate.getForEntity(archive, Category.class);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(category.getId(), response.getBody().getId());

            testRestTemplate.delete(archive);

            ResponseEntity<Category> response2 = testRestTemplate.getForEntity(archive, Category.class);

            assertEquals(404, response2.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
            assertFalse(response2.hasBody());

        }

        @Test
        void deleteNullIdTest() {
            Category category = createDataCategories();

            String archive = Category_URL + "/800" + category.getId();

            ResponseEntity<Category> response = testRestTemplate.getForEntity(archive, Category.class);

            assertEquals(404, response.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

            ResponseEntity<Category> response2 = testRestTemplate.getForEntity(archive, Category.class);
            testRestTemplate.delete(archive);

            assertFalse(archive.isEmpty());


        }

        @Test
        void deleteAllSuccess() {
            createDataCategories();
            createDataCategories();

            ResponseEntity<Category[]> response = testRestTemplate.getForEntity(Category_URL, Category[].class);

            assertNotNull(response.getBody());

            List<Category> categories = List.of(response.getBody());

            assertTrue(categories.size() >= 2);

            testRestTemplate.delete(Category_URL);
            response = testRestTemplate.getForEntity(Category_URL, Category[].class);

            assertNotNull(response.getBody());

            categories = List.of(response.getBody());
            assertEquals(0, categories.size());
        }

        @Nested
        class updateTest{



            @Test
            void updateNull() {
                Category product = createDataCategories();
                String json = String.format("""
                {
                    "id": null,
                    "name": "Update null test",
                    "color": "nuevo1"
                    
                }
                """, product.getId());
                System.out.println(json);
                ResponseEntity<Category> response =
                        testRestTemplate.exchange(Category_URL, HttpMethod.PUT, crearHttpRequest(json), Category.class);
                assertEquals(405, response.getStatusCodeValue());
                assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
            }


            @Test
            void updateIdNotAllowed() {
                Category product = createDataCategories();
                String json = String.format("""
                {
                    "id": 2,
                    "name": "Not allowed",
                    "color": "nuevo2"
                   
                }
                """, product.getId());
                System.out.println(json);
                ResponseEntity<Category> response =
                        testRestTemplate.exchange(Category_URL, HttpMethod.PUT, crearHttpRequest(json), Category.class);
                assertEquals(405, response.getStatusCodeValue());
                assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
            }



        }
    }

    @Nested
    class createsTest {
        @Test
        void createSuccess() {
            String json = """
                    {
                        "name": "Rest Success Test",
                        "color": "Negro"
                    }
                    """;
            ResponseEntity<Category> respuesta = testRestTemplate.postForEntity(Category_URL, crearHttpRequest(json), Category.class);

            assertEquals(200, respuesta.getStatusCodeValue());
            assertEquals(HttpStatus.OK, respuesta.getStatusCode());
            assertTrue(respuesta.hasBody());

            Category category = respuesta.getBody();

            assertNotNull(category);
            assertEquals("Rest Success Test", category.getName());


        }


    }


    private Category createDataCategories() {
        String json = """
                {
                    "name": "Crear Data",
                    "color": "Negro"
                              
                }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Category> response =
                testRestTemplate.postForEntity(Category_URL, request, Category.class);
        return response.getBody();
    }

    private HttpEntity<String> crearHttpRequest(String json) {
        HttpHeaders cabeceras = new HttpHeaders();
        cabeceras.setContentType(MediaType.APPLICATION_JSON);
        cabeceras.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(json, cabeceras);
        return request;
    }
}