package com.ford.wechat.respository.dict;


import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.entity.dict.FcDmsDictionary;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-08-14 21:43
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FcDmsDictionaryRepository extends JpaRepository<FcDmsDictionary, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<FcDmsDictionary> pagingBy(GridPage page);

    /**
     * 根据代码查询
     *
     * @param code 代码
     * @return
     */
    FcDmsDictionary findByCode(String code);

}
