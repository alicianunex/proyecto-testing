package com.example.proyectotesting;


import com.example.proyectotesting.controller.rest.ManufacturerRestController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.*;
import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.service.ManufacturerService;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ManufacturerRestControllerTest {

    private static final String URL = "/api/manufacturers";

    private ManufacturerService manufacturerService;

    private TestRestTemplate restController;


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restController = new TestRestTemplate(restTemplateBuilder);

        this.manufacturerService = mock(ManufacturerService.class);

        new ManufacturerRestController(manufacturerService);


    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Find {

        @Test
        void findAll() {

            List<Manufacturer> arrayList = new ArrayList<>();
            arrayList.add(new Manufacturer());
            arrayList.add(new Manufacturer());
            when(manufacturerService.findAll()).thenReturn(arrayList);

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
        @Test
        void findOneOkTest() {
            Manufacturer manufacturer = createDemoManufacturer();
            ResponseEntity<Manufacturer> response = restController.getForEntity(URL+"/"+manufacturer.getId(), Manufacturer.class);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Manufacturer result = response.getBody();
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(result.getId(), manufacturer.getId());
        }


        private Manufacturer createDemoManufacturer(){
            String json = """
                {
                    "name": "Demo",
                    "description": "Blablablá",
                    "quantity": 2,
                    "price": 5.99
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
        private HttpEntity<String> createHttpRequest(String json){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            return new HttpEntity<>(json, headers);
        }




    }


}