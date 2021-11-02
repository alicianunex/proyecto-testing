package com.example.proyectotesting.controller.rest;

import org.hibernate.annotations.OrderBy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.*;
import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.service.DirectionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Direction REST Controller Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// TODO implement     @TestClassOrder(ClassOrderer.OrderAnnotation.class)
/*
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.4.0</version>
</dependency>
 */
public class DirectionRestControllerTest {

    private static final String URL = "/api/directions";

    private DirectionService directionService;

    private DirectionRestController directionRestController;
    private TestRestTemplate restController;

    private TestRestTemplate testRestTemplate;

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
        // TODO check usage

    }

        @Nested
        @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class FIND_tests {

            @Test
            @DisplayName("Display all entries")
            @Order(2)
            void findAllTest() {

                List<Direction> arrayList = new ArrayList<>();
                arrayList.add(new Direction());
                arrayList.add(new Direction());
                // Mock not working, it's not called from this method !!
                when(directionService.findAll()).thenReturn(arrayList);

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
                //verify(directionService).findAll();
                //TODO Fix size, increments when others tests run

                assertTrue(Directions.size() >= 2);
            }

            /**
             *Creates a Direction from a default json formatted Direction object
             * @return Direction obj with attributes specified in String json
             */
            private Direction createDemoDirection(){
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
            @Test
            @DisplayName("Returns the requested object, returns 200 OK")
            void findOneReturn200Test() {

                Direction direction = createDemoDirection();
                when(directionService.findOne(1L)).thenReturn(Optional.of(direction));

                ResponseEntity<Direction> response = restController.getForEntity(URL + "/" + direction.getId(), Direction.class);

                assertAll(
                        () -> assertTrue(response.hasBody()),
                        () -> assertNotNull(response.getBody()),
                        () -> assertNotNull(response.getBody().getId()),
                        () -> assertEquals(200, response.getStatusCodeValue()),
                        () -> assertEquals(HttpStatus.OK, response.getStatusCode())
                );
            }

            @Test
            @DisplayName("Returns empty optional if the id doesn't exists finally returns 404 Not Found")
            void findOneEmptyTest() {

                when(directionService.findOne(1L)).thenReturn(Optional.of(new Direction("ex1","ex2","ex3","ex4")));

                ResponseEntity<Direction> response = restController.getForEntity(URL + "/1", Direction.class);

                assertAll(
                        () -> assertNull(response.getBody()),
                        () -> assertFalse(response.hasBody()),
                        () -> assertEquals(404, response.getStatusCodeValue()),
                        () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                        () -> assertFalse(response.hasBody())
                );
            }
        }

        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class Create {

            /**
             * Writes the Request (Headers and body) to process the JSON
             * @param json JSON object as String
             * @return the formatted JSON
             */
            private HttpEntity<String> createHttpRequest(String json) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                json = """
                        {
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;
                HttpEntity<String> request = new HttpEntity<>(json, headers);
                System.out.println(request.toString());
                return request;
            }

            @Test
            @DisplayName("Creates object and Returns HTTP 200 OK")
            void create200Test() {
                String json = """
                        {
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;

                ResponseEntity<Direction> response =
                        restController.postForEntity(URL, createHttpRequest(json), Direction.class);

                System.out.println(response.getStatusCodeValue());

                assertEquals(200, response.getStatusCodeValue());
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.hasBody());

                Direction direction = response.getBody();
                assertNotNull(direction);
                assertEquals("exampleStreet", direction.getStreet());

            }

            private HttpEntity<String> createHttpBadRequest(String json) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                json = """
                        {
                            "id": 14,
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;
                HttpEntity<String> request = new HttpEntity<>(json, headers);
                System.out.println(request.toString());
                return request;
            }

            @Test
            @DisplayName("Returns HTTP 400 Bad Req. if the object is not written properly")
            void create400Test() {
                String json = """
                        {
                            "id": 14,
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;
                HttpEntity<String> jsonmod = createHttpBadRequest(json);

                ResponseEntity<Direction> response =
                        restController.postForEntity(URL, jsonmod, Direction.class);

                System.out.println(response.getStatusCodeValue());

                assertEquals(400, response.getStatusCodeValue());
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertFalse(response.hasBody());
            }

            @Test
            @DisplayName("Returns HTTP 415 Unsupported if JSON is not parsed properly")
            void create415Test() {
                String json = """
                        {
                            "id": "1"
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "city": "excity",
                            "country": 4.99,
                            "manufacturer": "Man1"
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

            /**
             * Writes the Request (Headers and body) to process the JSON
             * @param json JSON object as String
             * @return the formatted JSON
             */
            private HttpEntity<String> createHttpRequest(String json) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                HttpEntity<String> request = new HttpEntity<>(json, headers);
                System.out.println(request.toString());
                return request;
            }
            @Test
            @DisplayName("Throws 500 Internal Server Error")
            void update500InternalerrorTest() {
                String json = """
                        {                       
                            "street": "exampleStreet",
                            "postCode": "354363",
                            "city": "excity",
                            "country": 4.99,
                            "manufacturer": "Man1"
                        }
                        """;
                ResponseEntity<Direction> response =
                        restController.exchange(URL,HttpMethod.PUT, createHttpRequest(json), Direction.class);

                assertEquals(500,response.getStatusCodeValue());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

            }

            private Direction createDemoDirection(){
                String json = """
                {
                   "street": "exampleStreet",
                   "postalCode": 354363,
                   "city": "excity",
                   "country": "AA"
                }
                """;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                HttpEntity<String> request = new HttpEntity<>(json, headers);
                ResponseEntity<Direction> response =
                        restController.postForEntity(URL, request, Direction.class);
                System.out.println(response.getStatusCodeValue());
                System.out.println(response.toString());
                return response.getBody();
            }

            @Test
            @DisplayName("Updates correctly and returns 200 OK")
            void update200OKTest() {

                // TODO Request change in code
                //  200 Not reachable within test
                // exchange method cannot process a null id
                // and the method to test requires such id

                Direction direction = createDemoDirection();
                System.out.println("target id" + direction.getId());
                String json = """
                {
                   "id": 14,
                   "street": "ModStreet",
                   "postalCode": 999999,
                   "city": "Modcity",
                   "country": "BB"
                },
                """;
                when(directionService.existsById(any(Long.class))).thenReturn(true);

                when(directionService.save(any(Direction.class))).thenReturn(new Direction());
                Direction directionoriginal = new Direction("ex1","ex2","ex3","ex4");
                Direction directionmod = new Direction("ex1","ex2","ex3","Man1");
                //assertDoesNotThrow(()->restController.put(URL,directionoriginal,directionmod));

                ResponseEntity<Direction> response =
                        restController.exchange(URL,HttpMethod.PUT, createHttpRequest(json), Direction.class);

                System.out.println(response.getStatusCodeValue());

                assertEquals(HttpStatus.OK,response.getStatusCode());
            }


            @Test
            @DisplayName("Doesn't update and returns 400 Not Found")
            void update400NotFoundTest() {
                when(directionService.save(any(Direction.class))).thenReturn(new Direction());


                Direction direction = new Direction("ex1","ex2","ex3","ex4");
                Direction directionmod = new Direction("ex1","ex2","ex3","Man1");

                when(directionService.existsById(any(long.class))).thenReturn(false);

                restController.put(URL,direction.getCountry(),"Man1");

                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);

                ResponseEntity<Direction> directionResponseEntity =  restController.exchange(URL,
                        HttpMethod.PUT, entity, Direction.class, directionmod);

                assertEquals(400, directionResponseEntity.getStatusCodeValue());
                assertEquals(HttpStatus.BAD_REQUEST, directionResponseEntity.getStatusCode());
                assertTrue(directionResponseEntity.hasBody());
            }

            @Test
            @DisplayName("Does Nothing and returns 404 Not Found")
            void update404NotFoundTest() {
                when(directionService.save(any(Direction.class))).thenReturn(new Direction());
                Direction direction = new Direction("ex1","ex2","ex3","ex4");
                Direction directionmod = new Direction("ex1","ex2","ex3","Man1");

                //restController.put(URL,direction.getCountry(),"Man1");

                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);

                ResponseEntity<Direction> directionResponseEntity =  restController.exchange(URL,
                        HttpMethod.PUT, entity, Direction.class, directionmod);

                assertEquals(404, directionResponseEntity.getStatusCodeValue());
                assertEquals(HttpStatus.NOT_FOUND, directionResponseEntity.getStatusCode());
                assertTrue(directionResponseEntity.hasBody());

            }
        }

    }