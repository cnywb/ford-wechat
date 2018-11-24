package com.ford.wechat.controller.batch.vo;

import com.ford.wechat.entity.batch.BatchType;
import lombok.Data;
import org.springframework.batch.core.BatchStatus;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/27.
 */
@Data
public class BatchTaskPageResp {

    private Long id;

    /**
     * 状态 1 待处理 2 处理中 3 已处理
     */
    private Integer status;
    /**
     * 参数
     */
    private String params;
    /**
     * 备注
     */
    private String remark;

    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 数据类型
     */
    private BatchType batchType;

    /**数据类型名称*/
    private String dataTypeName;

    /**
     * 业务编号
     */
    private Long businessId;

    /***
     * 执行开始时间
     */
    private Date startDate;
    /***
     * 执行结束时间
     */
    private Date endDate;

    /***
     * 执行状态
     */
    private BatchStatus executeStatus;

    /***
     * 批次处理那一日的数据
     */
    private Date batchDate;

    /***
     * 批处理执行次数  业务 处理3次及以上，任务则认为 失败
     */
    private Integer runTimes;

    /***
     * 批处理执行结果
     */
    private String batchResult;

    /**
     * 重置参数 每次执行需要更新，取时间值
     */
    private String resetParameter;
}
