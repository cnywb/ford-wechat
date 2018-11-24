package com.ford.wechat.batch.model;

/**
 * 查询统计结果
 * Created by wanglijun on 16/7/4.
 */
public class DataFileStatistic {

    /**文件批次号*/
    private String fileBatchNo;
    /**总数量*/
    private Long total=0L;


    public DataFileStatistic() {
    }

    public DataFileStatistic(String fileBatchNo, Long total) {
        this.fileBatchNo = fileBatchNo;
        this.total = total;
    }

    public String getFileBatchNo() {
        return fileBatchNo;
    }

    public void setFileBatchNo(String fileBatchNo) {
        this.fileBatchNo = fileBatchNo;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "DataCarOwnerStatistic{" +
                "fileBatchNo='" + fileBatchNo + '\'' +
                ", total=" + total +
                '}';
    }
}
