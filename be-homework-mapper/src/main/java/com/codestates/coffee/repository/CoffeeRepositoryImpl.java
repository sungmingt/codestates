package com.codestates.coffee.repository;
import com.codestates.coffee.entity.Coffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CoffeeRepositoryImpl implements CoffeeRepository {

    @Autowired
    EntityManager em;


    @Override
    public Coffee createCoffee(Coffee coffee) {
        em.persist(coffee);
        return coffee;
    }

    @Override
    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = em.find(Coffee.class, coffee.getCoffeeId());
        findCoffee.setEngName(coffee.getEngName());  ///////////////////////////////////////////
        findCoffee.setKorName(coffee.getKorName());
        findCoffee.setPrice(coffee.getPrice());

        em.persist(findCoffee);
        return findCoffee;
    }

    @Override
    public Coffee findCoffee(long coffeeId) {
        Coffee coffee = em.find(Coffee.class, coffeeId);
        return coffee;
    }

    @Override
    public List<Coffee> findCoffees() {
        List<Coffee> coffees =
                em.createQuery("select c from Coffee c", Coffee.class).getResultList();

        return coffees;
    }

    @Override
    public void deleteCoffee(long coffeeId) {
        em.remove(coffeeId);
    }
}
