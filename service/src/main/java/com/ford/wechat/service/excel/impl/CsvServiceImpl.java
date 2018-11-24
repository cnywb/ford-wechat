/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * CsvServiceImpl.java
 */

package com.ford.wechat.service.excel.impl;


import com.ford.wechat.entity.excel.ExcelDetail;
import com.ford.wechat.entity.excel.ExcelList;
import com.ford.wechat.service.excel.CsvService;
import com.ford.wechat.service.excel.ExcelDetailService;
import com.ford.wechat.service.excel.ExcelListService;
import io.dabing.common.Assert;
import io.dabing.exception.BusinessException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * 描述:TODO
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class CsvServiceImpl implements CsvService {

    private Logger logger = LoggerFactory.getLogger(CsvServiceImpl.class);

    @Autowired
    ExcelListService excelListService;

    @Autowired
    ExcelDetailService excelDetailService;

    /**
     * 导出excel文件,直接返回给浏览器
     *
     * @param businessCode 导入业务代码
     * @param response
     */
    @Override
    public void exportData(List<?> exportData, String fileName, String businessCode, HttpServletResponse response) {

        //校验数据完整性
        ExcelList excelList = excelListService.findBy(businessCode, ExcelList.TYPE_EXPORT);
        response.setCharacterEncoding("GBK");
        Assert.notNull(excelList, "【校验不通过】原因:未找到本模块的导入配置信息");
        //根据业务模块代码 获取对应excel到model的配置信息
        List<ExcelDetail> excelDetailList = excelDetailService.findBy(excelList.getId());
        if (excelDetailList != null && excelDetailList.size() == 0) {
            logger.error("【校验不通过】原因 未找到表头映射配置,模块代码{},描述{},ID{}", new Object[]{excelList.getBusinessCode(), excelList.getBusinessDesc(), excelList.getId()});
            throw new BusinessException("【校验不通过】原因:未找到本模块的导入excel表头映射配置信息");
        }
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".csv");
            OutputStream outputStream = response.getOutputStream();
//定义文件名格式并创建
            // UTF-8使正确读取分隔符"," 8192
            BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
            // 写入文件头部
            for (ExcelDetail excelDetail : excelDetailList) {
                csvFileOutputStream.write(excelDetail.getExcelCell()+"");
                csvFileOutputStream.write(",");
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            Iterator<?> it = exportData.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                for (int i = 0; i < excelDetailList.size(); i++) {
                    ExcelDetail excelDetail = excelDetailList.get(i);
                    String fieldName = excelDetail.getTableColumn();
                    Object value = getProperty(obj, fieldName);
                    csvFileOutputStream.write(value + ",");
                }
                csvFileOutputStream.newLine();
            }
            csvFileOutputStream.flush();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Assert.isTrue(false, "生成CVS文件异常！");
            logger.error("生成CVS文件异常！", e);
        }

    }

    /**
     * 导出excel文件,直接返回给浏览器
     *
     * @param businessCode 导入业务代码
     * @param csvFilePath
     */
    @Override
    public void exportData(List<?> exportData, String businessCode,String csvFilePath) {

        //校验数据完整性
        ExcelList excelList = excelListService.findBy(businessCode, ExcelList.TYPE_EXPORT);
        Assert.notNull(excelList, "【校验不通过】原因:未找到本模块的导入配置信息");
        //根据业务模块代码 获取对应excel到model的配置信息
        List<ExcelDetail> excelDetailList = excelDetailService.findBy(excelList.getId());
        if (excelDetailList != null && excelDetailList.size() == 0) {
            logger.error("【校验不通过】原因 未找到表头映射配置,模块代码{},描述{},ID{}", new Object[]{excelList.getBusinessCode(), excelList.getBusinessDesc(), excelList.getId()});
            throw new BusinessException("【校验不通过】原因:未找到本模块的导入excel表头映射配置信息");
        }
        try {
            File file = new File(csvFilePath);
            if (!file.exists()) {
                FileUtils.forceMkdir(file);
            }
            //定义文件名格式并创建
//            File csvFile = new File(csvFilePath+"oldAuthData.csv");
            File csvFile = File.createTempFile(excelList.getFileName(), ".csv", new File(csvFilePath));
            System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符"," 8192
            BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);
            //定义文件名格式并创建

            // 写入文件头部
            for (ExcelDetail excelDetail : excelDetailList) {
                csvFileOutputStream.write(excelDetail.getExcelCell()+"");
                csvFileOutputStream.write(",");
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            Iterator<?> it = exportData.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                for (int i = 0; i < excelDetailList.size(); i++) {
                    ExcelDetail excelDetail = excelDetailList.get(i);
                    String fieldName = excelDetail.getTableColumn();
                    Object value = getProperty(obj, fieldName);
                    csvFileOutputStream.write(value + ",");
                }
                csvFileOutputStream.newLine();
            }
            csvFileOutputStream.flush();

        } catch (Exception e) {
            Assert.isTrue(false, "生成CVS文件异常！");
            logger.error("生成CVS文件异常！", e);
        }

    }

    /***
     * 根据对象获取字段的值，获取Value
     * @param obj 对象
     * @return Object的Value
     */
    private Object getProperty(Object obj, String fieldName) {
        Object value = null;
        try {
            value = PropertyUtils.getProperty(obj, fieldName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

}