package com.example.proyectotesting.patterns.behavioral.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BookShopTest {

    BookShop bookShop;

    @BeforeEach
    void setUp() {bookShop = new BookShop();}

    @Test
    void addBookTest() {
        Book book = new Book("a","b",1);
        bookShop.addBook(book);
        assertTrue(bookShop.books.size() == 1);
    }

    @Test
    void iteratorTest() {
        Book book = new Book("a","b",1);
        bookShop.addBook(book);
        Iterator<Book> bookiter = bookShop.iterator();
        assertNotNull(bookiter);
        assertTrue(bookiter.next().getAuthor() == "b");
    }

    @Nested
    public class BookShopIteratorTests {

        Iterator<Book> bookiter;
        Book book;

        @BeforeEach
        void setUp(){
            book= new Book("a","b",1);
            bookShop.addBook(book);
            bookiter = bookShop.iterator();
        }

        @Test
        void hasNextTrueTest() {assertTrue(bookiter.hasNext());}

        @Test
        void hasNextFalseTest() {
            bookiter.next();
            assertFalse(bookiter.hasNext());}

        @Test
        void nextThrowsTest() {
            //if(!hasNext())
            bookiter.next();
            assertThrows(NoSuchElementException.class,()->bookiter.next());}

        @Test
        void nextOKTest() {assertEquals(book, bookiter.next());}
    }
}
