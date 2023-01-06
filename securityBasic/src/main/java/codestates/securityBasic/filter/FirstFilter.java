package codestates.securityBasic.filter;

import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {

    @Override  // 필터 초기화 작업
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("FirstFilter 생성");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========First 필터 시작========");
        chain.doFilter(request, response);  // request, response 파리미터를 이용하여 요청/응답의 필터 작업 수행 + 다음필터수행행
       System.out.println("========First 필터 종료========");
    }

    @Override   // 주로 필터가 사용한 자원을 반납
    public void destroy() {
        System.out.println("FirstFilter 사라짐");
        Filter.super.destroy();
    }
}
