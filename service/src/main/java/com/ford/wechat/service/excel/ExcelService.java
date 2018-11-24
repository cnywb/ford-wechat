/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * ExcelDetail.java 2016-05-12 15:03:01
 */
package com.ford.wechat.service.excel;


import com.ford.wechat.service.excel.impl.ExcelImportBase;
import com.ford.wechat.service.excel.impl.ExcelImportResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by ziv.hung on 2016-05-12 15:03:01 .
 *
 * @since 1.0
 */
public interface ExcelService {


    /**
     * 解析导入Excel数据 返回指定对象的数据集合
     *
     * @param businessCode    导入业务代码
     * @param stream 导入文件流
     * @param clazz           需要转换成何种对象
     * @return 对象几何数据
     */
    ExcelImportResult importData(String businessCode, InputStream stream, Class<? extends ExcelImportBase> clazz);

    /**
     * 导出excel文件,直接返回给浏览器
     *
     * @param businessCode 导入业务代码
     */
    void exportData(String businessCode, HttpServletRequest request, HttpServletResponse httpServletResponse);
    /**
     * 导出excel文件,直接返回给浏览器(lion)
     *
     * @param businessCode 导入业务代码
     */
    public void lionExportData(List<?> data, String businessCode, HttpServletRequest request, HttpServletResponse httpServletResponse) ;

    /**
     * 导出excel文件,直接返回给浏览器(lion)
     *
     * @param businessCode 导入业务代码
     */
    public void lionExportData(List<?> data, String fileName, String businessCode, HttpServletRequest request, HttpServletResponse httpServletResponse) ;

    /**
     * 导出excel文件,直接返回给浏览器(lion)
     *
     * @param fileName 文件名称
     */
    public void lionExportDataByFile(Map<String, List<?>> data, String fileName, HttpServletRequest request, HttpServletResponse httpServletResponse) ;
    /**
     * 导出excel文件,直接写入文件至path中
     *
     * @param data 文件名称
     *
     * @param businessCode 文件名称
     *
     * @param path 文件名称
     */
    public void lionExportData(List<?> data,String businessCode, String path);
}