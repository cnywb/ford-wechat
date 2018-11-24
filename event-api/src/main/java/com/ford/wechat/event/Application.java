/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * Application.java
 */

package com.ford.wechat.event;

import io.dabing.service.cache.impl.ApplicationCacheManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述:Application 应用启动类
 *
 * @author Neel create on 17/8/31
 * @since 1.0
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext-redis.xml")
@ComponentScan(value = {"com.ford","io.dabing"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {ApplicationCacheManagerImpl.class})})
@EntityScan(value = {"com.ford.wechat.entity"})
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
