/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ExcelCellVal.java
 */

package com.ford.wechat.batch.job;

/**
 * 描述: excel数据单元格数据信息
 *
 * @author ziv
 * @since 1.0
 */
public class ExcelCellVal {
    public static final String TYPE_DOUBLE = "double";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_DATE = "date";
    private String dataType;
    private Object data;

    public ExcelCellVal(String dataType, Object data) {
        this.dataType = dataType;
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}