package com.ford.wechat.respository.user;


import com.ford.wechat.entity.user.DmsRepairOrder;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-07-17 16:42
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface DmsRepairOrderRepository extends JpaRepository<DmsRepairOrder, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<DmsRepairOrder> pagingBy(GridPage page);

    /**
     * 根据vin码查询
     * @param vin
     * @return
     */
    public List<DmsRepairOrder> findByVin(String vin);

}
