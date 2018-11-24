package com.ford.wechat.entity.license;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "WE_LICENSE_IMAGE")
public class WeLicenseImage extends VersionEntity {

    @Id
    @GeneratedValue(generator="SEQUENCE",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName="SEQ_WE_LICENSE_IMAGE", allocationSize = 1, name ="SEQUENCE")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    /** vin码 */
    @Column(name = "VIN")
    private String vin;

    /** 行驶证识别车主姓名 */
    @Column(name="NAME")
    private String name;

    /** 微信唯一标识 */
    @Column(name="OPEN_ID")
    private String openId;

    /** 会员号 */
    @Column(name="MEMBER_NO")
    private String memberNo;

    /** 车牌号 */
    @Column(name="PLATE_NO")
    private String plateNo;

    /** 扫码结果(json) */
    @Column(name="RESULT")
    private String result;

    /** 行驶证图片地址 */
    @Column(name="IMAGE_URL")
    private String imageUrl;

    /** FORD_CLUB_MEMBER主键 */
    @Column(name="VCARD_ID")
    private String cardId;

    /** 上传类型(1.认证   2.解绑) */
    @Column(name="TYPE")
    private Long type;

}
