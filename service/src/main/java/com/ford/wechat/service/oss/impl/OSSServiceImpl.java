/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * OSSServiceImpl.java
 */

package com.ford.wechat.service.oss.impl;

import com.aliyun.oss.OSSClient;
import com.ford.wechat.service.oss.OSSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

/**
 * 描述: 阿里云OSS 链接实现
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class OSSServiceImpl implements OSSService {

    @Value(value = "${oss.bucket}")
    private String bucket;
    @Value(value = "${oss.schema}")
    private String schema;
    @Value(value = "${oss.endpoint.external}")
    private String endpoint;
    @Value(value = "${oss.endpoint.internal}")
    private String endpointInternal;
    @Value(value = "${oss.accessKeyId}")
    private String accessKeyId;
    @Value(value = "${oss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 文件上传,并返回url地址
     *
     * @param file 文件字节流
     * @param key  文件名称
     * @return 外网访问URL
     */
    public String put(byte[] file, String key) {
        OSSClient client = new OSSClient(schema + endpointInternal, accessKeyId, accessKeySecret);
        client.putObject(bucket, key, new ByteArrayInputStream(file));
        client.shutdown();
        return schema + bucket + "." + endpoint + "/" + key;
    }

    @Override
    public void remove(String key) {
        OSSClient client = new OSSClient(schema + endpointInternal, accessKeyId, accessKeySecret);
        client.deleteObject(bucket, key);
        client.shutdown();
    }
}