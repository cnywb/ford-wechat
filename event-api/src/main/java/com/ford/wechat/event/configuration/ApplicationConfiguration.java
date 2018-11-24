/**
 * Copyright (c)  2016.
 * All rights reserved.
 * AppConfig.java 16/8/31 18:27
 */
package com.ford.wechat.event.configuration;

import com.ford.wechat.event.interceptor.AuthenticationInterceptor;
import com.ford.wechat.event.interceptor.SessionInterceptor;
import io.dabing.core.web.config.ApiServiceBeanPostProcessor;
import io.dabing.core.web.manager.ApiServiceDefinitionManager;
import io.dabing.core.web.manager.DefaultApiServiceDefinitionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 应用程序启动配置
 */
@Configuration
@EnableRedisHttpSession
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
    private static DefaultApiServiceDefinitionManager definitionManager = new DefaultApiServiceDefinitionManager();

    private static ApiServiceBeanPostProcessor apiServiceBeanPostProcessor = new ApiServiceBeanPostProcessor();

    private static Log4jFastJsonHttpMessageConverter  fastJsonHttpMessageConverter = new Log4jFastJsonHttpMessageConverter ();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //临时允许跨域访问
//        registry.addInterceptor(new CrosAccessInterceptor());
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/e/**").excludePathPatterns("/e/open/**", "/e/login" ,"");
    }
    @Bean
    public ApiServiceDefinitionManager definitionManager() {
        return definitionManager;
    }

    @Bean
    public ApiServiceBeanPostProcessor apiServiceBeanPostProcessor() {
        apiServiceBeanPostProcessor.setDefinitionManager(definitionManager());
        return apiServiceBeanPostProcessor;
    }

    @Bean
    public Log4jFastJsonHttpMessageConverter  fastJsonHttpMessageConverter() {
        return fastJsonHttpMessageConverter;
    }

}
