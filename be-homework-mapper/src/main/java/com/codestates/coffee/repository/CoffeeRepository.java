package com.codestates.coffee.repository;

import com.codestates.coffee.entity.Coffee;

import java.util.List;

public interface CoffeeRepository {

    Coffee createCoffee(Coffee coffee);

    Coffee updateCoffee(Coffee coffee);

    Coffee findCoffee(long coffeeId);

    List<Coffee> findCoffees();

    void deleteCoffee(long coffeeId);
}
