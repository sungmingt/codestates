package com.codestates.sample;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class HelloReactorExample {
    public static void main(String[] args) throws InterruptedException {
        Flux    // (1) Flux로 시작 -> Reactor Sequence가 여러 건의 데이터를 처리함을 의미
                .just("Hello", "Reactor")               // (2) 원본 데이터 소스로부터(Original Data Source) 데이터를 emit하는 Publisher의 역할
                .map(message -> message.toUpperCase())  // (3)
                .publishOn(Schedulers.parallel())  // (4) Reactor Sequence에서 쓰레드 관리자 역할을 하는 Scheduler를 지정하는 Operator
        // (4)와 같이 publishOn() Operator에 Scheduler를 지정하면 publishOn()을 기준으로 Downstream의 쓰레드가 Scheduler에서 지정한 유형의 쓰레드로 변경됩니다.
//      즉, 여기서는 Reactor Sequence 상에서 두 개의 쓰레드가 실행됩니다.

                .subscribe(System.out::println,         // (5)
                        error -> System.out.println(error.getMessage()),  // (6)
                        () -> System.out.println("# onComplete"));        // (7)

//        (5)의 subscribe()는 파라미터로 총 세 개의 람다 표현식을 가지는데, (5)의 첫 번째 파라미터는 Publisher가 emit한 데이터를 전달 받아서 처리하는 역할을 합니다.
//        (6)의 두 번째 파라미터는 Reqctor Sequence 상에서 에러가 발생할 경우, 해당 에러를 전달 받아서 처리하는 역할을 합니다.
//        (7)의 세 번째 파라미터는 Reactor Sequence가 종료된 후 어떤 후처리를 하는 역할을 합니다.
//
//        subscribe() 메서드로 전달된 세 개의 람다 표현식은 Subscriber에게 전달되어 각각의 동작을 수행한다는 사실을 기억하세요!

        Thread.sleep(100L);
        // Reactor Sequence에 Scheduler를 지정하면 main 쓰레드 이외에 별도의 쓰레드가 하나 더 생깁니다.
        //Reactor에서 Scheduler로 지정한 쓰레드는 모두 데몬 쓰레드이기 때문에 주 쓰레드인 main 쓰레드가 종료되면 동시에 종료됩니다.
        //따라서 main 쓰레드를 Thread.sleep(100L)을 통해 0.1초 정도 동작을 지연시키면 그 0.1초 사이에 Scheduler로 지정한 데몬 쓰레드를 통해 Reactor Sequence가 정상 동작을 하게 됩니다.
    }
}
