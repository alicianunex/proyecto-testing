package com.example.proyectotesting.patterns.behavioral.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Object that stores book objects
 * Uses an iterator to return the books stored
 */
public class BookShop implements Iterable<Book>{

    List<Book> books;

    public BookShop(){
        books = new ArrayList<>();
    }

    /**
     * Adds the provided book to the inner list of the shop
     * @param book
     */
    public void addBook(Book book){
        books.add(book);
    }

    @Override
    /**
     * Creates and returns a new iterator
     * the object Allows easy access
     * to each book in the shop
     */
    public Iterator<Book> iterator() {
        return new BookShopIterator();
    }

    private class BookShopIterator implements Iterator<Book>{

        int currentIndex = 0;

        @Override
        /**
         * True if the iterator has a next element
         * False if the current element is the last in the iterator
         */
        public boolean hasNext() {
            return this.currentIndex < books.size();
        }

        /**
         * returns the next book in the iterator
         * throws NoSuchElementException if there
         * is no book to return
         */
        @Override
        public Book next() {
        	if(!hasNext())
        		throw new NoSuchElementException();
        	
            return books.get(currentIndex++);
        }
    }
}
