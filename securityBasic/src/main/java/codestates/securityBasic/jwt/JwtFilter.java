package codestates.securityBasic.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //요청에서 토큰 추출
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        //토큰의 username을 이용해 UserDetail 생성
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.extractSubject(token));

        //토큰 검증
        if (token != null && jwtProvider.validateToken(token, userDetails)) {
            Authentication authentication = jwtProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
