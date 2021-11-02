package com.example.proyectotesting;

import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.service.ManufacturerService;
import com.example.proyectotesting.service.ManufacturerServiceImpl;
import com.example.proyectotesting.repository.ManufacturerRepository;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;


import java.util.List;
import java.util.Optional;




public class ManufacturerServiceImplTest {

    ManufacturerRepository repository; // dependencia
    ManufacturerService service;

    @BeforeEach
    void setUp() {
        repository = mock(ManufacturerRepository.class);
        service = new ManufacturerServiceImpl(repository);
    }

    @Test
    void count() {

        when(repository.count()).thenReturn(3L);

        Integer result = Math.toIntExact(service.count());

        assertNotNull(result);
        assertEquals(3, result);
    }


    @Test
    void findAll() {
        List<Manufacturer> manufacturers = service.findAll();
        assertNotNull(manufacturers);
        assertEquals(0, manufacturers.size());
    }


    @Test
    void findOneNullOptional() {
        Optional<Manufacturer> manufacturerOpt = service.findOne(null);

        assertTrue(manufacturerOpt.isEmpty());
    }

}
