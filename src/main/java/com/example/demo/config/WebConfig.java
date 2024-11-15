package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestMemberDtoArgumentResolver requestMemberDtoArgumentResolver;

    @Autowired
    public WebConfig(RequestMemberDtoArgumentResolver requestMemberDtoArgumentResolver) {
        this.requestMemberDtoArgumentResolver = requestMemberDtoArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestMemberDtoArgumentResolver); // 기존 주입된 인스턴스를 추가
    }
}
