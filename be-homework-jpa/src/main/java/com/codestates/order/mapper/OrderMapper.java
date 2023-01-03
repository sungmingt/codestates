package com.codestates.order.mapper;

import com.codestates.coffee.entity.Coffee;
import com.codestates.member.entity.Member;
import com.codestates.order.dto.OrderCoffeeDto;
import com.codestates.order.dto.OrderPatchDto;
import com.codestates.order.dto.OrderPostDto;
import com.codestates.order.dto.OrderResponseDto;
import com.codestates.order.entity.Order;
import com.codestates.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    Order orderPostDtoToOrder(OrderPostDto orderPostDto);

    default Order orderPostDtoToOrder(OrderPostDto orderPostDto){
        Member member = orderPostDto.getMember();


        List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees()
                .stream()
                .map(orderCoffeeDto -> {
                    OrderCoffee orderCoffee = orderCoffeeDtoToOrderCoffees(orderCoffeeDto);

                    long coffeeId = orderCoffeeDto.getCoffeeId();
                    Coffee coffee = new Coffee();
                    coffee.setCoffeeId(coffeeId);

                    orderCoffee.addCoffee(coffee);
                    return orderCoffee;
                })
                .collect(Collectors.toList());

        Order order = new Order();
        order.setMember(member);
        order.setOrderCoffees(orderCoffees);

        return order;
    }

    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto);
    OrderResponseDto orderToOrderResponseDto(Order order, List<Coffee> coffees);
    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);

    OrderCoffee orderCoffeeDtoToOrderCoffees(OrderCoffeeDto orderCoffeeDto);

}
