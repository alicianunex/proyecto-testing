package com.service;

import com.entities.DirectionTests;
import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.repository.DirectionRepository;
import com.example.proyectotesting.service.DirectionService;
import com.example.proyectotesting.service.DirectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayName("Direction Service Implementation Tests")
public class DirectionServiceImpTest {

    private DirectionService directionService;
    private DirectionRepository directionRepository;

    @BeforeEach
    protected void setUp() {
        directionRepository = mock(DirectionRepository.class);
        this.directionService = new DirectionServiceImpl(directionRepository);

        List<Direction> arrayList = new ArrayList<>();
        arrayList.add(new Direction());
        arrayList.add(new Direction());
        when(directionRepository.findAll()).thenReturn(arrayList);

    }

    @Nested
    public class FIND_tests {

        @Test
        @DisplayName("Display all entries")
        void findAllFilledReturnTest() {


            List<Direction> found = directionService.findAll();
            List<DirectionTests> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass())
            );

            // if any Direction is null break
            for (Direction count : found)
                assertNotNull(count);
        }

        @Test
        @DisplayName("Display Empty List of Registries")
        void findAllEmptyReturnTest() {

            List<Direction> templist = new ArrayList<>();
            when(directionRepository.findAll())
                    .thenReturn(templist);
            List<Direction> found = directionService.findAll();
            List<DirectionTests> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass()),
                    () -> assertEquals("[]", found.toString())
            );

            // if any Direction is null break
            for (Direction count : found)
                assertNotNull(count);
        }

        @Test
        @DisplayName("Returns the requested object")
        void findOneReturn1Test() {
            //Optional<Direction> optionalDirection = Optional.of((new Direction()));
            // TODO FIX
            //when (directionRepository.findOne(1L)).thenReturn(( new optionalDirection));

            Optional<Direction> found = directionService.findOne(1L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertNotNull(found.get()),
                    () -> assertTrue(found.get().getId() == 1L)
            );
        }

        @Test
        @DisplayName("Returns empty optional")
        void findOneReturnNullTest() {

            Optional<Direction> found = directionService.findOne(null);
            assertThrows(IllegalArgumentException.class,() ->directionService.findOne(null));
            assertFalse(found.isPresent());
            assertTrue(found.isEmpty());
        }
    }

    @Nested
    @DisplayName("Check if id exists")
    // TODO Move this to the repository tests,
    //  Check ONLY call to repo in these tests
    public class ExistsById {
        @Test
        @DisplayName("Returns true if Object Exists")
        void existsbyIdOKTest() {
            assertTrue(directionService.existsById(1L));
        }

        @Test
        @DisplayName("Throws error if id null")
        void existsbyIdNullTest() {
            assertThrows(IllegalArgumentException.class, () -> directionService.existsById(null));
        }

        @Test
        @DisplayName("Returns False if there is no object ")
        void existsbyNoIdTest() {
            assertFalse(directionService.existsById(0L));
        }
    }

    @Nested
    public class Save {

        @Test
        @DisplayName("Doesn't save a null direction")
        void saveNull() {

            assertThrows(IllegalArgumentException.class, () -> directionService.save(null));
            assertNull(directionService.save(null));
        }

        @Test
        @DisplayName("saves the correct direction")
        void saveOKTest() {

            when(directionRepository.save(any(Direction.class)))
                    .thenReturn(new Direction());

            Direction result = directionService.save(new Direction());
            assertNotNull(result);
        }

        @Nested
        public class Count_tests {
            @Test
            @DisplayName("Counts the number of elements")
            void countTest() {

                when(directionRepository.count()).thenReturn(0L);

                Long num = directionService.count();

                assertAll(
                        () -> assertNotNull(num),
                        () -> assertFalse(num < 0 && num > 0),
                        () -> assertEquals(0L, num)
                );
            }
        }

        @Nested
        @DisplayName("Delete test")
        public class delete {

            @Test
            @DisplayName("check the null id")
            void deleteNullTest() {
                when(directionRepository.findById(null)).thenReturn(null);
                assertThrows(IllegalArgumentException.class, () -> directionService.deleteById(0L));
                assertFalse(directionService.deleteById(1L));
            }

            @Test
            @DisplayName("Delete non existing registries")
            void deleteNotContainsTest() {
                when(directionRepository.findById(0L)).thenReturn(Optional.of((new Direction())));
                assertThrows(IllegalArgumentException.class, () -> directionService.deleteById(0L));
                assertFalse(directionService.deleteById(1L));
            }

            @Test
            @DisplayName("Deletes the id provided")
            void deleteOKTest() {

                when(directionRepository.findById(1L)).thenReturn(Optional.of((new Direction())));
                assertTrue(directionService.deleteById(1L));
            }

            @Test
            @DisplayName("Deletes all the registries")
            void deleteAllTest() {

                long reg_count = 2;
                when(directionRepository.count()).thenReturn(reg_count);
                assumeTrue(directionService.count() > 0);
                directionService.deleteAll();
                reg_count = 0;
                when(directionRepository.count()).thenReturn(reg_count);

                assertEquals(0, directionService.count());
            }
        }
        @Test
        @DisplayName("Deletes all the registries")
        void deleteAllFailTest() {

            // TODO FIX

             //when(directionRepository.deleteAll()).thenThrow(Exception.class);
            directionService.deleteAll();

        }
    }

        @Nested
        @DisplayName("Find By City and Country test")
        public class findByCityAndCountry {
            @Test
            @DisplayName("Returns empty when City null")
            void findCityNullTest() {

                List<Direction> result =
                        directionService.findByCityAndCountry(null, "a");
                assertNull(result);
            }

            @Test
            @DisplayName("Returns empty when Country null")
            void findCountryNullTest() {

                List<Direction> result =
                        directionService.findByCityAndCountry("a", null);
                assertNull(result);
            }

            @Test
            @DisplayName("Finds OK by Proper City and Country")
            void findCityOKTest() {

                when(directionRepository.findByCityAndCountry("a", "b")).thenReturn(new ArrayList<>());

                List<Direction> result =
                        directionService.findByCityAndCountry("a", "b");
                assertNotNull(result);
            }
        }
    }
