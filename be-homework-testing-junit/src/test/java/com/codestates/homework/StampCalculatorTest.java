package com.codestates.homework;

import com.codestates.helper.StampCalculator;
import com.codestates.order.entity.Order;
import com.codestates.order.entity.OrderCoffee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StampCalculatorTest {
    @Test
    @DisplayName("실습1: 스탬프 카운트 계산 단위 테스트")
    public void calculateStampCountTest() {
        // TODO 여기에 테스트 케이스를 작성해주세요.
        //given
        int nowCount = 5;
        int earned = 3;

        //when
        int result = StampCalculator.calculateStampCount(nowCount, earned);

        //then
        Assertions.assertEquals(8, result);
    }

    @Test
    @DisplayName("실습1: 주문 후 누적 스탬프 카운트 계산 탄위 테스트")
    public void calculateEarnedStampCountTest(){
        // TODO 여기에 테스트 케이스를 작성해주세요.
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
