package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "BBS_USER_ONLINE")
public class JbUserOnline implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 8630642907435482114L;

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ONLINE_LATEST")
    private Double onlineLatest = 0.00;

    @Column(name = "ONLINE_DAY")
    private Double onlineDay = 0.00;

    @Column(name = "ONLINE_WEEK")
    private Double onlineWeek = 0.00;

    @Column(name = "ONLINE_MONTH")
    private Double onlineMonth = 0.00;

    @Column(name = "ONLINE_YEAR")
    private Double onlineYear = 0.00;

    @Column(name = "ONLINE_TOTAL")
    private Double onlineTotal = 0.00;

}
