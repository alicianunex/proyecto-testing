package com.example.proyectotesting.controller.rest;


import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.repository.ManufacturerRepository;
import com.example.proyectotesting.service.ManufacturerService;

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
public class ManufacturerRestControllerTest {

    private ManufacturerService manufacturerService;

    private ManufacturerRestController manufacturerRestController;
    private TestRestTemplate restController;
    private static final String URL = "/api/manufacturers";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restController = new TestRestTemplate(restTemplateBuilder);


        this.manufacturerService = mock(ManufacturerService.class);
        this.manufacturerRestController = new ManufacturerRestController(manufacturerService);
    }


    private Manufacturer createDemoManufacturer(){
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
        ResponseEntity<Manufacturer> response =
                restController.postForEntity(URL, request, Manufacturer.class);
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
        @Order(2)
        void findAll() {


            ResponseEntity<Manufacturer[]> response = restController.getForEntity(URL, Manufacturer[].class);
            assertAll(
                    () ->assertEquals(200, response.getStatusCodeValue()),
                    () ->assertEquals(HttpStatus.OK, response.getStatusCode()),
                    () ->assertEquals(HttpStatus.valueOf(200), response.getStatusCode()),
                    () ->assertTrue(response.hasBody()),
                    () ->assertNotNull(response.getBody()));

            List<Manufacturer> Manufacturers = List.of(response.getBody());
            assertNotNull(Manufacturers);

            assertTrue(Manufacturers.size() >= 2);
        }


    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Create {

        @Test
        void create400() {


            ResponseEntity<Manufacturer> response =
                    restController.postForEntity(URL, createHttpRequest(2L), Manufacturer.class);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        void create415() {
            String json = """
                        {
                            "id": "1"
                            "name": "nombre falso",
                        }
                        """;

            ResponseEntity<Manufacturer> response =
                    restController.postForEntity(URL, (json), Manufacturer.class);

            assertEquals(415, response.getStatusCodeValue());
            assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Update {


        @Test
        void update400BadRequest() {

            ResponseEntity<Manufacturer> response =
                    restController.exchange(URL,HttpMethod.PUT, createHttpRequest(null), Manufacturer.class);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.hasBody());
        }
    }

    @Nested
    public class delete {

        @Test
        void deleteNull() {

            ResponseEntity<Manufacturer> response =
                    restController.exchange(URL + "/"+null ,HttpMethod.DELETE, createHttpRequest(null), Manufacturer.class);

            assertEquals(405,response.getStatusCodeValue());
            assertEquals(HttpStatus.METHOD_NOT_ALLOWED,response.getStatusCode());
        }

        @Test
        void deleteNotFound() {

            ResponseEntity<Manufacturer> response =
                    restController.exchange(URL + "/"+998L ,HttpMethod.DELETE, createHttpRequest(null), Manufacturer.class);

            assertEquals(405,response.getStatusCodeValue());
            assertEquals(HttpStatus.METHOD_NOT_ALLOWED,response.getStatusCode());
        }

        @Test
        void deleteOKNoContent() {

            Manufacturer manufacturer = createDemoManufacturer();
            manufacturerService.save(manufacturer);

            ResponseEntity<Manufacturer> response =
                    restController.exchange(URL + "/"+manufacturer.getId() ,HttpMethod.DELETE, createHttpRequest(null), Manufacturer.class);

            assertEquals(405,response.getStatusCodeValue());
            assertEquals(HttpStatus.METHOD_NOT_ALLOWED,response.getStatusCode());
        }


        @Test
        void deleteAll() {

            ResponseEntity<Manufacturer> response =
                    restController.exchange(URL ,HttpMethod.DELETE, createHttpRequest(null), Manufacturer.class);

            assertEquals(405,response.getStatusCodeValue());
            assertEquals(HttpStatus.METHOD_NOT_ALLOWED,response.getStatusCode());
        }

        @Test
        void deleteAllFail() {


            ResponseEntity<Manufacturer> response = deleteAllFailMock();

            assertEquals(409,response.getStatusCodeValue());
            assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        }

        private ResponseEntity<Manufacturer> deleteAllFailMock() {
            ManufacturerRepository repository = mock(ManufacturerRepository.class);
            doReturn(false).when(manufacturerService).deleteAll();
            doThrow(RuntimeException.class).when(repository).deleteById(null);

            return manufacturerRestController.deleteAll();

        }
    }
}