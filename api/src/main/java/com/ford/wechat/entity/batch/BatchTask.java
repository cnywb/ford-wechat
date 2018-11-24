package com.ford.wechat.entity.batch;/**
 * Created by jovi on 23/03/2017.
 */

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;
import org.springframework.batch.core.BatchStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-23 20:16
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "WE_BATCH_TASK")
public class BatchTask extends VersionEntity {
    /**
     * 待处理
     */
    public static final Integer STATUS_INIT = 1;

    /**
     * 处理中
     */
    public static final Integer STATUS_HANDLING = 2;

    /**
     * 已处理
     */
    public static final Integer STATUS_FINISHED = 3;

    /**
     * 失败
     */
    public static final Integer STATUS_FAIL = 4;

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_BATCH_TASK_ID")
    @SequenceGenerator(name = "SEQ_WE_BATCH_TASK_ID", sequenceName = "SEQ_WE_BATCH_TASK_ID")
    private Long id;

    /**
     * 状态 1 待处理 2 处理中 3 已处理 4 失败
     */
    @Column(name = "STATUS")
    private Integer status;
    /**
     * 参数
     */
    @Column(name = "PARAMS")
    private String params;
    /**
     * 备注
     */
    @Column(name = "TASK_REMARK")
    private String remark;

    /**
     * 批次号
     */
    @Column(name = "BATCH_NO")
    private String batchNo;
    /**
     * 数据类型
     */
    @Column(name = "DATA_TYPE")
    @Enumerated(EnumType.STRING)
    private BatchType batchType;
    /**
     * 业务编号
     */
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    /***
     * 执行开始时间
     */
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    /***
     * 执行结束时间
     */
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /***
     * 执行状态
     */
    @Column(name = "BATCH_STATUS")
    @Enumerated(EnumType.STRING)
    private BatchStatus executeStatus;

    /***
     * 批次处理那一日的数据
     */
    @Column(name = "BATCH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date batchDate;

    /***
     * 批处理执行次数  业务 处理3次及以上，任务则认为 失败
     */
    @Column(name = "RUN_TIMES")
    private Integer runTimes;

    /***
     * 批处理执行结果
     */
    @Column(name = "BATCH_RESULT")
    private String batchResult;

    /**
     * 重置参数 每次执行需要更新，取时间值
     */
    @Column(name = "RESET_PARAMETER")
    private String resetParameter;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
