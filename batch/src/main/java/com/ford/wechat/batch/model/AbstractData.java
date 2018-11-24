package com.ford.wechat.batch.model;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Created by wanglijun on 16/7/12.
 */
public abstract class AbstractData {


    /**文件名*/
    @XStreamOmitField
    protected String  fileName;
    /***文件批处号*/
    @XStreamOmitField
    protected String  fileBatchNo;
    /**批次号*/
    @XStreamOmitField
    protected String batchNo;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }


    public String getFileBatchNo() {
        return fileBatchNo;
    }

    public void setFileBatchNo(String fileBatchNo) {
        this.fileBatchNo = fileBatchNo;
    }
}
