package com.codestates.slice.repository.coffee;

import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.repository.CoffeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CoffeeRepositoryHomeworkTest {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void findByCoffeeCodeTest() {
        // TODO CoffeeRepository의 findByCoffeeCode() 메서드에 대한 테스트 케이스를 여기에 작성해 주세요.
        Coffee coffee = new Coffee();
        coffee.setCoffeeId(1L);
        coffee.setEngName("latte");
        coffee.setKorName("라떼");
        coffee.setCoffeeCode("LAT");
        coffee.setPrice(2000);
        coffee.setCoffeeStatus(Coffee.CoffeeStatus.COFFEE_FOR_SALE);

        Coffee coffee2 = new Coffee();
        coffee2.setCoffeeId(2L);
        coffee2.setEngName("americano");
        coffee2.setKorName("아메리카노");
        coffee2.setCoffeeCode("AME");
        coffee2.setPrice(3000);
        coffee2.setCoffeeStatus(Coffee.CoffeeStatus.COFFEE_FOR_SALE);

        Coffee saved = coffeeRepository.save(coffee);
        Coffee saved2 = coffeeRepository.save(coffee2);

        Optional<Coffee> findCoffee = coffeeRepository.findByCoffeeCode("LAT");
        assertEquals(coffee.getCoffeeCode(), findCoffee.get().getCoffeeCode());
        assertEquals(coffee.getEngName(), findCoffee.get().getEngName());
    }

    @Test
    public void findByCoffeeTest() {
        // TODO CoffeeRepository의 findByCoffeeCode() 메서드에 대한 테스트 케이스를 여기에 작성해 주세요.
    }
}
