package com.codestates;

import com.codestates.order.dto.entity.Order;
import com.codestates.order.dto.entity.OrderCoffee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StampCalculateTest {


    @Test
    void calculateStampCountTest() {
        //given
        int nowCount = 5;
        int earned = 3;

        //when
        int result = StampCalculator.calculateStampCount(nowCount, earned);

        //then
        Assertions.assertEquals(8, result);
    }

    @Test
    void calculateEarnedStampCountTest() {
        //given
        Order order = new Order();

        OrderCoffee orderCoffee1 = new OrderCoffee();
        OrderCoffee orderCoffee2 = new OrderCoffee();
        orderCoffee1.setQuantity(3);
        orderCoffee2.setQuantity(3);

        order.addOrderCoffee(orderCoffee1);
        order.addOrderCoffee(orderCoffee2);

        //when
        int result = StampCalculator.calculateEarnedStampCount(order);

        //then
        Assertions.assertEquals(6, result);
    }



}

