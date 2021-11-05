package com.example.proyectotesting.patterns.behavioral.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book book;

    @BeforeEach
    void setUp() {book = new Book("a","b",1);}

    @Test
    void setIsbn() {
        book.setIsbn("qw");
        assertEquals("qw",book.getIsbn());
    }

    @Test
    void setAuthor() {
        book.setAuthor("vv");
        assertEquals("vv",book.getAuthor());
    }

    @Test
    void setYear() {
        book.setYear(9);
        assertEquals(9,book.getYear());
    }

    @Test
    void testToString() {assertNotNull(book.toString());}
}