
package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.service.ManufacturerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
/**
 * Manages the CRUD operations for the Category class
 */
public class ManufacturerRestController {

    private final ManufacturerService manufacturerService;

    public ManufacturerRestController (ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    // métodos HTTP
    /**
     * Returns a list with all the Repository's entries
     * through Get protocol
     */
    @GetMapping("/api/manufacturers")
    public List<Manufacturer> findAll(){
        return manufacturerService.findAll();
    }

    /**
     * Returns the object with the id specified
     * through Get protocol
     */
    @GetMapping("/api/manufacturers/{id}")
    public ResponseEntity<Manufacturer> findOne(@PathVariable Long id){
        Optional<Manufacturer> manufacturerOptional = manufacturerService.findOne(id);
        return ResponseEntity.of(manufacturerOptional);
    }

    @PostMapping("/api/manufacturers")
    public ResponseEntity<Manufacturer> create(@RequestBody Manufacturer manufacturer){
        if(manufacturer.getId() != null)
            return ResponseEntity.badRequest().build();

        Manufacturer result = manufacturerService.save((manufacturer));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/manufacturers")
    public ResponseEntity<Manufacturer> update(@RequestBody Manufacturer manufacturer){
        if(manufacturer.getId() == null)
            return ResponseEntity.badRequest().build();
        Manufacturer result = manufacturerService.save(manufacturer);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/manufacturers/{id}")
    public ResponseEntity<Manufacturer> delete(@PathVariable Long id){
        if(!manufacturerService.existsById(id)) // si no hay id entonces NO se borra
            return ResponseEntity.notFound().build(); // HTTP Status es 404
        boolean result = manufacturerService.deleteById(id);
        if (result)
            return ResponseEntity.noContent().build(); // HTTP Status es 204 NO CONTENT
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // HTTP Status es 409 CONFLICT

    }

    @DeleteMapping("/api/manufacturers")
    public ResponseEntity<Manufacturer> deleteAll(){

        if(manufacturerService.deleteAll())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}