package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Category;
import com.example.proyectotesting.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
/**
 * Implementation for the CRUD operations of the Category object
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    /**
     * Returns a list with all the objects in the repository
     * @return List<Category>
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    /**
     * Returns the object with the id provided
     * @param id id of the object requested
     * @returns Optional with the object requested
     * @returns Empty optional if the object doesn't exists
     */
    public Optional<Category> findOne(Long id) {
        if (id == null || id <= 0)
            return Optional.empty();

        return categoryRepository.findById(id);

    }
    @Override
    /**
     * Checks if the object with the id provided
     * exists in the repository
     */
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
    @Override
    /**
     * Returns the object with the color provided
     * @param color id of the object requested
     * @returns Optional with the object requested
     * @returns Empty optional if the object doesn't exists
     */
    public Optional<Category> findOne(String color) {
        if (color == null)
            return Optional.empty();

        return categoryRepository.findByColor(color);
    }

    @Override
    /**
     * Saves the object provided in the repository
     * @param category The object to save
     */
    public Category save(Category category) {
        if (category == null)
            return null;

        return categoryRepository.save(category);

    }

    @Override
    /**
     * Returns the current size of the repository
     * as a number
     * @return long number of elements present in the repo
     */
    public long count() {
        return categoryRepository.count();
    }

    @Override
    /**
     * Deletes the object with the id provided
     * @param id  id of the object to delete
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteById(Long id) {
        if (id == null || !categoryRepository.existsById(id)) {
            System.out.println("No existe id a eliminar");
            return false;
        } else {
            categoryRepository.deleteById(id);
        }
        return true;

    }
    @Override
    /**
     * Deletes all the objects in the repo
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteAll() {
        try{
            categoryRepository.deleteAll();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
