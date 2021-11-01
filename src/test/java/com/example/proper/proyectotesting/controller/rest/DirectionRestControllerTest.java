package com.example.proper.proyectotesting.controller.rest;

import com.example.proper.proyectotesting.service.DirectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.*;
import com.example.proper.proyectotesting.entities.Direction;
import com.example.proper.proyectotesting.service.DirectionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Direction REST Controller Tests")
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

        this.directionService = mock(DirectionServiceImpl.class);
        this.directionRestController = new DirectionRestController(directionService);

    }

        @Nested
        @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        public class FIND_tests {

            @Test
            @DisplayName("Display all entries")
            void findAllTest() {

                List<Direction> arrayList = new ArrayList<>();
                arrayList.add(new Direction());
                arrayList.add(new Direction());
                when(directionService.findAll()).thenReturn(arrayList);

                //Only accepts arrays
                ResponseEntity<Direction[]> response = restController.getForEntity(URL, Direction[].class);

                assertEquals(200, response.getStatusCodeValue());
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

                assertTrue(response.hasBody());
                assertNotNull(response.getBody());

                List<Direction> Directions = List.of(response.getBody());
                assertNotNull(Directions);
                System.out.println(Directions.size());
                assertTrue(Directions.size() == 2);

            }

            @Test
            @DisplayName("Returns the requested object")
            void findOneReturn200Test() {
                Optional<Direction> optionalDirection = Optional.of(new Direction("ex1","ex2","ex3","ex4"));
                directionRestController.create(optionalDirection.get());

                //DirectionRepository directionRepository = mock(DirectionRepository.class);
                //when(directionRepository.findById(1L)).thenReturn(optionalDirection);
                // TODO FIX

                when(directionService.findOne(1L)).thenReturn(optionalDirection);

                ResponseEntity<Direction> response = restController.getForEntity(URL + "/1", Direction.class);

                assertAll(
                        () -> assertNotNull(response.getBody()),
                        () -> assertTrue(response.getBody().getId() == 1L),
                        () -> assertThrows(NullPointerException.class, () -> response.getBody()),
                        () -> assertEquals(200, response.getStatusCodeValue()),
                        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                        () -> assertTrue(response.hasBody()),
                        () -> assertNotNull(response.getBody()),
                        () -> assertNotNull(response.getBody().getId())
                );
            }

            @Test
            @DisplayName("Returns empty optional if the id doesn't exists")
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
        public class Create {

            /**
             * Writes the Request (Headers and body) to process the JSON
             * @param json JSON object as String
             * @return the formatted JSON
             */
            private Object createHttpRequest(String json) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                HttpEntity<String> request = new HttpEntity<>(json, headers);
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

            @Test
            @DisplayName("Returns HTTP 400 Bad Req. if the object is not written properly")
            void create400Test() {
                String json = """
                        {
                            "id": 2L,
                            "street" "exampleStreet",
                            "postCode": "354363",
                            "country": 4.99, 
                            "manufacturer": "Man1"
                        }
                        """;


                ResponseEntity<Direction> response =
                        restController.postForEntity(URL, createHttpRequest(json), Direction.class);

                System.out.println(response.getStatusCodeValue());
                assertEquals(400, response.getStatusCodeValue());
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertTrue(response.hasBody());
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
        public class Update {

            /**
             * Writes the Request (Headers and body) to process the JSON
             * @param json JSON object as String
             * @return the formatted JSON
             */
            private Object createHttpRequest(String json) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                HttpEntity<String> request = new HttpEntity<>(json, headers);
                return request;
            }

            @Test
            @DisplayName("Updates correctly and returns 200 OK")
            void update200OKTest() {

                when(directionService.save(any(Direction.class))).thenReturn(new Direction());
                Direction direction = new Direction("ex1","ex2","ex3","ex4");
                Direction directionmod = new Direction("ex1","ex2","ex3","Man1");

                assertDoesNotThrow(()->restController.put(URL,direction,directionmod));

                // TODO Check exchage method
                assertTrue(false);
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
            @DisplayName("updates the correct direction")
            void update404BadReqTest() {
                when(directionService.save(any(Direction.class))).thenReturn(new Direction());
                Direction direction = new Direction("ex1","ex2","ex3","ex4");
                Direction directionmod = new Direction("ex1","ex2","ex3","Man1");

                restController.put(URL,direction.getCountry(),"Man1");

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