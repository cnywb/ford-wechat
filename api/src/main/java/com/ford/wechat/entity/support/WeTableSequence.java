package com.ford.wechat.entity.support;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/6/1.
 */

@Data
@Entity
@Table(name = "WE_TABLE_SEQUENCE")
public class WeTableSequence extends AuditEntity {

    /** 物理主键 */
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_TABLE_SEQUENCE")
    @SequenceGenerator(name = "SEQ_WE_TABLE_SEQUENCE",allocationSize=1,sequenceName = "SEQ_WE_TABLE_SEQUENCE")
    protected Long id;
    /** 表名 */
    @Column(name = "TABLE_NAME")
    private String tableName;

    /** SEQ 值 */
    @Column(name = "SEQ_VALUE")
    private Long seqValue;

    /** 业务描述 */
    @Column(name = "DESC_")
    private String desc;

    /**
     * 当前日期.
     */
    @Column(name = "CURRENT_DATE")
    private Date currentDate;


}
