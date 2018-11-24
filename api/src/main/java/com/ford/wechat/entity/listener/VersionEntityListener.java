package com.ford.wechat.entity.listener;


import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.VersionEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by wanglijun on 16/5/7.
 */
public class VersionEntityListener  extends  AbstractEntityListener{


    @PrePersist
    public void prePersist(AuditEntity entity) {
        this.setEntity (entity);
    }

    @PreUpdate
    public void preUpdate(VersionEntity entity) {
        Date updatedDate = new Date();
        entity.setUpdatedDate(updatedDate);
        //TODO
        entity.setCreatedById(1L);
        if(entity.getDeleted()){
            entity.setDeleteDate(updatedDate);
        }
    }
}
