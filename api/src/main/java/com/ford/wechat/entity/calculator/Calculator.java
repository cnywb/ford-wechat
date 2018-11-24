package com.ford.wechat.entity.calculator;

import com.ford.wechat.entity.AuditEntity;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Created by wanglijun on 16/11/18.
 */
@Entity
@Table(name = "P_MAIN_CALC")
public class Calculator {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SERIES")
    private String carType;

    @Column(name = "MILEAGE")
    private String licType;

    @Column(name = "PROJECT")
    private String project;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "DESCRIPTION")
    private String result;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "MONTH")
    private Long month;

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLicType() {
        return licType;
    }

    public void setLicType(String licType) {
        this.licType = licType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }
}
