/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * ExceptionHandler.java 2016-09-07 下午9:06
 */

package com.ford.wechat.pc.interceptor;

import com.ford.wechat.pc.common.response.Response;
import io.dabing.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

/**
 * 描述:controller类所有接口异常捕获,返回给前端采用统一的JSON格式
 *
 * @author yangkui create on 2016-09-07.
 * @since 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class); //日志记录器

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleBusinessException(BusinessException e) throws IOException {
        //记录日志
        LOGGER.error(e.getMessage(), e);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
    }



    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleIllegalArgumentExceptions(final Exception e, final WebRequest req) {
        //记录日志
        LOGGER.error(e.getMessage(), e);
        return new Response<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    /**
     * 全局处理Exception
     * 错误的情况下返回500
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleOtherExceptions(final Exception e, final WebRequest req) {
        //记录日志
        LOGGER.error(e.getMessage(), e);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求异常", null);
    }
}