package com.codestates;

import com.codestates.order.dto.entity.Order;
import com.codestates.order.dto.entity.OrderCoffee;

public class StampCalculator {

    public static int calculateStampCount(int nowCount, int earned) {
        return nowCount + earned;
    }

    public static int calculateEarnedStampCount(Order order) {
        return order.getOrderCoffees().stream()
                .map(OrderCoffee::getQuantity)
                .mapToInt(quantity -> quantity)
                .sum();
    }
}
