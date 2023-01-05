package com.codestates.homework;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.order.entity.Order;
import com.codestates.order.repository.OrderRepository;
import com.codestates.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.codestates.order.entity.Order.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceHomeworkTest {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void cancelOrderTest() {
        // TODO OrderService의 cancelOrder() 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus(ORDER_COMPLETE);

        given(orderRepository.findById(order.getOrderId()))
                .willReturn(Optional.of((order)));

        //when
        //then
        BusinessLogicException ex =
                assertThrows(BusinessLogicException.class,
                        () -> orderService.cancelOrder(order.getOrderId()));

        assertEquals(ExceptionCode.CANNOT_CHANGE_ORDER, ex.getExceptionCode());
    }
}
