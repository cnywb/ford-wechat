/*
 * Copyright (c)  2016, 
 * All rights reserved.
 * ExcelImportResult.java 2016-05-13 下午4:42
 */

package com.ford.wechat.service.excel.impl;

import java.util.List;

/**
 * 描述:Excel文件导入结果
 *
 * @author yangkui create on 2016-05-13.
 * @since 1.0
 */
public class ExcelImportResult {

    private int count;

    private int successCount;

    private List<? extends ExcelImportBase> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public List<? extends ExcelImportBase> getResult() {
        return result;
    }

    public void setResult(List<? extends ExcelImportBase> result) {
        this.result = result;
    }
}