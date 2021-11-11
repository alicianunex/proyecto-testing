package com.example.proyectotesting.service;

import com.example.proyectotesting.entities.Direction;
import com.example.proyectotesting.entities.Product;
import com.example.proyectotesting.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProductService --> ProductRepository --> Base de datos
 * Implementation for the CRUD operations of the Product object
 */
@Service
public class  ProductServiceImpl implements ProductService {

    private ProductRepository repository; // dependencia

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    /**
     * Returns a list with all the objects in the repository
     * @return List<Product>
     */
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    /**
     * Returns the object with the id provided
     * @param id id of the object requested
     * @returns Optional with the object requested
     * @returns Empty optional if the object doesn't exists
     */
    public Optional<Product> findOne(Long id) {
        if (id == null || id <= 0)
            return Optional.empty();

        return repository.findById(id);
    }

    @Override
    /**
     * Checks if the object with the id provided
     * exists in the repository
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    /**
     *
     * @param min A int with the minimum value to search for
     * @param max A int with the maximum value to search for
     *
     * @return List<Direction> with the Products that match the param search
     * @return empty ArrayList if either @param is null
     * or if min is greater the max
     */
    public List<Product> findByPriceBetween(Double min, Double max) {
        List<Product> result = new ArrayList<>();
        if (min == null || max == null)
            return result;

        if (min < 0 || max <= 0)
            return result;

        if (min >= max)
            return result;

        return repository.findByPriceBetween(min, max);

    }

    @Override
    /**
     * @param manufacturer A string containing the manufacturer of the Product
     *
     * @return List<Direction> with the Products that match the param search
     * @return empty ArrayList if either @param is null
     */
    public List<Product> findByManufacturer(String manufacturer) {
        List<Product> result = new ArrayList<>();
        if(manufacturer == null || manufacturer.isEmpty() )
            return result;

        return repository.findByManufacturerName(manufacturer);
    }

    @Override
    /**
     * CAlculates the cost of the shipping of a Product to a Direction provided
     * @param product
     * @param direction
     * @return 22,99 if direction country is spain
     * @return 0 if either @param is null
     * @return 20 if country is not spain
     */
    public Double calculateShippingCost(Product product, Direction direction) {

        if(product == null || direction == null || direction.getCountry() == null)
            return 0d;

        double result = 2.99;
        // pais
        if(!direction.getCountry().equalsIgnoreCase("Spain"))
            result += 20; // 20 € de envío fuera de españa

        return result;
    }

    @Override
    /**
     * Saves the object provided in the repository
     * @param category The object to save
     */
    public Product save(Product product) {
        if(product == null)
            return null;

        return repository.save(product);
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
