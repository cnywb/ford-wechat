package com.ford.wechat.entity.security;


import com.ford.wechat.entity.AuditEntity;

import javax.persistence.*;

/**
 * Created by kui.yang on 2014/6/8.
 * 角色表
 */
@Entity
@Table(name = "WE_ROLE")
public class BasRole extends AuditEntity {
    /** 物理主键 */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "SEQ_WE_ROLE_ID")
    @SequenceGenerator(name = "SEQ_WE_ROLE_ID",allocationSize=1,sequenceName = "SEQ_WE_ROLE_ID")
    protected Long id;
    /**
     * 角色代码
     */
    @Column(name = "ROLE_CODE", nullable = false)
    private String code;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME", nullable = false)
    private String name;

    /**
     * 备注
     */
    @Column(name = "REMARK", length = 100)
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
