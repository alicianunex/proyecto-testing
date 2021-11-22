package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Category;
import com.example.proyectotesting.repository.CategoryRepository;
import com.example.proyectotesting.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryRestControllerTest {

    private CategoryService categoryService;

    private CategoryRestController categoryRestController;
    private TestRestTemplate restController;
    private static final String URL = "/api/category";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restController = new TestRestTemplate(restTemplateBuilder);


        this.categoryService = mock(CategoryService.class);
        this.categoryRestController = new CategoryRestController(categoryService);
    }


    private Category createDemoCategory(){
        String json = """
                        {
                            "name": "name2",
                            "description": "blablablá",
                            "quantity": 4,
                            "price": 0.99
                        }
                        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Category> response =
                restController.postForEntity(URL, request, Category.class);
        return response.getBody();
    }

    private HttpEntity<String> createHttpRequest(Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = String.format("""
                        {
                            "id": %d,
                            "name": "name2",
                            "description": "blablablá",
                            "quantity": 4,
                            "price": 0.99
                        }
                        """,id);
        return new HttpEntity<>(json, headers);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Find {

        @Test
        @Order(2)
        void findAll() {


            ResponseEntity<Category[]> response = restController.getForEntity(URL, Category[].class);
            assertAll(
                    () ->assertEquals(200, response.getStatusCodeValue()),
                    () ->assertEquals(HttpStatus.OK, response.getStatusCode()),
                    () ->assertEquals(HttpStatus.valueOf(200), response.getStatusCode()),
                    () ->assertTrue(response.hasBody()),
                    () ->assertNotNull(response.getBody()));

            List<Category> Categorys = List.of(response.getBody());
            assertNotNull(Categorys);

            assertTrue(Categorys.size() >= 2);
        }

        @Test
        void findOneReturn200() {

            Category category = createDemoCategory();
            categoryService.save(category);
            ResponseEntity<Category> response = restController.getForEntity
                    (URL + "/" + category.getId(), Category.class);

            assertAll(
                    () -> assertTrue(response.hasBody()),
                    () -> assertNotNull(response.getBody()),
                    () -> assertNotNull(response.getBody().getId()),
                    () -> assertEquals(200, response.getStatusCodeValue()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode()));
        }

        @Test
        void findOneEmptyTest() {

            ResponseEntity<Category> response = restController.getForEntity(URL + "/1", Category.class);

            assertAll(
                    () -> assertNull(response.getBody()),
                    () -> assertFalse(response.hasBody()),
                    () -> assertEquals(404, response.getStatusCodeValue()),
                    () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                    () -> assertFalse(response.hasBody()));
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Create {

        @Test
        void create200() {

            ResponseEntity<Category> response =
                    restController.postForEntity(URL, createHttpRequest(null), Category.class);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());

            Category category = response.getBody();
            assertNotNull(category);
            assertEquals("name2", category.getName());
        }

        @Test
        void create200OK() {


            ResponseEntity<Category> response =
                    restController.postForEntity(URL, createHttpRequest(2L), Category.class);

            assertEquals(200, response.getStatusCodeValue());
                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void create415() {
            String json = """
                        {
                            "id": "1"
                            "name": "nombre falso",
                        }
                        """;

            ResponseEntity<Category> response =
                    restController.postForEntity(URL, (json), Category.class);

            assertEquals(415, response.getStatusCodeValue());
            assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        }
    }
    @Nested
    public class delete {

        @Test
        void deleteNull() {

            ResponseEntity<Category> response =
                    restController.exchange(URL + "/"+null ,HttpMethod.DELETE, createHttpRequest(null), Category.class);

            assertEquals(400,response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        }

        @Test
        void deleteNotFound() {

            ResponseEntity<Category> response =
                    restController.exchange(URL + "/"+998L ,HttpMethod.DELETE, createHttpRequest(null), Category.class);

            assertEquals(404,response.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }

        @Test
        void deleteOKNoContent() {

            Category category = createDemoCategory();
            categoryService.save(category);

            ResponseEntity<Category> response =
                    restController.exchange(URL + "/"+category.getId() ,HttpMethod.DELETE, createHttpRequest(null), Category.class);

            assertEquals(204,response.getStatusCodeValue());
            assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        }

        @Test
        void deleteFail409() {

            ResponseEntity<Category> response = deleteIdFailmock();

            assertEquals(409,response.getStatusCodeValue());
            assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        }

        private ResponseEntity<Category> deleteIdFailmock() {
            CategoryRepository repository = mock(CategoryRepository.class);
            doReturn(true).when(categoryService).existsById(null);
            doReturn(false).when(categoryService).deleteById(null);
            doThrow(RuntimeException.class).when(repository).deleteById(null);

            return categoryRestController.delete(null);
        }

        @Test
        void deleteAll() {

            ResponseEntity<Category> response =
                    restController.exchange(URL ,HttpMethod.DELETE, createHttpRequest(null), Category.class);

            assertEquals(204,response.getStatusCodeValue());
            assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        }

        @Test
        void deleteAllFail() {


            ResponseEntity<Category> response = deleteAllFailMock();

            assertEquals(409,response.getStatusCodeValue());
            assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        }

        private ResponseEntity<Category> deleteAllFailMock() {
            CategoryRepository repository = mock(CategoryRepository.class);
            doReturn(false).when(categoryService).deleteAll();
            doThrow(RuntimeException.class).when(repository).deleteById(null);

            return categoryRestController.deleteAll();

        }
    }
}
