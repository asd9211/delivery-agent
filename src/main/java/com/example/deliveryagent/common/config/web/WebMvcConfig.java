package com.example.deliveryagent.common.config.web;

import com.example.deliveryagent.common.interceptor.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final BearerAuthInterceptor bearerAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(bearerAuthInterceptor)
                .excludePathPatterns("/member", "/member/login")
                .addPathPatterns("/member/**")
                .addPathPatterns("/rider/**")
                .addPathPatterns("/devliery/**");
    }
}
