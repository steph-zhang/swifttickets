package com.stephzhang.swifttickets.config;

import com.stephzhang.swifttickets.interceptor.LoginInterceptor;
import com.stephzhang.swifttickets.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate))
                .excludePathPatterns("/userlogin/login",
                        "/userlogin/sendcode",
                        "/ticketsearch/search",
                        "/ticketsearch/queryTicketLeft",
                        "/ticketsearch/searchAllStations").order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
                .order(0);
    }
}
