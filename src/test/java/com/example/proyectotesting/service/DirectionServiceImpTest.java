package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.*;
import com.example.proyectotesting.repository.DirectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@TestClassOrder(ClassOrderer.ClassName.class)
@DisplayName("Direction Service Implementation Tests")
public class DirectionServiceImpTest {

    private DirectionService directionService;
    private DirectionRepository directionRepository;

    @BeforeEach
    @DisplayName("Setting parameters...")
    protected void setUp() {
        directionRepository = mock(DirectionRepository.class);
        this.directionService = new DirectionServiceImpl(directionRepository);
    }

    @Nested
    public class FIND_tests {

        @Test
        @DisplayName("Display all entries")
        void findAllFilledReturnTest() {

            List<Direction> arrayList = new ArrayList<>();
            arrayList.add(new Direction());
            arrayList.add(new Direction());
            when(directionRepository.findAll()).thenReturn(arrayList);

            List<Direction> found = directionService.findAll();
            List<Direction> classarray = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(classarray.getClass(), found.getClass())
            );

            for (Direction count : found)
                assertNotNull(count);
            verify(directionRepository).findAll();
        }

        @Test
        @DisplayName("Display Empty List of Registries")
        void findAllEmptyReturnTest() {

            List<Direction> templist = new ArrayList<>();
            when(directionRepository.findAll()).thenReturn(templist);

            List<Direction> found = directionService.findAll();
            List<Direction> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass()),
                    () -> assertEquals("[]", found.toString()));

            for (Direction count : found)
                assertNotNull(count);
            verify(directionRepository).findAll();
        }

        @Test
        @DisplayName("Returns the requested object")
        void findOneReturn1Test() {
            Optional<Direction> optionalDirection = Optional.empty();
            when(directionRepository.findById(2L)).thenReturn(optionalDirection);

            Optional<Direction> found = directionService.findOne(2L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertFalse(found.isPresent()),
                    () -> assertThrows(NoSuchElementException.class, () ->found.get().getId()));

            verify(directionRepository).findById(2L);
        }

        @Test
        @DisplayName("Returns empty optional if the id doesn't exists")
        void findOneReturnNullTest() {

            when(directionRepository.findById(null))
                    .thenThrow(IllegalArgumentException.class);

            Optional<Direction> found = directionService.findOne(null);
            assertFalse(found.isPresent());
            assertTrue(found.isEmpty());
            verify(directionRepository).findById(null);
        }
    }

    @Nested
    @DisplayName("Check if id exists")
    public class ExistsById {
        @Test
        @DisplayName("Returns true if Object Exists")
        void existsbyIdOKTest() {
            when(directionRepository.existsById(1L)).thenReturn(true);
            assertTrue(directionService.existsById(1L));
            verify(directionRepository).existsById(1L);
        }

        @Test
        @DisplayName("Throws error if id null")
        void existsbyIdNullTest() {
            when(directionRepository.existsById(null))
                    .thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> directionService.existsById(null));
            verify(directionRepository).existsById(null);
        }

        @Test
        @DisplayName("Returns False if there is no object")
        void existsbyNoIdTest() {
            when(directionRepository.existsById(0L)).thenReturn(false);

            assertFalse(directionService.existsById(0L));
            verify(directionRepository).existsById(0L);
        }
    }

    @Nested
    public class Save {

        @Test
        @DisplayName("Doesn't save a null direction")
        void saveNull() {
            assertNull(directionService.save(null));
        }

        @Test
        @DisplayName("saves the correct direction")
        void saveOKTest() {

            Direction testdir = new Direction();

            when(directionRepository.save(any(Direction.class)))
                    .thenReturn(testdir);

            Direction result = directionService.save(testdir);
            assertNotNull(result);
            verify(directionRepository).save(testdir);
        }
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
                    () -> assertEquals(0L, num));
            verify(directionRepository).count();
        }
    }
    @Nested
    @DisplayName("Delete test")
    public class delete {

        @Test
        @DisplayName("If the id is null prints no result")
        void deleteNullTest() {
            when(directionRepository.findById(null))
                    .thenReturn(Optional.empty());

            assertFalse(directionService.deleteById(null));
            verify(directionRepository).findById(null);
        }

        @Test
        @DisplayName("Skips deletion of non existing registries")
        void deleteNotContainsTest() {
            when(directionRepository.findById(0L))
                    .thenReturn(Optional.empty());

            assertFalse(directionService.deleteById(0L));
            verify(directionRepository).findById(0L);
        }

        @Test
        @DisplayName("Deletes the id provided")
        void deleteOKTest() {

            when(directionRepository.findById(1L))
                    .thenReturn(Optional.of((new Direction())));

            assertTrue(directionService.deleteById(1L));
            verify(directionRepository).findById(1L);
        }

        @Test
        @DisplayName("Deletes all the registries correctly")
        void deleteAllTest() {

            long reg_count = 2;
            when(directionRepository.count()).thenReturn(reg_count);
            assumeTrue(directionService.count() > 0);

            directionService.deleteAll();

            reg_count = 0;
            when(directionRepository.count()).thenReturn(reg_count);

            assertEquals(0, directionService.count());
            verify(directionRepository,times(2)).count();
        }

        @Test
        @DisplayName("If delete fails throws exception,returns false")
        void deleteAllFailTest() {

            doThrow(new RuntimeException()).when(directionRepository).deleteAll();

            assertFalse(directionService.deleteAll());
            verify(directionRepository).deleteAll();
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
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Returns empty when Country null")
        void findCountryNullTest() {

            List<Direction> result =
                    directionService.findByCityAndCountry("a", null);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Finds OK by Proper City and Country")
        void findCityOKTest() {

            when(directionRepository.findByCityAndCountry("a", "b")).thenReturn(new ArrayList<>());

            List<Direction> result =
                    directionService.findByCityAndCountry("a", "b");

            assertNotNull(result);
            verify(directionRepository).findByCityAndCountry("a","b");
        }
    }
}
