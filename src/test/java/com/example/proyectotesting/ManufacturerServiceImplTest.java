package com.example.proyectotesting;

import com.example.proyectotesting.entities.*;
import com.example.proyectotesting.repository.ManufacturerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.proyectotesting.service.ManufacturerService;
import com.example.proyectotesting.service.ManufacturerServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ManufacturerServiceImplTest {
    private ManufacturerService manufacturerService;
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    protected void setUp() {
        manufacturerRepository = mock(ManufacturerRepository.class);
        this.manufacturerService = new ManufacturerServiceImpl(manufacturerRepository);

        List<Manufacturer> arrayList = new ArrayList<>();
        arrayList.add(new Manufacturer());
        arrayList.add(new Manufacturer());
        when(manufacturerRepository.findAll()).thenReturn(arrayList);
    }

    @Nested
    public class Find {

        @Test
        void findAllFilledReturn() {

            List<Manufacturer> found = manufacturerService.findAll();
            List<Manufacturer> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass())
            );

            for (Manufacturer count : found)
                assertNotNull(count);
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
        void findOneReturn1() {
            Optional<Manufacturer> optionalManufacturer = Optional.empty();
            when(manufacturerRepository.findById(2L)).thenReturn(optionalManufacturer);

            Optional<Manufacturer> found = manufacturerService.findOne(2L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertFalse(found.isPresent()),
                    () -> assertThrows(NoSuchElementException.class, () ->found.get().getId())
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
    }


    @Nested
    public class Save {

        @Test
        void saveNull() {
            assertNull(manufacturerService.save(null));
        }

        @Test
        void saveOK() {

            when(manufacturerRepository.save(any(Manufacturer.class)))
                    .thenReturn(new Manufacturer());

            Manufacturer result = manufacturerService.save(new Manufacturer());
            assertNotNull(result);
        }
    }

    @Nested
    public class Count {
        @Test
        void count() {

            when(manufacturerRepository.count()).thenReturn(0L);
            Long num = manufacturerService.count();

            assertAll(
                    () -> assertNotNull(num),
                    () -> assertFalse(false),
                    () -> assertEquals(0L, num)
            );
        }
    }


    @Test
    void deleteNull() {
        when(manufacturerRepository.findById(null))
                .thenReturn(Optional.empty());
        assertFalse(manufacturerService.deleteById(null));
    }

    @Test
    void deleteNotContains() {
        when(manufacturerRepository.findById(0L))
                .thenReturn(Optional.empty());

        assertFalse(manufacturerService.deleteById(0L));
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
    }


}


