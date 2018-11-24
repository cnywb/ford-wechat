package com.ford.wechat.batch.model;

import java.util.Date;

/**
 * Created by wanglijun on 16/7/3.
 */
public class DataFileVo extends AbstractData{
    /**文件名*/
    private String fileName;
    /**文件大小*/
    private Long size;
    /**文件创建时间*/
    private Date creationTime;
    /**最后访问文件的时间*/
    private Date lastAccessTime;
    /**最后修改文件的时间*/
    private Date lastModifiedTime;
    /**日批次号*/
    private String batchNo;
    /**文件批次号*/
    private String fileBatchNo;
    /**绝对路径*/
    private String absolutePath;
    /**导入日期*/
    private Date importDate;
    /**导入总数量*/
    private Long total=0L;
    /**导入成功量*/
    private Long success=0L;
    /**导入状态*/
    private String  status;
    /**数据的类型*/
    private String  dataType;
    /**CarOwner时间戳*/
    private String timestamp;
    /**导入序列*/
    private String sequence;

    public DataFileVo() {
        super();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFileBatchNo() {
        return fileBatchNo;
    }

    public void setFileBatchNo(String fileBatchNo) {
        this.fileBatchNo = fileBatchNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataFile{" +
                "absolutePath='" + absolutePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", size=" + size +
                ", creationTime=" + creationTime +
                ", lastAccessTime=" + lastAccessTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", batchNo='" + batchNo + '\'' +
                ", fileBatchNo='" + fileBatchNo + '\'' +
                ", importDate=" + importDate +
                ", total=" + total +
                ", success=" + success +
                ", status='" + status + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
