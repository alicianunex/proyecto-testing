package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.service.DirectionService;

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
@DisplayName("Direction REST Controller Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DirectionRestControllerTest {

    private DirectionService directionService;

    private DirectionRestController directionRestController;
    private TestRestTemplate restController;
    private static final String URL = "/api/directions";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restController = new TestRestTemplate(restTemplateBuilder);

        this.directionService = mock(DirectionService.class);
        this.directionRestController = new DirectionRestController(directionService);
    }

    /**
     *Creates a Direction from a default json formatted Direction object
     * @return Direction obj with attributes specified in String json
     */
    private  Direction createDemoDirection(){
        String json = """
                        {
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Direction> response =
                restController.postForEntity(URL, request, Direction.class);
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
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
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
            ResponseEntity<Direction[]> response = restController.getForEntity(URL, Direction[].class);
            assertAll(
                    () ->assertEquals(200, response.getStatusCodeValue()),
                    () ->assertEquals(HttpStatus.OK, response.getStatusCode()),
                    () ->assertEquals(HttpStatus.valueOf(200), response.getStatusCode()),
                    () ->assertTrue(response.hasBody()),
                    () ->assertNotNull(response.getBody()));

            List<Direction> Directions = List.of(response.getBody());
            assertNotNull(Directions);

            //size increments when others tests run
            assertTrue(Directions.size() >= 2);
        }

        @Test
        @DisplayName("Returns the requested object, returns 200 OK")
        void findOneReturn200Test() {

            Direction direction = createDemoDirection();
            ResponseEntity<Direction> response = restController.getForEntity(URL + "/" + direction.getId(), Direction.class);

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

            ResponseEntity<Direction> response = restController.getForEntity(URL + "/1", Direction.class);

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
        @DisplayName("Creates object and Returns HTTP 200 OK")
        void create200Test() {

            ResponseEntity<Direction> response =
                    restController.postForEntity(URL, createHttpRequest(null), Direction.class);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());

            Direction direction = response.getBody();
            assertNotNull(direction);
            assertEquals("exampleStreet", direction.getStreet());
        }

        @Test
        @DisplayName("Returns HTTP 400 Bad Req. if the object is not written properly")
        void create400Test() {


            ResponseEntity<Direction> response =
                    restController.postForEntity(URL, createHttpRequest(2L), Direction.class);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Returns HTTP 415 Unsupported if JSON is not parsed properly")
        void create415Test() {
            String json = """
                    {
                        "id": "1"
                        "street": "exampleStreet",
                    }
                    """;

            ResponseEntity<Direction> response =
                    restController.postForEntity(URL, (json), Direction.class);

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

            ResponseEntity<Direction> created =
                    restController.postForEntity(URL, createHttpRequest(null), Direction.class);

            ResponseEntity<Direction> directionResponseEntity =  restController.exchange(URL,
                    HttpMethod.PUT, createHttpRequest(created.getBody().getId()), Direction.class);
            assertEquals(HttpStatus.OK,directionResponseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Doesn't update and returns 404 Not Found")
        void update404NotFoundTest() {

            Direction directionmod = new Direction("ex1","ex2","ex3","Man1");
            directionmod.setId(null);

            ResponseEntity<Direction> directionResponseEntity =  restController.exchange(URL,
                    HttpMethod.PUT, createHttpRequest(99L), Direction.class, directionmod);

            assertEquals(404, directionResponseEntity.getStatusCodeValue());
            assertEquals(HttpStatus.NOT_FOUND, directionResponseEntity.getStatusCode());
            assertFalse(directionResponseEntity.hasBody());
        }

        @Test
        @DisplayName("Does Nothing and returns 400 BadRequest")
        void update400BadReqTest() {

            ResponseEntity<Direction> response =
                    restController.exchange(URL,HttpMethod.PUT, createHttpRequest(null), Direction.class);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.hasBody());
        }
    }
}
