package com.ford.wechat.entity.factory;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "WE_FACTORY_FORM")
public class WeFactoryForm extends VersionEntity {

    @Id
    @GeneratedValue(generator="SEQUENCE",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName="S_WE_FACTORY_FORM", allocationSize = 1, name ="SEQUENCE")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "VIN")
    private String vin;

    @Column(name="BATCH_NO")
    private String batchNo ;

    @Column(name="DATE_NO")
    private String dateNo ;

    @Column(name="FILE_NAME")
    private String fileName ;

}
