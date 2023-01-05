package codestates.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class Aspect1 {

    @Around("execution(* codestates.aop.order..*(..))")
    public Object logPerf(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log -> {}", joinPoint.getStaticPart());
        return joinPoint.proceed();
    }

}
