/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ApiApplication.java
 */

package com.ford.wechat.pc;

import io.dabing.service.cache.impl.ApplicationCacheManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述:ApiApplication 应用启动类
 *
 * @author ziv create on 16/11/13
 * @since 1.0
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext-redis.xml")
//排除默认的ehcacheManager服务，避免和redis缓存冲突
@ComponentScan(value = {"com.ford","io.dabing"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {ApplicationCacheManagerImpl.class})})
@EntityScan(value = {"com.ford.wechat.entity"})
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
