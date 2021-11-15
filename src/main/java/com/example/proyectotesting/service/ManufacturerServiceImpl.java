package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Manufacturer;
import com.example.proyectotesting.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/**
 * Implementation for the CRUD operations of the Manufacturer object
 */
public class ManufacturerServiceImpl implements ManufacturerService {

    // TODO bring changes from https://github.com/alansastre/proyecto-testing/commit/43547980e15c452400b92007c11e7a5b886a0f20

    private ManufacturerRepository repository;

    public ManufacturerServiceImpl(ManufacturerRepository repository){
        this.repository = repository;
    }


    @Override
    /**
     * Returns a list with all the objects in the repository
     * @return List<Manufacturer>
     */
    public List<Manufacturer> findAll() {
        return repository.findAll();
    }

    @Override
    /**
     * Returns the object with the id provided
     * @param id id of the object requested
     * @returns Optional with the object requested
     * @returns Empty optional if the object doesn't exists
     */
    public Optional<Manufacturer> findOne(Long id) {
        if (id == null || id <= 0)
            return Optional.empty();
        return repository.findById(id);
    }



    @Override
    /**
     *
     * @param year A int containing the year of the Manufacturer
     * @return List<Direction> with the manufacturers that match the param search
     * @return empty ArrayList if either @param is null
     */
    public List<Manufacturer> findByYear(Integer year) {
        List<Manufacturer> result = new ArrayList<>();
        if (year == null || year <= 0)
            return result;
        return repository.findByYear(year);
    }

    @Override
    /**
     * Saves the object provided in the repository
     * @param category The object to save
     */
    public Manufacturer save(Manufacturer manufacturer) {
        if(manufacturer == null)
            return null;
        return repository.save(manufacturer);
    }

    @Override
    /**
     * Returns the current size of the repository
     * as a number
     * @return long number of elements present in the repo
     */
    public long count() {
        return repository.count();
    }

    @Override
    /**
     * Deletes the object with the id provided
     * @param id  id of the object to delete
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteById(Long id) {
        if (id == null || !repository.existsById(id))
            return false;
        try{
            repository.deleteById(id);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    /**
     *
     * @param country A string containing the country of the Manufacturer
     * @return List<Manufacturer> with the manufacturer that match the param search
     * @return empty ArrayList if  @param is null or empty
     */
    public List<Manufacturer> findManufacturerByCountry(String country) {
        List<Manufacturer> result = new ArrayList<>();
        if(country == null || country.isEmpty())
            return result;
        return repository.findManufacturerByDirectionCountry(country);
    }

    @Override
    /**
     * Deletes all the objects in the repo
     * @returns true if deletion is completed
     * @returns false if deletion fails
     * */
    public boolean deleteAll() {
        try{
            repository.deleteAll();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}