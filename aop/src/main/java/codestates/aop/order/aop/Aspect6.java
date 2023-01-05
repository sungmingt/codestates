package codestates.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class Aspect6 {

    //@Around("codestates.aop.order.aop.Pointcuts.orderAndService()")
    @Around("execution(* *..*Service.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }
}
