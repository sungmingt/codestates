package codestates.securityBasic.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginEvent {

    @EventListener  //로그인 상황에서 특정상황 발생 시 수행할 동작 정의
    public void handleInvalidCredentials(AuthenticationFailureBadCredentialsEvent event) {
        System.out.println(event.getAuthentication().getPrincipal() + " 인증 시도 중..");
        System.out.println("비밀번호가 틀렸습니다.");
    }  //AuthenticationSuccessHandler과의 차이?

}
