package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@TestClassOrder(ClassOrderer.ClassName.class)
@DisplayName("Product Service Implementation Tests")
public class ProductServiceImpTest {

    private ProductService productService;
    private ProductRepository repository;

    @BeforeEach
    @DisplayName("Setting parameters...")
    protected void setUp() {
        repository = mock(ProductRepository.class);
        this.productService = new ProductServiceImpl(repository);
    }

    @Nested
    public class FIND_tests {

        @Test
        @DisplayName("Display all entries")
        void findAllFilledReturnTest() {

            List<Product> arrayList = new ArrayList<>();
            arrayList.add(new Product());
            arrayList.add(new Product());
            when(repository.findAll()).thenReturn(arrayList);

            List<Product> found = productService.findAll();
            List<Product> classarray = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(classarray.getClass(), found.getClass())
            );

            for (Product count : found)
                assertNotNull(count);
            verify(repository).findAll();
        }

        @Test
        @DisplayName("Display Empty List of Registries")
        void findAllEmptyReturnTest() {

            List<Product> templist = new ArrayList<>();
            when(repository.findAll()).thenReturn(templist);

            List<Product> found = productService.findAll();
            List<Product> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass()),
                    () -> assertEquals("[]", found.toString()));

            for (Product count : found)
                assertNotNull(count);
            verify(repository).findAll();
        }

        @Test
        @DisplayName("Returns the requested object")
        void findOneReturn1Test() {
            Optional<Product> optionalProduct = Optional.empty();
            when(repository.findById(2L)).thenReturn(optionalProduct);

            Optional<Product> found = productService.findOne(2L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertFalse(found.isPresent()),
                    () -> assertThrows(NoSuchElementException.class, () ->found.get().getId()));

            verify(repository).findById(2L);
        }

        @Test
        @DisplayName("Returns empty optional if the id doesn't exists")
        void findOneReturnNullTest() {

            when(repository.findById(null))
                    .thenThrow(IllegalArgumentException.class);

            Optional<Product> found = productService.findOne(null);
            assertFalse(found.isPresent());
            assertTrue(found.isEmpty());
            //verify(repository).findById(null);
        }

    @Test
    @DisplayName("Returns empty optional if the id is negative")
    void findOneNegativeIdTest() {

        when(repository.findById(-1L))
                .thenThrow(IllegalArgumentException.class);

        Optional<Product> found = productService.findOne(-1L);
        assertFalse(found.isPresent());
        assertTrue(found.isEmpty());
        //verify(repository).findById(null);
    }
}
    @Nested
    @DisplayName("Check if id exists")
    public class ExistsById {
        @Test
        @DisplayName("Returns true if Object Exists")
        void existsbyIdOKTest() {
            when(repository.existsById(1L)).thenReturn(true);
            assertTrue(productService.existsById(1L));
            verify(repository).existsById(1L);
        }

        @Test
        @DisplayName("Throws error if id null")
        void existsbyIdNullTest() {
            when(repository.existsById(null))
                    .thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> productService.existsById(null));
            verify(repository).existsById(null);
        }

        @Test
        @DisplayName("Returns False if there is no object")
        void existsbyNoIdTest() {
            when(repository.existsById(0L)).thenReturn(false);

            assertFalse(productService.existsById(0L));
            verify(repository).existsById(0L);
        }
    }
    @Nested
    @DisplayName("Find By Price Between test")
    public class findByPriceBetween {

        @Test
        @DisplayName("if min is null returns empty")
        void findPriceMinNullTest() {

            List<Product> result =
                    productService.findByPriceBetween(null,45D);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("if max is null returns empty")
        void findPriceMaxNullTest() {

            List<Product> result =
                    productService.findByPriceBetween(1D,null);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("if min is negative returns empty")
        void findPriceMinNegativeTest() {

            List<Product> result =
                    productService.findByPriceBetween(-1D,45D);
            assertTrue(result.isEmpty());
        }
        @Test
        @DisplayName("if max is negative or 0returns empty")
        void findPriceMaxNegativeTest() {

            List<Product> result =
                    productService.findByPriceBetween(1D,-45D);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("if min is greater than max returns empty")
        void findPriceMinMoreMaxTest() {

            List<Product> result =
                    productService.findByPriceBetween(2D,1D);
            assertTrue(result.isEmpty());
        }
        @Test
        @DisplayName("if min is lower than max returns List")
        void findPriceOKTest() {

            List<Product> array = new ArrayList<>();
            array.add( new Product("n", "d", 1, 1D, new Manufacturer()));

            when (repository.findByPriceBetween(0D,45D)).thenReturn(array);
            List<Product> result =
                    productService.findByPriceBetween(0D,45D);
            assertFalse(result.isEmpty());
            assertNotNull(result.get(0).getQuantity());
        }

    }

        @Nested
        @DisplayName("Find By Manufacturer test")
        public class findByManufacturer {


            @Test
            @DisplayName("Returns empty when Manufacturer null")
            void findManufacturerNullTest() {

                List<Product> result =
                        productService.findByManufacturer(null);
                assertTrue(result.isEmpty());
            }

            @Test
            @DisplayName("Returns empty when Manufacturer is empty")
            void findManufacturerEmptyTest() {

                List<Product> result =
                        productService.findByManufacturer("");
                assertTrue(result.isEmpty());
            }

            @Test
            @DisplayName("Finds OK by Proper Manufacturer")
            void findManufacturerOKTest() {

                when(repository.findByManufacturerName("Manu")).thenReturn(new ArrayList<>());

                List<Product> result =
                        productService.findByManufacturer("Manu");

                assertNotNull(result);
                verify(repository).findByManufacturerName("Manu");
            }
        }

        @Nested
        @DisplayName("Calculates the shipping cost")
        public class CalculateShippingCost {
            Product demoproduct = new Product("n", "d", 1, 1D, new Manufacturer());
            Direction demodirection = new Direction("s", "p", "ci", null);

            @Test
            @DisplayName("if product is null return 0; JaCoCo100")
            void nullProductTest() {

                demodirection.setCountry("a");
                Double result = productService.calculateShippingCost(null, demodirection);
                assertEquals(0, result);
                demodirection.setCountry(null);
            }
            @Test
            @DisplayName("if product and country are null return 0, JaCoCo101")
            void nullProductNullCountryTest() {

                Double result = productService.calculateShippingCost(null, demodirection);
                assertEquals(0, result);
            }

            @Test
            @DisplayName("if all args are null return 0, JaCoCo111")
            void nullAllTest () {
                Double result = productService.calculateShippingCost(null,null);
                assertEquals(0, result);
            }

            @Test
            @DisplayName("if direction is null return 0, JaCoCo011")
            void nullDirectionTest() {

                Double result = productService.calculateShippingCost(demoproduct, null);
                assertEquals(0, result);
            }

            @Test
            @DisplayName("if country is null return 0, JaCoCo001")
            void nullCountryTest() {

                Double result = productService.calculateShippingCost(demoproduct, demodirection);
                assertEquals(0, result);
            }


            @Test
            @DisplayName("if country is spain return 2.99, JaCoCo000")
            void CountrySpainLowercaseTest() {
                demodirection.setCountry("spain");
                Double result = productService.calculateShippingCost(demoproduct, demodirection);
                assertEquals(2.99, result);
            }

            @Test
            @DisplayName("if country is SPaIn return 2.99")
            void CountrySpainBothcaseTest() {
                demodirection.setCountry("SPaIn");
                Double result = productService.calculateShippingCost(demoproduct, demodirection);
                assertEquals(2.99, result);
            }

            @Test
            @DisplayName("if country is NOT spain return 22.99")
            void CountryOtherTest() {
                demodirection.setCountry("France");
                Double result = productService.calculateShippingCost(demoproduct, demodirection);
                assertEquals(22.0, result.shortValue());
            }

            @Test
            @DisplayName("if country is España return 22.99")
            void CountryEspanaTest() {
                demodirection.setCountry("España");
                Double result = productService.calculateShippingCost(demoproduct, demodirection);
                assertEquals(22,  result.shortValue());
            }
        }

        @Nested
        public class Save {

            @Test
            @DisplayName("Doesn't save a null product")
            void saveNull() {
                assertNull(productService.save(null));
            }

            @Test
            @DisplayName("saves the correct product")
            void saveOKTest() {

                Product testdir = new Product();

                when(repository.save(any(Product.class)))
                        .thenReturn(testdir);

                Product result = productService.save(testdir);
                assertNotNull(result);
                verify(repository).save(testdir);
            }
        }

        @Nested
        public class Count_tests {
            @Test
            @DisplayName("Counts the number of elements")
            void countTest() {

                when(repository.count()).thenReturn(0L);
                Long num = productService.count();

                assertAll(
                        () -> assertNotNull(num),
                        () -> assertFalse(num < 0 && num > 0),
                        () -> assertEquals(0L, num));
                verify(repository).count();
            }
        }

        @Nested
        @DisplayName("Delete test")
        public class delete {

            @Test
            @DisplayName("If the id is null returns false")
            void deleteNullTest() {
                assertFalse(productService.deleteById(null));
            }

            @Test
            @DisplayName("Skips deletion of non existing registries")
            void deleteNotContainsTest() {
                when(repository.existsById(0L))
                        .thenReturn(false);

                assertFalse(productService.deleteById(0L));
                verify(repository).existsById(0L);
            }

            @Test
            @DisplayName("Deletes the id provided")
            void deleteOKTest() {
                when(repository.existsById(1L)).thenReturn(true);
                doNothing().when(repository).deleteById(1L);

                assertTrue(productService.deleteById(1L));
                verify(repository).existsById(1L);
                verify(repository).deleteById(1L);
            }

            @Test
            @DisplayName("If repo throws error return false")
            void deleteErrorTest() {
                doThrow(new RuntimeException()).when(repository).deleteById(1L);
                when(repository.existsById(1L)).thenReturn(true);

                assertFalse(productService.deleteById(1L));
                verify(repository).existsById(1L);
            }


            @Test
            @DisplayName("Deletes all the registries correctly")
            void deleteAllTest() {

                long reg_count = 2;
                when(repository.count()).thenReturn(reg_count);
                assumeTrue(productService.count() > 0);

                productService.deleteAll();

                reg_count = 0;
                when(repository.count()).thenReturn(reg_count);

                assertEquals(0, productService.count());
                verify(repository, times(2)).count();
            }

            @Test
            @DisplayName("If delete all fails throws exception,returns false")
            void deleteAllFailTest() {

                doThrow(new RuntimeException()).when(repository).deleteAll();

                assertFalse(productService.deleteAll());

                verify(repository).deleteAll();
            }
        }
    }

