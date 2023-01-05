package com.codestates.coffee.repository;

import com.codestates.coffee.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {//(1) 수정된 부분
    Optional<Coffee> findByCoffeeCode(String coffeeCode);

    // (2) 수정된 부분
//    @Query(value = "FROM Coffee c WHERE c.coffeeId = :coffeeId")  // (2-1)
//    @Query(value = "SELECT * FROM COFFEE WHERE coffee_Id = :coffeeId", (2-2)
//																																	nativeQuery = true)
    @Query(value = "SELECT c FROM Coffee c WHERE c.coffeeId = :coffeeId")  // (2-3)
    Optional<Coffee> findByCoffee(long coffeeId);
}