package com.ex01.basic.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping : 모든 엔드포인트(api/v1/members) 허용
        // 컨트롤러들의 모든 경로들
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials( true ); //쿠키 전달허용
    }
}


