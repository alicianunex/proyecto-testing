package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.repository.ManufacturerRepository;
import com.example.proyectotesting.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManufacturerServiceImplTest {

    private ManufacturerService manufacturerService;
    private ManufacturerRepository manufacturerRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        manufacturerRepository = mock(ManufacturerRepository.class);
        productRepository = mock(ProductRepository.class);
        this.manufacturerService = new ManufacturerServiceImpl(manufacturerRepository, productRepository);
    }

    @Test
    void count() {
        when(manufacturerRepository.count()).thenReturn(2l);
        Long result = manufacturerService.count();
        assertNotNull(result);
        assertEquals(2, result);
    }

    @Test
    void countNull() {
        when(manufacturerRepository.count()).thenReturn(0L);
        Long result = manufacturerService.count();
        assertNotNull(result);
        assertEquals(0, result);
    }

    @Nested
    class Find {
        @Test
        void findAll() {
            List<Manufacturer> manufacturers = Arrays.asList(
                    new Manufacturer("Adidas", "123456A", 55000, 1936),
                    new Manufacturer("Nike", "1234567B", 55000, 1946)
            );
            when(manufacturerRepository.findAll()).thenReturn(manufacturers);
            List<Manufacturer> result = manufacturerService.findAll();
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(manufacturerRepository).findAll();
        }

        @Test
        void findOneOk() {
            Manufacturer adidas = new Manufacturer("Adidas", "123456A", 55000, 1936);
            Manufacturer nike = new Manufacturer("Nike", "1234567B", 78000, 1946);
            when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(adidas));
            when(manufacturerRepository.findById(2L)).thenReturn(Optional.of(nike));
            Optional<Manufacturer> result1 = manufacturerService.findOne(1L);
            Optional<Manufacturer> result2 = manufacturerService.findOne(2L);
            assertAll(
                    () -> assertTrue(result1.isPresent()),
                    () -> assertEquals("Adidas", result1.get().getName()),
                    () -> assertEquals("123456A", result1.get().getCif()),
                    () -> assertEquals(55000, result1.get().getNumEmployees()),
                    () -> assertEquals(1936, result1.get().getYear()),
                    () -> assertTrue(result2.isPresent()),
                    () -> assertEquals("Nike", result2.get().getName()),
                    () -> assertEquals("1234567B", result2.get().getCif()),
                    () -> assertEquals(78000, result2.get().getNumEmployees()),
                    () -> assertEquals(1946, result2.get().getYear())
            );
            verify(manufacturerRepository).findById(1L);
            verify(manufacturerRepository).findById(2L);
        }

        @Test
        void findAny() {
            Manufacturer adidas = new Manufacturer("Adidas", "123456A", 55000, 1936);
            when(manufacturerRepository.findById(anyLong())).thenReturn(Optional.of(adidas));
            Optional<Manufacturer> result1 = manufacturerService.findOne(1L);
            assertAll(
                    () -> assertTrue(result1.isPresent()),
                    () -> assertEquals("Adidas", result1.get().getName()),
                    () -> assertEquals("123456A", result1.get().getCif()),
                    () -> assertEquals(55000, result1.get().getNumEmployees()),
                    () -> assertEquals(1936, result1.get().getYear())
            );
            verify(manufacturerRepository).findById(anyLong());
        }

        @Test
        void findOneNegative() {
            when(manufacturerRepository.findById(anyLong())).thenThrow(IllegalArgumentException.class);
            Optional<Manufacturer> manufacturerOpt = manufacturerService.findOne(-9L);
            assertAll(
                    () -> assertTrue(manufacturerOpt.isEmpty())
            );
            verifyNoInteractions(manufacturerRepository);
        }

        @Test
        void findOneNull() {
            when(manufacturerRepository.findById(anyLong())).thenThrow(IllegalArgumentException.class);
            Optional<Manufacturer> manufacturerOpt = manufacturerService.findOne(null);
            assertAll(
                    () -> assertTrue(manufacturerOpt.isEmpty())
            );
            verifyNoInteractions(manufacturerRepository);
        }

        @Test
        void findOneNotContains() {
            Manufacturer adidas = new Manufacturer("Adidas", "123456A", 55000, 1936);
            when(manufacturerRepository.findById(anyLong())).thenReturn(Optional.of(adidas));
            Optional<Manufacturer> result1 = manufacturerService.findOne(999L);
            assertAll(
                    () -> assertTrue(result1.isPresent()),
                    () -> assertEquals("Adidas", result1.get().getName()),
                    () -> assertEquals("123456A", result1.get().getCif()),
                    () -> assertEquals(55000, result1.get().getNumEmployees()),
                    () -> assertEquals(1936, result1.get().getYear())
            );
            verify(manufacturerRepository).findById(anyLong());
        }


        @Test
        void findByYear() {
            List<Manufacturer> manufacturers1 = Arrays.asList(
                    new Manufacturer("Adidas", "123456A", 55000, 1936),
                    new Manufacturer("Reebok", "12345678C", 65000, 1936)
            );
            List<Manufacturer> manufacturers2 = Arrays.asList(
                    new Manufacturer("Nike", "1234567B", 78000, 1946),
                    new Manufacturer("Puma", "123456789D", 35000, 1946)
            );

            when(manufacturerRepository.findByYear(1936)).thenReturn(manufacturers1);
            when(manufacturerRepository.findByYear(1946)).thenReturn(manufacturers2);
            List<Manufacturer> manufacturersOne = manufacturerService.findByYear(1936);
            List<Manufacturer> manufacturersTwo = manufacturerService.findByYear(1946);
            assertAll(
                    () -> assertFalse(manufacturersOne.isEmpty()),
                    () -> assertEquals("Adidas", manufacturersOne.get(0).getName()),
                    () -> assertEquals("123456A", manufacturersOne.get(0).getCif()),
                    () -> assertEquals(55000, manufacturersOne.get(0).getNumEmployees()),
                    () -> assertEquals("Reebok", manufacturersOne.get(1).getName()),
                    () -> assertEquals("12345678C", manufacturersOne.get(1).getCif()),
                    () -> assertEquals(65000, manufacturersOne.get(1).getNumEmployees()),
                    () -> assertFalse(manufacturersTwo.isEmpty()),
                    () -> assertEquals("Nike", manufacturersTwo.get(0).getName()),
                    () -> assertEquals("1234567B", manufacturersTwo.get(0).getCif()),
                    () -> assertEquals(78000, manufacturersTwo.get(0).getNumEmployees()),
                    () -> assertEquals("Puma", manufacturersTwo.get(1).getName()),
                    () -> assertEquals("123456789D", manufacturersTwo.get(1).getCif()),
                    () -> assertEquals(35000, manufacturersTwo.get(1).getNumEmployees())
            );
            verify(manufacturerRepository).findByYear(1936);
            verify(manufacturerRepository).findByYear(1946);
        }

        @Test
        void findYearNull() {

            List<Manufacturer> manufacturer =
                    manufacturerService.findByYear(null);
            assertTrue(manufacturer.isEmpty());
        }

        @Test
        void findYearEmpty() {

            List<Manufacturer> manufacturer =
                    manufacturerService.findByYear(0);
            assertTrue(manufacturer.isEmpty());
        }


        @Test
        void findByCountry() {
            Direction direction1 = new Direction("Calle Nueva", "41001", "Sevilla", "Spain");
            Direction direction2 = new Direction("Avenida Nueva", "28001", "Madrid", "Spain");
            Direction direction3 = new Direction("Xiao Road", null, null, "China");
            Direction direction4 = new Direction("Tanki Road", "124508", "New Delhi", "India");

            Manufacturer Adidas = new Manufacturer("Adidas", "123456A", 55000, 1936);
            Adidas.setDirection(direction1);
            Manufacturer Reebok = new Manufacturer("Reebok", "12345678C", 65000, 1936);
            Reebok.setDirection(direction2);
            Manufacturer Nike = new Manufacturer("Nike", "1234567B", 75000, 1946);
            Nike.setDirection(direction3);
            Manufacturer Puma = new Manufacturer("Puma", "123456789D", 35000, 1946);
            Puma.setDirection(direction4);


            List<Manufacturer> manufacturers1 = new ArrayList<>();
            manufacturers1.add(0, Adidas);
            manufacturers1.add(1, Reebok);
            List<Manufacturer> manufacturers2 = new ArrayList<>();
            manufacturers2.add(0, Nike);
            List<Manufacturer> manufacturers3 = new ArrayList<>();
            manufacturers3.add(0, Puma);

            when(manufacturerRepository.findManufacturerByDirectionCountry("Spain")).thenReturn(manufacturers1);
            when(manufacturerRepository.findManufacturerByDirectionCountry("China")).thenReturn(manufacturers2);
            when(manufacturerRepository.findManufacturerByDirectionCountry("India")).thenReturn(manufacturers3);

            List<Manufacturer> manufacturersOne = manufacturerService.findManufacturerByCountry("Spain");
            List<Manufacturer> manufacturersTwo = manufacturerService.findManufacturerByCountry("China");
            List<Manufacturer> manufacturersThree = manufacturerService.findManufacturerByCountry("India");

            assertAll(
                    () -> assertEquals(manufacturersOne, manufacturers1),
                    () -> assertEquals("Adidas", manufacturersOne.get(0).getName()),
                    () -> assertEquals("Calle Nueva", manufacturersOne.get(0).getDirection().getStreet()),
                    () -> assertEquals("41001", manufacturersOne.get(0).getDirection().getPostalCode()),
                    () -> assertEquals("Sevilla", manufacturersOne.get(0).getDirection().getCity()),
                    () -> assertEquals("Reebok", manufacturersOne.get(1).getName()),
                    () -> assertEquals("Avenida Nueva", manufacturersOne.get(1).getDirection().getStreet()),
                    () -> assertEquals("28001", manufacturersOne.get(1).getDirection().getPostalCode()),
                    () -> assertEquals("Madrid", manufacturersOne.get(1).getDirection().getCity()),
                    () -> assertEquals(manufacturersTwo, manufacturers2),
                    () -> assertEquals("Nike", manufacturersTwo.get(0).getName()),
                    () -> assertEquals("Xiao Road", manufacturersTwo.get(0).getDirection().getStreet()),
                    () -> assertEquals(null, manufacturersTwo.get(0).getDirection().getPostalCode()),
                    () -> assertEquals(null, manufacturersTwo.get(0).getDirection().getCity()),
                    () -> assertEquals(manufacturersThree, manufacturers3),
                    () -> assertEquals("Puma", manufacturersThree.get(0).getName()),
                    () -> assertEquals("Tanki Road", manufacturersThree.get(0).getDirection().getStreet()),
                    () -> assertEquals("124508", manufacturersThree.get(0).getDirection().getPostalCode()),
                    () -> assertEquals("New Delhi", manufacturersThree.get(0).getDirection().getCity())
            );
            verify(manufacturerRepository).findManufacturerByDirectionCountry("Spain");
            verify(manufacturerRepository).findManufacturerByDirectionCountry("China");
            verify(manufacturerRepository).findManufacturerByDirectionCountry("India");
        }

        @Test
        void findOneId() {
            List<Manufacturer> manufacturers = new ArrayList<>();

            Manufacturer manufacturer1 = new Manufacturer("name surname", "12345A", 10, 2010);
            manufacturer1.setId(1L);
            manufacturers.add(manufacturer1);

            assertEquals(1, manufacturers.size());

            Optional<Manufacturer> manufacturer = manufacturerService.findOne(1L);
            assertNotNull(manufacturer);
            assertTrue(manufacturer.isEmpty());
        }


        @Test
        void findAllEmptyReturn() {

            List<Manufacturer> tempList = new ArrayList<>();
            when(manufacturerRepository.findAll())
                    .thenReturn(tempList);
            List<Manufacturer> found = manufacturerService.findAll();
            List<Manufacturer> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass()),
                    () -> assertEquals("[]", found.toString())
            );

            for (Manufacturer count : found)
                assertNotNull(count);
        }

        @Test
        void findAllFilledReturn() {

            List<Manufacturer> arrayList = new ArrayList<>();
            arrayList.add(new Manufacturer());
            arrayList.add(new Manufacturer());
            when(manufacturerRepository.findAll()).thenReturn(arrayList);

            List<Manufacturer> found = manufacturerService.findAll();
            List<Manufacturer> classarray = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(classarray.getClass(), found.getClass())
            );

            for (Manufacturer count : found)
                assertNotNull(count);
            verify(manufacturerRepository).findAll();
        }

        @Test
        void findOneReturn1() {
            Optional<Manufacturer> optionalManufacturer = Optional.empty();
            when(manufacturerRepository.findById(2L)).thenReturn(optionalManufacturer);

            Optional<Manufacturer> found = manufacturerService.findOne(2L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertFalse(found.isPresent()),
                    () -> assertThrows(NoSuchElementException.class, () -> found.get().getId())
            );
        }

        @Test
        void findOneReturnNull() {

            when(manufacturerRepository.findById(null))
                    .thenThrow(IllegalArgumentException.class);

            Optional<Manufacturer> found = manufacturerService.findOne((Long) null);
            assertFalse(found.isPresent());
            assertTrue(true);
        }


        @Test
        void findOneIdCeroTest() {
            Optional<Manufacturer> manufacturerOpt = manufacturerService.findOne(0L);
            assertEquals(Optional.empty(), manufacturerOpt);
        }

        @Test

        void findOneNegativeIdTest() {
            Optional<Manufacturer> manufacturerOpt = manufacturerService.findOne(-5L);
            assertEquals(Optional.empty(), manufacturerOpt);
        }

    }

    @Nested
    public class delete {

        @Test
        void deleteNull() {
            assertFalse(manufacturerService.deleteById(null));
        }




        @Test
        void deleteNotContains() {
            when(manufacturerRepository.existsById(0L))
                    .thenReturn(false);

            assertFalse(manufacturerService.deleteById(0L));
            verify(manufacturerRepository).existsById(0L);
        }

        @Test
        void deleteOK() {
            when(manufacturerRepository.existsById(1L)).thenReturn(true);
            doNothing().when(manufacturerRepository).deleteById(1L);

            assertTrue(manufacturerService.deleteById(1L));
            verify(manufacturerRepository).existsById(1L);
            verify(manufacturerRepository).deleteById(1L);
        }

        @Test
        void deleteError() {
            doThrow(new RuntimeException()).when(manufacturerRepository).deleteById(1L);
            when(manufacturerRepository.existsById(1L)).thenReturn(true);

            assertFalse(manufacturerService.deleteById(1L));
            verify(manufacturerRepository).existsById(1L);
        }


        @Test
        void deleteAll() {

            long reg_count = 2;
            when(manufacturerRepository.count()).thenReturn(reg_count);
            assumeTrue(manufacturerService.count() > 0);

            manufacturerService.deleteAll();

            reg_count = 0;
            when(manufacturerRepository.count()).thenReturn(reg_count);

            assertEquals(0, manufacturerService.count());
            verify(manufacturerRepository, times(2)).count();
        }

        @Test
        void deleteAllFail() {

            doThrow(new RuntimeException()).when(manufacturerRepository).deleteAll();

            assertFalse(manufacturerService.deleteAll());

            verify(manufacturerRepository).deleteAll();
        }

        @Test
        void deleteAllExceptionTest() {
            doThrow(RuntimeException.class).when(manufacturerRepository).deleteAll();
            boolean result = manufacturerService.deleteAll();
            assertThrows(Exception.class, () -> manufacturerRepository.deleteAll());
            verify(manufacturerRepository, times(2)).deleteAll();
            assertFalse(result);
        }

        @Test
        void deleteByIdOk() {
            doThrow(RuntimeException.class).when(manufacturerRepository).deleteById(anyLong());
            boolean result = manufacturerService.deleteById(anyLong());
            assertFalse(result);
            assertThrows(Exception.class, () -> manufacturerRepository.deleteById(anyLong()));
            verify(manufacturerRepository).deleteById(anyLong());
        }
    }

    @Nested
    public class save {
        @Test
        void saveNull() {
            when(manufacturerRepository.save(null)).thenThrow(IllegalArgumentException.class);
            Manufacturer result1 = manufacturerService.save(null);
            assertAll(
                    () -> assertNull(result1)
            );
            verifyNoInteractions(manufacturerRepository);
        }

    }
}

