package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.repository.DirectionRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/**
 * Implementation for the CRUD operations of the Direction object
 * */
public class DirectionServiceImpl implements DirectionService{

    DirectionRepository directionRepository;

    public DirectionServiceImpl(DirectionRepository directionRepository){
        this.directionRepository = directionRepository;
    }

    @Override
    /**
     * Returns a list with all the objects in the repository
     * @return List<Direction>
     */
    public List<Direction> findAll() {
        List<Direction> answer;
        answer = directionRepository.findAll();
        if (answer.size() < 1)
            System.out.println("Empty Table");
        return answer;
    }

    @Override
    /**
     * Returns the object with the id provided
     * @param id id of the object requested
     * @returns Optional with the object requested
     * @returns Empty optional if the object doesn't exists
     */
    public Optional<Direction> findOne(Long id) {
        try {
            Optional<Direction> optionalanswer = directionRepository.findById(id);
            if (optionalanswer.isEmpty())
                System.out.println("No result");
            return optionalanswer;
        }
        catch(IllegalArgumentException error){
            error.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    /**
     * Checks if the object with the id provided
     * exists in the repository
     */
    public boolean existsById(Long id) {
        return directionRepository.existsById(id);
    }

    @Override
    /**
     * Saves the object provided in the repository
     * @param category The object to save
     */
    public Direction save(Direction direction) {
        try {
            if (direction != null) {

                return directionRepository.save(direction);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException error) {
            error.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * Returns the current size of the repository
     * as a number
     * @return long number of elements present in the repo
     */
    public Long count() {

        long answer =directionRepository.count();
        if (answer < 1)
            System.out.println("Empty Table");
        return answer;
    }

    @Override
    /**
     * Deletes the object with the id provided
     * @param id  id of the object to delete
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteById(Long id) {

        try {
            // directionrepo.exists()
            Optional<Direction> directionoptional = directionRepository.findById(id);
            if (directionoptional.isEmpty()) {
                System.out.println("No result");
                throw new IllegalArgumentException();
            }
            Direction directionans = directionoptional.get();
            directionRepository.delete(directionans);
            return true;
        }
        catch(Exception error){
            error.printStackTrace();
            return false;
        }
    }

    @Override
    /**
     * Deletes all the objects in the repo
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteAll() {

        try {
            directionRepository.deleteAll();
            return true;
        }
        catch(Exception error){
            error.printStackTrace();
            return false;
        }
    }

    @Override
    /**
     *
     * @param city A string containing the city that contains the direction
     * @param country A string containing the country of the Direction
     * @return List<Direction> with the directions that match the param search
     * @return empty ArrayList if either @param is null
     */
    public List<Direction> findByCityAndCountry(String city, String country) {
        List<Direction> result = new ArrayList<>();
        if (city == null || country == null)
            return result;
        return directionRepository.findByCityAndCountry(city,country);
    }

}