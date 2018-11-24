package com.ford.wechat.entity.hub;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="WE_REDIRECTION")
public class Redirection extends AuditEntity{

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_REDIRECTION")
    @SequenceGenerator(name = "SEQ_WE_REDIRECTION",allocationSize=1,sequenceName = "SEQ_WE_REDIRECTION")
    private Long id;


    /** 微信入口标识参数*/
    @Column(name="STATE")
    private String state;

    /**微信入口标识参数  问卷项目ID*/
//    @Column(name="CHANNEL")
//    private String channel;

    /**授权跳转的URL，带有openid参数*/

    @Column(name="URL")
    private String url;

    /**备注说明*/
    @Column(name="REMARK")
    private String remark;

}
