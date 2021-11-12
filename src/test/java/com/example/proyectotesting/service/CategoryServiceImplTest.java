package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.*;
import com.example.proyectotesting.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.proyectotesting.service.CategoryService;
import com.example.proyectotesting.service.CategoryServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    protected void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        this.categoryService = new CategoryServiceImpl(categoryRepository);

        List<Category> arrayList = new ArrayList<>();
        arrayList.add(new Category());
        arrayList.add(new Category());
        when(categoryRepository.findAll()).thenReturn(arrayList);
    }

    @Nested
    public class Find {

        @Test
        void findAllFilledReturn() {

            List<Category> found = categoryService.findAll();
            List<Category> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass())
            );

            for (Category count : found)
                assertNotNull(count);
        }

        @Test
        void findAllEmptyReturn() {

            List<Category> tempList = new ArrayList<>();
            when(categoryRepository.findAll())
                    .thenReturn(tempList);
            List<Category> found = categoryService.findAll();
            List<Category> b = new ArrayList<>();

            assertAll(
                    () -> assertNotNull(found),
                    () -> assertSame(b.getClass(), found.getClass()),
                    () -> assertEquals("[]", found.toString())
            );

            for (Category count : found)
                assertNotNull(count);
        }

        @Test
        void findOneReturn1() {
            Optional<Category> optionalCategory = Optional.empty();
            when(categoryRepository.findById(2L)).thenReturn(optionalCategory);

            Optional<Category> found = categoryService.findOne(2L);
            assertAll(
                    () -> assertNotNull(found),
                    () -> assertFalse(found.isPresent()),
                    () -> assertThrows(NoSuchElementException.class, () ->found.get().getId())
            );
        }

        @Test
        void findOneReturnNull() {

            when(categoryRepository.findById(null))
                    .thenThrow(IllegalArgumentException.class);

            Optional<Category> found = categoryService.findOne((Long) null);
            assertFalse(found.isPresent());
            assertTrue(true);
        }
        @Test
        void findOneIdCeroTest() {
            Optional<Category> categoryOpt = categoryService.findOne(0L);
            assertEquals(Optional.empty(), categoryOpt);
        }


        @Test
        void findOneNegativeIdTest() {
            Optional<Category> categoryOpt = categoryService.findOne(-5L);
            assertEquals(Optional.empty(), categoryOpt);
        }

        @Test
        void findByIdTest() {
            List<Category> categories = new ArrayList<>();
            Category c1 = new Category("New Category", "New Color");
            Category c2 = new Category("Old Color", "Color Negro");

            System.out.println(categories + "\n");

            c1.setId(1L);
            c2.setId(2L);

            categories.add(c1);
            categories.add(c2);

            assertEquals(2, categories.size());

            System.out.println("Category 1 :" + c1 + "\n" +
                    "Category 2: " + c2);
            Optional<Category> categoryOpt1 = categoryService.findOne(1L);
            Optional<Category> categoryOpt2 = categoryService.findOne(2L);

            assertNotNull(categoryOpt1);
            assertNotNull(categoryOpt2);
            assertTrue(categoryOpt1.isEmpty());
            assertTrue(categoryOpt2.isEmpty());

        }
    }

    @Nested
    public class ExistsById {
        @Test
        void exitsIdOK() {
            when(categoryRepository.existsById(1L)).thenReturn(true);
            assertTrue(categoryService.existsById(1L));
            verify(categoryRepository).existsById(1L);
        }

        @Test
        void existsIdNull() {
            when(categoryRepository.existsById(null))
                    .thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> categoryService.existsById(null));
            verify(categoryRepository).existsById(null);
        }

        @Test
        void existsNoId() {
            when(categoryRepository.existsById(0L)).thenReturn(false);

            assertFalse(categoryService.existsById(0L));
            verify(categoryRepository).existsById(0L);
        }
    }

    @Nested
    public class Save {

        @Test
        void saveNull() {
            assertNull(categoryService.save(null));
        }

        @Test
        void saveOK() {

            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(new Category());

            Category result = categoryService.save(new Category());
            assertNotNull(result);
        }
    }

    @Nested
    public class Count {
        @Test
        void count() {

            when(categoryRepository.count()).thenReturn(0L);
            Long num = categoryService.count();

            assertAll(
                    () -> assertNotNull(num),
                    () -> assertFalse(false),
                    () -> assertEquals(0L, num)
            );
        }
    }


        @Test
        void deleteNull() {
            when(categoryRepository.findById(null))
                    .thenReturn(Optional.empty());
            assertFalse(categoryService.deleteById(null));
        }

        @Test
        void deleteNotContains() {
            when(categoryRepository.findById(0L))
                    .thenReturn(Optional.empty());

            assertFalse(categoryService.deleteById(0L));
        }


        @Test
        void deleteAll() {

            long reg_count = 2;
            when(categoryRepository.count()).thenReturn(reg_count);
            assumeTrue(categoryService.count() > 0);
            categoryService.deleteAll();
            reg_count = 0;
            when(categoryRepository.count()).thenReturn(reg_count);

            assertEquals(0, categoryService.count());
        }



    }

