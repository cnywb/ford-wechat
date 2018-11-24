/**
 * Copyright (c)  2016.
 * All rights reserved.
 * AppConfig.java 16/8/31 18:27
 */
package com.ford.wechat.pc.configuration;

import com.ford.wechat.pc.interceptor.AuthenticationExternalInterceptor;
import com.ford.wechat.pc.interceptor.AuthenticationInterceptor;
import io.dabing.core.web.config.ApiServiceBeanPostProcessor;
import io.dabing.core.web.manager.ApiServiceDefinitionManager;
import io.dabing.core.web.manager.DefaultApiServiceDefinitionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 应用程序启动配置
 */
@Configuration

public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
    private static DefaultApiServiceDefinitionManager definitionManager = new DefaultApiServiceDefinitionManager();

    private static ApiServiceBeanPostProcessor apiServiceBeanPostProcessor = new ApiServiceBeanPostProcessor();

    private static Log4jFastJsonHttpMessageConverter fastJsonHttpMessageConverter = new Log4jFastJsonHttpMessageConverter();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //临时允许跨域访问
        //registry.addInterceptor(new CrosAccessInterceptor());
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/api/private/**").excludePathPatterns("/api/public/**", "");
        registry.addInterceptor(new AuthenticationExternalInterceptor())
                .addPathPatterns("/api/external/**");

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
    public Log4jFastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        return fastJsonHttpMessageConverter;
    }

    //自定义cookie名称
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("MOBILEAPISESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }
}
