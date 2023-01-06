package codestates.securityBasic.config.auth;

import codestates.securityBasic.model.Member;
import codestates.securityBasic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

//Security 설정에서 loginProcessingUrl(”/login”);으로 요청이 오면 자동으로 UserDetailsService 구현체의
// loadUserByUsername 함수가 실행됩니다.

    @Autowired
    private MemberRepository memberRepository;

    @Override //사용자 정보를 가져오는 메서드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByUsername(username);
        System.out.println("username :" + username);

        if (memberEntity != null) {
            return new PrincipalDetails(memberEntity);
        }
        throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
    }
}
