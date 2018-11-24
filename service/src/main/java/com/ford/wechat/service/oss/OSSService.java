/**
 * Copyright (c) dabing.io
 * All rights reserved.
 * OSSService.java
 */
package com.ford.wechat.service.oss;

/**
 * 描述:OSSService 阿里云OSS 服务接口
 *
 * @author ziv
 * @since 1.0
 */
public interface OSSService {

    String put(byte[] file, String key);

    void remove(String key);
}
