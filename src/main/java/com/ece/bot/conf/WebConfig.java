package com.ece.bot.conf;

import com.ece.bot.web.interceptor.IpAdressDetectorInterceptor;
import com.ece.bot.web.interceptor.TgUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final static String ADMIN_PREFIX = "/admin";
    private final static String API_PREFIX = "/api";
    @Autowired
    private IpAdressDetectorInterceptor ipAdressDetectorInterceptor;
    @Autowired
    private TgUserInterceptor tgUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipAdressDetectorInterceptor).addPathPatterns("/**");
        registry.addInterceptor(tgUserInterceptor).addPathPatterns("/api/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
