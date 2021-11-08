package com.example.proyectotesting.patterns.behavioral.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookShop Tests")
public class BookShopTest {

    BookShop bookShop;

    @BeforeEach
    void setUp() {bookShop = new BookShop();}

    @Test
    @DisplayName("Adds a book to the bookshop")
    void addBookTest() {
        Book book = new Book("a","b",1);
        bookShop.addBook(book);
        assertTrue(bookShop.books.size() == 1);
    }

    @Test
    @DisplayName("Creates an iterator with the books")
    void iteratorTest() {
        Book book = new Book("a","b",1);
        bookShop.addBook(book);
        Iterator<Book> bookiter = bookShop.iterator();
        assertNotNull(bookiter);
        assertTrue(bookiter.next().getAuthor() == "b");
    }

    @Nested
    @DisplayName("Iterator Methods")
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
        @DisplayName("Returns true if there is another book in the iterator")
        void hasNextTrueTest() {assertTrue(bookiter.hasNext());}

        @Test
        @DisplayName("When no more books are present returns false")
        void hasNextFalseTest() {
            bookiter.next();
            assertFalse(bookiter.hasNext());
        }

        @Test
        @DisplayName("Throws exception if there is no more books in the iterator")
        void nextThrowsTest() {
            //if(!hasNext())
            bookiter.next();
            assertThrows(NoSuchElementException.class,()->bookiter.next());
        }

        @Test
        @DisplayName("Changes to te next book in the iterator")
        void nextOKTest() {assertEquals(book, bookiter.next());}
    }
}
