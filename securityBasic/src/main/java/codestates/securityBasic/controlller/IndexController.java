package codestates.securityBasic.controlller;

import codestates.securityBasic.model.Member;
import codestates.securityBasic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

//    @Secured("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(Member member) {

        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode((rawPassword));
        member.setPassword(encodedPassword);

        memberRepository.save(member);
        return "redirect:/login";
    }


    //=================@Secured, @PreAuthorize===================

    @Secured("ROLE_ADMIN")
    @GetMapping("/info") // SecurityConfig에 .antMatchers("/info/**").access("hasRole('ROLE_ADMIN')") 와 같은 동작이 됩니다.
    public @ResponseBody String info() {
        return "info";
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data") // .antMatchers("/data/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 와 같은 동작이 됩니다.
    public @ResponseBody String data() {
        return "data";
    }

//    @Secured는 1개의 권한을 주고 싶을 때 사용합니다.
//    @PreAuthorize는 1개 이상의 권한을 주고 싶을 때 사용합니다.
//
//    #을 사용하면 파라미터에 접근할 수 있습니다.
//    ex) @PreAuthorize("isAuthenticated() and (( #user.name == principal.name ) or hasRole('ROLE_ADMIN'))")

//    @PostAuthorize는 메서드가 실행되고 응답하기 직전에 권한을 검사하는데 사용됩니다.
//    클라이언트에 응답하기 전에 로그인 상태 또는 반환되는 사용자 이름과 현재 사용자 이름에 대한 검사,
//    현재 사용자가 관리자 권한을 가지고 있는지 등의 권한 후처리를 할 수 있습니다.

}
