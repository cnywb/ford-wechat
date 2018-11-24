package com.ford.wechat.entity.listener;


import com.ford.wechat.entity.AuditEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author li.yu
 * @since 1.0
 */
public class AuditEntityListener extends  AbstractEntityListener {


    @PrePersist
    public void prePersist(AuditEntity entity) {
        Date createdDate = new Date();
        entity.setCreatedById(1L);
        entity.setCreatedDate(createdDate);
    }

    @PreUpdate
    public void preUpdate(AuditEntity entity) {
        Date updatedDate = new Date();
        entity.setUpdatedDate(updatedDate);
    }

}