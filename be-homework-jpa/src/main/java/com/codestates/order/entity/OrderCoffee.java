package com.codestates.order.entity;

import com.codestates.coffee.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCoffee {

    @Id @GeneratedValue
    private Long orderCoffeeId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    private int quantity;

    private void addOrder(Order order) {
        this.order = order;
    }

    public void addCoffee(Coffee coffee) {
        this.coffee = coffee;
    }
}
