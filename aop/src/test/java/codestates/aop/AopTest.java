package codestates.aop;

import codestates.aop.order.OrderRepository;
import codestates.aop.order.OrderService;
import codestates.aop.order.aop.Aspect1;
import codestates.aop.order.aop.Aspect6;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Import(Aspect6.class) // 설정파일을 추가할때 사용하지만, 빈 등록도 된다.
public class AopTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void beforeTest() {
        orderService.orderItem("itemA");
        log.info("class info = {}", orderService.getClass().getName());
    }

}
