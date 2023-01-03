package com.codestates.order.service;

import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.service.CoffeeService;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.order.dto.OrderCoffeeDto;
import com.codestates.order.dto.OrderPostDto;
import com.codestates.order.entity.Order;
import com.codestates.order.entity.OrderCoffee;
import com.codestates.order.repository.OrderRepository;
import com.codestates.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final MemberService memberService;
    private final OrderRepository orderRepository;
    private final CoffeeService coffeeService;

    public OrderService(MemberService memberService,
                        OrderRepository orderRepository, CoffeeService coffeeService) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        // 회원이 존재하는지 확인
        Member member = memberService.findVerifiedMember(order.getMember().getMemberId());

        // TODO 커피가 존재하는지 조회하는 로직이 포함되어야 합니다.
        // 커피가 존재하는지 확인

        for (OrderCoffee orderCoffee : order.getOrderCoffees()) {
            coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId());
        }

        // stamp 추가
        int count = order.getOrderCoffees()
                .stream()
                .mapToInt(orderCoffee -> orderCoffee.getQuantity())
                .sum();

//        addStamp(member, count);

        Stamp stamp = member.getStamp();
        stamp.setStampCount(stamp.getStampCount() + count);
        member.setStamp(stamp);

        return orderRepository.save(order);
    }


    // 메서드 추가
    public Order updateOrder(Order order) {
        Order findOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getOrderStatus())
                .ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));
        findOrder.setModifiedAt(LocalDateTime.now());
        return orderRepository.save(findOrder);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size,
                Sort.by("orderId").descending()));
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        // OrderStatus의 step이 2 이상일 경우(ORDER_CONFIRM)에는 주문 취소가 되지 않도록한다.
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        findOrder.setModifiedAt(LocalDateTime.now());
        orderRepository.save(findOrder);
    }

    //====================================================


    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }

    public List<Coffee> getOrderedCoffeeList(Order order) {
        return order.getOrderCoffees()
                .stream()
                .map(orderCoffee -> orderCoffee.getCoffee())
                .collect(Collectors.toList());
    }

    private void addStamp(Member member, int count) {

    }

}
