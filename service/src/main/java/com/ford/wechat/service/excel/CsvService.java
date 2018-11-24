/**
 * Copyright (c)  2017, xiaosheep.com
 * All rights reserved.
 * CsvService.java 17/3/2
 */
package com.ford.wechat.service.excel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述:CsvService
 *
 * @author ziv.hung create on 17/3/2
 * @since 1.0
 */
public interface CsvService {

    /**
     * 导出excel文件,直接返回给浏览器
     *
     * @param businessCode 导入业务代码
     */
    void exportData(List<?> exportData, String fileName, String businessCode, HttpServletResponse response);

    /**
     *
     * @param exportData
     * @param fileName
     * @param businessCode
     * @param path
     */
    public void exportData(List<?> exportData, String businessCode, String path);
}
