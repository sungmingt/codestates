package com.codestates.coffee.repository;

import com.codestates.coffee.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findByCoffeeCode(String coffeeCode);

//    @Query(value = "FROM Coffee c WHERE c.coffeeId = :coffeeId")
//    @Query(value = "SELECT * FROM COFFEE WHERE coffee_Id = :coffeeId", nativeQuery =true)
    @Query(value = "SELECT c FROM Coffee c WHERE c.coffeeId = :coffeeId")
    Optional<Coffee> findByCoffee(@Param("coffeeId") long coffeeId);
}
