package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.ProductRepository;
import com.example.proyectotesting.service.ProductService;

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
@DisplayName("Product REST Controller Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRestControllerTest {

    private ProductService productService;

    private ProductRestController productRestController;
    private TestRestTemplate restController;
    private static final String URL = "/api/products";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restController = new TestRestTemplate(restTemplateBuilder);


        this.productService = mock(ProductService.class);
        this.productRestController = new ProductRestController(productService);
    }

    /**
     *Creates a Direction from a default json formatted Direction object
     * @return Direction obj with attributes specified in String json
     */
    private Product createDemoProduct(){
        String json = """
                        {
                            "name": "name2",
                            "description": "reg354363",
                            "quantity": 2,
                            "price": 4.99
                        }
                        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Product> response =
                restController.postForEntity(URL, request, Product.class);
        return response.getBody();
    }
    /**
     * Writes the Request (Headers and body) to process the JSON
     * @return the formatted JSON
     */
    private HttpEntity<String> createHttpRequest(Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String json = String.format("""
                        {
                            "id": %d,
                            "name": "name2",
                            "description": "reg354363",
                            "quantity": 2,
                            "price": 4.99
                        }
                        """,id);
        return new HttpEntity<>(json, headers);
    }

        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class Find {

            @Test
            @DisplayName("Display all entries")
            @Order(2)
            void findAllTest() {

                //Only accepts arrays
                ResponseEntity<Product[]> response = restController.getForEntity(URL, Product[].class);
                assertAll(
                        () ->assertEquals(200, response.getStatusCodeValue()),
                        () ->assertEquals(HttpStatus.OK, response.getStatusCode()),
                        () ->assertEquals(HttpStatus.valueOf(200), response.getStatusCode()),
                        () ->assertTrue(response.hasBody()),
                        () ->assertNotNull(response.getBody()));

                List<Product> Products = List.of(response.getBody());
                assertNotNull(Products);

                //size increments when others tests run
                assertTrue(Products.size() >= 2);
            }

            @Test
            @DisplayName("Returns the requested object, returns 200 OK")
            void findOneReturn200Test() {

                Product product = createDemoProduct();
                productService.save(product);
                ResponseEntity<Product> response = restController.getForEntity
                        (URL + "/" + product.getId(), Product.class);

                assertAll(
                        () -> assertTrue(response.hasBody()),
                        () -> assertNotNull(response.getBody()),
                        () -> assertNotNull(response.getBody().getId()),
                        () -> assertEquals(200, response.getStatusCodeValue()),
                        () -> assertEquals(HttpStatus.OK, response.getStatusCode()));
            }

            @Test
            @DisplayName("Returns empty optional if the id doesn't exists finally returns 404 Not Found")
            void findOneEmptyTest() {

                ResponseEntity<Product> response = restController.getForEntity(URL + "/1", Product.class);

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
            @DisplayName("Creates object and Returns HTTP 201 Created")
            void create201Test() {

                ResponseEntity<Product> response =
                        restController.postForEntity(URL, createHttpRequest(null), Product.class);

                assertEquals(201, response.getStatusCodeValue());
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertTrue(response.hasBody());

                Product product = response.getBody();
                assertNotNull(product);
                assertEquals("name2", product.getName());
            }

            @Test
            @DisplayName("Returns HTTP 400 Bad Req. if the object is not written properly")
            void create400Test() {


                ResponseEntity<Product> response =
                        restController.postForEntity(URL, createHttpRequest(2L), Product.class);

                assertEquals(400, response.getStatusCodeValue());
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            }

            @Test
            @DisplayName("Returns HTTP 415 Unsupported if JSON is not parsed properly")
            void create415Test() {
                String json = """
                        {
                            "id": "1"
                            "name": "name2",
                        }
                        """;

                ResponseEntity<Product> response =
                        restController.postForEntity(URL, (json), Product.class);

                assertEquals(415, response.getStatusCodeValue());
                assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
            }
        }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Update {

        @Test
        @DisplayName("Updates correctly and returns 200 OK")
        void update200OKTest() {

            ResponseEntity<Product> created =
                    restController.postForEntity(URL, createHttpRequest(null), Product.class);

            ResponseEntity<Product> productResponseEntity =  restController.exchange(URL,
                    HttpMethod.PUT, createHttpRequest(created.getBody().getId()), Product.class);
            assertEquals(HttpStatus.OK,productResponseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Doesn't update and returns 404 Not Found")
        void update404NotFoundTest() {

            Product productmod = new Product("ex1","ex2",3,4D,new Manufacturer());
            productmod.setId(null);

            ResponseEntity<Product> productResponseEntity =  restController.exchange(URL,
                    HttpMethod.PUT, createHttpRequest(99L), Product.class, productmod);

            assertEquals(404, productResponseEntity.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND, productResponseEntity.getStatusCode());
            assertFalse(productResponseEntity.hasBody());
        }

        @Test
        @DisplayName("Does Nothing and returns 400 BadRequest")
        void update400BadReqTest() {

            ResponseEntity<Product> response =
                    restController.exchange(URL,HttpMethod.PUT, createHttpRequest(null), Product.class);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.hasBody());
        }
    }


    @Nested
    @DisplayName("Delete test")
    public class delete {

        @Test
        @DisplayName("If the id is null returns 400")
        void deleteNullTest() {

            ResponseEntity<Product> response =
                    restController.exchange(URL + "/"+null ,HttpMethod.DELETE, createHttpRequest(null), Product.class);

            assertEquals(400,response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        }

        @Test
        @DisplayName("If the id is doesn't exists returns 404")
        void deleteNotFoundTest() {

            ResponseEntity<Product> response =
                    restController.exchange(URL + "/"+998L ,HttpMethod.DELETE, createHttpRequest(null), Product.class);

            assertEquals(404,response.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }

        /*
        @Test
        @DisplayName("Deletes correctly")
        void deleteOKNoContentTest() {

            Product product = createDemoProduct();
            productService.save(product);

            ResponseEntity<Product> response =
                    restController.exchange(URL + "/"+product.getId() ,HttpMethod.DELETE, createHttpRequest(null), Product.class);

            assertEquals(204,response.getStatusCodeValue());
            assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        }
*/
        @Test
        @DisplayName("If delete fails returns 409 ")
        void deleteFail409Test() {

            ResponseEntity<Product> response = deleteIdFailmock();

            assertEquals(409,response.getStatusCodeValue());
            assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        }

        private ResponseEntity<Product> deleteIdFailmock() {
            ProductRepository repository = mock(ProductRepository.class);
            doReturn(true).when(productService).existsById(null);
            doReturn(false).when(productService).deleteById(null);
            doThrow(RuntimeException.class).when(repository).deleteById(null);

            return productRestController.delete(null);
        }

        @Test
        @DisplayName("Deletes all the registries correctly")
        void deleteAllTest() {

            ResponseEntity<Product> response =
                    restController.exchange(URL ,HttpMethod.DELETE, createHttpRequest(null), Product.class);

            assertEquals(204,response.getStatusCodeValue());
            assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        }

        @Test
        @DisplayName("If delete All fails throws exception,returns false")
        void deleteAllFailTest() {
            // TODO need Mock for this method !!

            ResponseEntity<Product> response = deleteAllFailmock();

            assertEquals(409,response.getStatusCodeValue());
            assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        }

        private ResponseEntity<Product> deleteAllFailmock() {
            ProductRepository repository = mock(ProductRepository.class);
            doReturn(false).when(productService).deleteAll();
            doThrow(RuntimeException.class).when(repository).deleteById(null);

            return productRestController.deleteAll();

        }
    }
}
