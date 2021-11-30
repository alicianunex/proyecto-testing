package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Category;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
/**
 * Manages the CRUD operations for the Category class
 */
public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns a list with all the Repository's entries
     * through Get protocol
     */
    @GetMapping("/api/category")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    /**
     * Returns the object with the id specified
     * through Get protocol
     */
    @GetMapping("/api/category/{id}")
    public ResponseEntity<Category> findOne(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findOne(id);
        return ResponseEntity.of(categoryOptional);

    }

    /**
     * Adds a new object to the repository
     * and returns an appropriate response entity
     * returns 400 or 200 entity
     * through post protocol
     */
    @PostMapping("/api/category")
    public ResponseEntity<Category> create(@RequestBody Category category) {
    if  (category.getId() != null)
        ResponseEntity.badRequest().build();
        Category cat=   categoryService.save(category);
    return ResponseEntity.ok(cat);
    }

    /**
     * Updates the object provided in the repository
     * and returns an appropriate response entity
     * returns 400 404 or 200 entity
     * through post
     */
    @PutMapping("/api/category")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        if  (category.getId() == null)
            ResponseEntity.badRequest().build();
        if (!categoryService.existsById(category.getId()))
        ResponseEntity.notFound().build();
        Category result=   categoryService.save(category);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes the object with the id provided in the repository
     * and returns an appropriate response entity
     * returns 404 204 or 409 entity
     * though delete protocol
     */
    @DeleteMapping("/api/category/{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id){

        if(!categoryService.existsById(id))
            return ResponseEntity.notFound().build();

        boolean result = categoryService.deleteById(id);
        if (result)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    /**
     * Deletes all the objects in the repository
     * and returns an appropriate response entity
     * returns 204 or 409 entity
     * through delete protocol
     */
    @DeleteMapping("/api/category")
    public ResponseEntity<Category> deleteAll(){

        if(categoryService.deleteAll())
            return ResponseEntity.noContent().build(); // HTTP Status es 204 NO CONTENT
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // HTTP Status es 409 CONFLICT
    }
}