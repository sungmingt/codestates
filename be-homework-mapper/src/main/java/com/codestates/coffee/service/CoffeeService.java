package com.codestates.coffee.service;

import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public Coffee createCoffee(Coffee coffee) {
        coffeeRepository.createCoffee(coffee);
        return coffee;
    }

    public Coffee updateCoffee(Coffee coffee) {
        coffeeRepository.updateCoffee(coffee);
        return coffee;
    }

    @Transactional(readOnly = true)
    public Coffee findCoffee(long coffeeId) {
        Coffee coffee = coffeeRepository.findCoffee(coffeeId);
        return coffee;
    }

    @Transactional(readOnly = true)
    public List<Coffee> findCoffees() {

        List<Coffee> coffees = coffeeRepository.findCoffees();
        return coffees;
    }

    public void deleteCoffee(long coffeeId) {
        coffeeRepository.deleteCoffee(coffeeId);
    }
}
