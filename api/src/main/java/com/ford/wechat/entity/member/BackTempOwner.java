package com.ford.wechat.entity.member;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Neel on 2017/5/24.
 */
@Data
@Entity
@Table(name = "BACK_TEMP_OWNER")
public class BackTempOwner {
    // Fields
    @Id
    @Column(name = "OWNER_ID", unique = true, nullable = false, length = 60)
    private String ownerId;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "MOBILE")
    private String mobile;

}
