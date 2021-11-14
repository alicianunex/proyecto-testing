package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.*;
import com.example.proyectotesting.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
                    () -> assertThrows(NoSuchElementException.class, () -> found.get().getId())
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
        @Test
        void findAllTest() {
            Category category1 = new Category("Bebes", "blue");
            Category category2 = new Category("Electronica", "black");
            Category category3 = new Category("Baño", "white");
            Category category4 = new Category("Mascotas", "red");
            List<Category> categories = new ArrayList<>();
            when(categoryRepository.findAll()).thenReturn(categories);
            List<Category> result = categoryService.findAll();
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(0,result.size())
            );
            verify(categoryRepository).findAll();
        }


        @Test
        void findByColorTest() {
            Category category1 = new Category("Bebes", "blue");
            Category category2 = new Category("Electronica", "black");
            Category category3 = new Category("Baño", "white");
            Category category4 = new Category("Mascotas", "red");
            when(categoryRepository.findByColor("blue")).thenReturn(Optional.of(category1));
            when(categoryRepository.findByColor("black")).thenReturn(Optional.of(category2));
            when(categoryRepository.findByColor("white")).thenReturn(Optional.of(category3));
            when(categoryRepository.findByColor("red")).thenReturn(Optional.of(category4));
            Optional<Category> categoryOne = categoryService.findOne("blue");
            Optional<Category> categoryTwo = categoryService.findOne("black");
            Optional<Category> categoryThree = categoryService.findOne("white");
            Optional<Category> categoryFour = categoryService.findOne("red");
            assertAll(
                    () -> assertNotNull(categoryOne),
                    // () -> assertEquals(1L, categoryOne.getId()),
                    () -> assertEquals("Bebes", categoryOne.get().getName()),
                    () -> assertEquals("blue", categoryOne.get().getColor()),
                    () -> assertNotNull(categoryTwo),
                    // () -> assertEquals(2L, categoryTwo.getId()),
                    () -> assertEquals("Electronica", categoryTwo.get().getName()),
                    () -> assertEquals("black", categoryTwo.get().getColor()),
                    () -> assertNotNull(categoryThree),
                    // () -> assertEquals(3L, categoryThree.getId()),
                    () -> assertEquals("Baño", categoryThree.get().getName()),
                    () -> assertEquals("white", categoryThree.get().getColor()),
                    () -> assertNotNull(categoryFour),
                    // () -> assertEquals(4L, categoryFour.getId()),
                    () -> assertEquals("Mascotas", categoryFour.get().getName()),
                    () -> assertEquals("red", categoryFour.get().getColor())

            );
            verify(categoryRepository).findByColor("blue");
            verify(categoryRepository).findByColor("black");
            verify(categoryRepository).findByColor("white");
            verify(categoryRepository).findByColor("red");
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

    @Nested
    public class Delete {
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

        @Test
        void deleteNegative() {
            doThrow(RuntimeException.class).when(categoryRepository).deleteById(anyLong());
            boolean result = categoryService.deleteById(-9L);
            assertFalse(result);
            assertThrows(Exception.class, () -> categoryRepository.deleteById(-9L));
            verify(categoryRepository).deleteById(anyLong());
        }

        @Test
        void deleteByIdOk() {
            doThrow(RuntimeException.class).when(categoryRepository).deleteById(anyLong());
            boolean result = categoryService.deleteById(anyLong());
            assertFalse(result);
            assertThrows(Exception.class, () -> categoryRepository.deleteById(anyLong()));
            verify(categoryRepository).deleteById(anyLong());
        }


    }
}
