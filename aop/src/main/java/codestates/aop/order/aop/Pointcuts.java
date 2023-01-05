package codestates.aop.order.aop;
import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts { //pointcut 을 외부 클래스에 모아둘 수 있음

    @Pointcut("execution(* codestates.aop.order..*(..))")
    public void allOrder() {}

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
