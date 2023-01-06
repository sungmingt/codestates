package codestates.securityBasic.config;

import codestates.securityBasic.handler.LoginSuccessHandler;
import codestates.securityBasic.jwt.JwtFilter;
import codestates.securityBasic.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  //스프링 security filter가 스프링 FilterChain에 등록 됩니다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//securedEnabled = true : Secured 애노테이션이 활성화됨, SecurityConfig가 아닌 Controller에서 애너테이션으로 접근권한 설정을 관리할 수 있게해줌.
//prePostEnabled = true : PreAuthorize, PostAuthorize 애너테이션이 활성화 됩니다.
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();  //form 태그로만 요청이 가능해지고 postman등의 요청이 불가능하게 됩니다.
        http.headers().frameOptions().disable();  //h2 연결할 때 필요합니다.

        http.authorizeRequests()  //url 별 권한설정
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 생성 안함

                .and()  //권한없는 페이지로 접속 시 login 페이지로 넘어감
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")

                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
