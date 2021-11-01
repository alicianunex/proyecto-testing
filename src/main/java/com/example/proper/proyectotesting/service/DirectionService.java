package com.example.proper.proyectotesting.service;

        import com.example.proper.proyectotesting.entities.Direction;

        import java.util.List;
        import java.util.Optional;

public interface DirectionService {

    List<Direction> findAll();

    List<Direction> findByCityAndCountry(String city, String country);

    Optional<Direction> findOne(Long id);

    boolean existsById(Long id);

    Direction save(Direction direction);

    Long count();

    boolean deleteById(Long id);


    boolean deleteAll();
}

