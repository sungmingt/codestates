package com.codestates.order.mapper;

import com.codestates.coffee.entity.Coffee;
import com.codestates.order.dto.OrderCoffeeDto;
import com.codestates.order.dto.OrderCoffeeResponseDto;
import com.codestates.order.dto.OrderPatchDto;
import com.codestates.order.dto.OrderResponseDto;
import com.codestates.order.entity.Order;
import com.codestates.order.entity.OrderCoffee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-09T15:36:20+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto) {
        if ( orderPatchDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderId( orderPatchDto.getOrderId() );
        order.setOrderStatus( orderPatchDto.getOrderStatus() );

        return order;
    }

    @Override
    public OrderResponseDto orderToOrderResponseDto(Order order, List<Coffee> coffees) {
        if ( order == null && coffees == null ) {
            return null;
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        if ( order != null ) {
            orderResponseDto.setMember( order.getMember() );
            if ( order.getOrderId() != null ) {
                orderResponseDto.setOrderId( order.getOrderId() );
            }
            orderResponseDto.setOrderStatus( order.getOrderStatus() );
            orderResponseDto.setOrderCoffees( orderCoffeeListToOrderCoffeeResponseDtoList( order.getOrderCoffees() ) );
            orderResponseDto.setCreatedAt( order.getCreatedAt() );
        }

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderResponseDto> list = new ArrayList<OrderResponseDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( orderToOrderResponseDto1( order ) );
        }

        return list;
    }

    @Override
    public OrderCoffee orderCoffeeDtoToOrderCoffees(OrderCoffeeDto orderCoffeeDto) {
        if ( orderCoffeeDto == null ) {
            return null;
        }

        OrderCoffee orderCoffee = new OrderCoffee();

        return orderCoffee;
    }

    protected OrderCoffeeResponseDto orderCoffeeToOrderCoffeeResponseDto(OrderCoffee orderCoffee) {
        if ( orderCoffee == null ) {
            return null;
        }

        Integer quantity = null;

        quantity = orderCoffee.getQuantity();

        long coffeeId = 0L;
        String korName = null;
        String engName = null;
        Integer price = null;

        OrderCoffeeResponseDto orderCoffeeResponseDto = new OrderCoffeeResponseDto( coffeeId, korName, engName, price, quantity );

        return orderCoffeeResponseDto;
    }

    protected List<OrderCoffeeResponseDto> orderCoffeeListToOrderCoffeeResponseDtoList(List<OrderCoffee> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCoffeeResponseDto> list1 = new ArrayList<OrderCoffeeResponseDto>( list.size() );
        for ( OrderCoffee orderCoffee : list ) {
            list1.add( orderCoffeeToOrderCoffeeResponseDto( orderCoffee ) );
        }

        return list1;
    }

    protected OrderResponseDto orderToOrderResponseDto1(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setMember( order.getMember() );
        if ( order.getOrderId() != null ) {
            orderResponseDto.setOrderId( order.getOrderId() );
        }
        orderResponseDto.setOrderStatus( order.getOrderStatus() );
        orderResponseDto.setOrderCoffees( orderCoffeeListToOrderCoffeeResponseDtoList( order.getOrderCoffees() ) );
        orderResponseDto.setCreatedAt( order.getCreatedAt() );

        return orderResponseDto;
    }
}
