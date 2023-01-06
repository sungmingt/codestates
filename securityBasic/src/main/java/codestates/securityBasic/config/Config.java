package codestates.securityBasic.config;

import codestates.securityBasic.filter.FirstFilter;
import codestates.securityBasic.filter.SecondFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   //FirstFilter를 적용하기 위한 Config 파일
public class Config {

    // Filter는 다운스트림에 있는 나머지 Filter와 Servlet에만 영향을 주기 때문에 Filter의 실행 순서가 가장 중요합니다.


    @Bean
    public FilterRegistrationBean<FirstFilter> firstFilterRegister()  {
        FilterRegistrationBean<FirstFilter> registrationBean = new FilterRegistrationBean<>(new FirstFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SecondFilter> secondFilterRegister()  {
        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>(new SecondFilter());
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
