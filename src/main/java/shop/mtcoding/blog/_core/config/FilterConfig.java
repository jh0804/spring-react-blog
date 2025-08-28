package shop.mtcoding.blog._core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.mtcoding.blog._core.filter.CorsFilter;
import shop.mtcoding.blog._core.filter.JwtFilter;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    @Value("${allow.host}")
    private String host;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        System.out.println("CORS 필터 등록");
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(host));
        bean.addUrlPatterns("/*");
        bean.setOrder(1); // 낮은 번호부터 실행됨.
        return bean;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        System.out.println("JWT 필터 등록");
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(new JwtFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 낮은 번호부터 실행됨.
        return bean;
    }
}