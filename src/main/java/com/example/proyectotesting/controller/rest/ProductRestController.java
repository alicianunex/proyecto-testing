package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Escenario 1:
 * peticiones HTTP --> ProductRestController --> ProductRepository --> Base de datos (H2)
 *
 * Escenario 2:
 * peticiones HTTP --> ProductRestController --> ProductService --> ProductRepository --> Base de datos (H2)
 *
 * SmartDevice
 * |
 * |
 * SmartPhone
 *
 * Manage CRUD operations of Product repo through REST
 */
@RestController // rest
public class ProductRestController {

    private ProductService productService; // dependencia

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // metodos HTTP
    /**
     * Returns a list with all the Repository's entries
     * through Get protocol
     */
    @GetMapping("/api/products")
    public List<Product> findAll(){
        return productService.findAll();
    }

    /**
     * Returns the object with the id specified
     * through Get protocol
     */
    @GetMapping("/api/products/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id){
        Optional<Product> productOpt = productService.findOne(id);
        return ResponseEntity.of(productOpt); // HTTP Status 200 si hay objeto en el Optional y 404 si no hay objeto en Optional
    }

    /**
     * Adds a new object to the repository
     * and returns an appropriate response entity
     * returns 400 or 201 entity
     * through post protocol
     */
    @PostMapping("/api/products") // crear nuevos productos
    public ResponseEntity<Product> create(@RequestBody Product product){
        if(product.getId() != null) // si hay id entonces NO es creación
            return ResponseEntity.badRequest().build();

        Product result = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product); // HTTP Status es 201
    }

    /**
     * Updates the object provided in the repository
     * and returns an appropriate response entity
     * returns 400 404 or 200 entity
     * through post
     */
    @PutMapping("/api/products") // actualizar un producto existente
    public ResponseEntity<Product> update(@RequestBody Product product){
        if(product.getId() == null)
            return ResponseEntity.badRequest().build(); // HTTP Status 400 Bad Request

        if(!productService.existsById(product.getId())) // si no hay id entonces NO es una actualización
            return ResponseEntity.notFound().build();// HTTP Status 404 Not Found

        Product result = productService.save(product);
        return ResponseEntity.ok(result); // HTTP Status es 200
    }

    /**
     * Deletes the object with the id provided in the repository
     * and returns an appropriate response entity
     * returns 404 204 or 409 entity
     * though delete protocol
     */
    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){

        if(!productService.existsById(id)) // si no hay id entonces NO se borra
            return ResponseEntity.notFound().build(); // HTTP Status es 404

        boolean result = productService.deleteById(id);
        if (result)
            return ResponseEntity.noContent().build(); // HTTP Status es 204 NO CONTENT
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // HTTP Status es 409 CONFLICT

    }

    /**
     * Deletes all the objects in the repository
     * and returns an appropriate response entity
     * returns 204 or 409 entity
     * through delete protocol
     */
    @DeleteMapping("/api/products")
    public ResponseEntity<Product> deleteAll(){

        if(productService.deleteAll())
            return ResponseEntity.noContent().build(); // HTTP Status es 204 NO CONTENT
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // HTTP Status es 409 CONFLICT
    }
}
