package com.example.proyectotesting.controller.rest;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.service.DirectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Manages the Direction Repository through REST calls
 */
@RestController
public class DirectionRestController {

    private DirectionService directionService;

    public DirectionRestController (DirectionService directionService) {

        this.directionService = directionService;
    }

    // Peticiones HTTP

    /**
     * Finds all the Repository's entries
     * @return List with all the Direction objects
     *
     *
     *
     */
    @GetMapping("/api/directions")
    public List<Direction> findAll(){
        return directionService.findAll();
    }

    /**
     * Returns the object with the id specified
     * through Get protocol
     */
    @GetMapping("/api/directions/{id}")
    public ResponseEntity<Direction> findOne(@PathVariable Long id){
        Optional<Direction> directionOpt = directionService.findOne(id);
        return ResponseEntity.of(directionOpt);
    }

    /**
     * Adds a new object to the repository
     * and returns an appropriate response entity
     * returns 400 or 200 entity
     * through post protocol
     */
    @PostMapping("/api/directions")
    public ResponseEntity<Direction> create(@RequestBody Direction direction){
        if(direction.getId() != null)
            return ResponseEntity.badRequest().build();

        Direction result = directionService.save(direction);
        return ResponseEntity.ok(result); //HTTP Status 200
    }
    /**
     * Updates the object provided in the repository
     * and returns an appropriate response entity
     * returns 400 404 or 200 entity
     * through put protocol
     */
    @PutMapping("/api/directions")
    public ResponseEntity<Direction> update(@RequestBody Direction direction){
        if(direction.getId() == null)
            return ResponseEntity.badRequest().build();
        if(!directionService.existsById(direction.getId()))
            return ResponseEntity.notFound().build();

        Direction result = directionService.save(direction);
        return ResponseEntity.ok(result); //HTTP Status 200
    }


}
