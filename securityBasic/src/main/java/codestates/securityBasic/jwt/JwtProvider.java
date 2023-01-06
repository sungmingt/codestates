package codestates.securityBasic.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("spring.jwt.secret")
    private String secretKey;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //Jwt 토큰 생성
    public String createToken(String subject, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    //요청 헤더에서 token 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
    
    //토큰에서 Authentication 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.extractSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    //토큰에서 userName 추출
    public String extractSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //토큰에서 expiration 추출
    public Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    //토큰의 expiration 여부
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //토큰의 유효성 검증
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
