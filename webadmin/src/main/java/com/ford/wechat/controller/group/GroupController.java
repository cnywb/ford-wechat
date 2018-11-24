package com.ford.wechat.controller.group;

import com.ford.wechat.controller.group.vo.*;
import com.ford.wechat.entity.group.Group;
import com.ford.wechat.service.group.GroupService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;


/**
 * Created by wanglijun on 2016-11-02.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class GroupController {

    @Autowired
    GroupService service;

    /**
     * 按关键字分页查询对象 调用groupPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "groupPage")
    public Page<GroupPageResp> groupPage(GroupPageReq req) {
        Page<Group> pages = service.pagingBy (req.getOpenId(),req.getName(),req.getMobile(),req.getFavourCarName(),req.getDealer(),req.getAppLinkName(),req.getBuyStartDate(),req.getBuyEndDate(),req.getPage ());
        Page<GroupPageResp> respS = pages.map (new Converter<Group, GroupPageResp> () {
            public GroupPageResp convert(Group source) {
                GroupPageResp resp = new GroupPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用groupHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "groupHandle")
    public String groupHandle(GroupHandleReq req) {
        Group entity = new Group ();
        if (req.getId () != null) {
            entity = service.get (req.getId ());
        }
        BeanUtils.copyProperties (req, entity);
        if (req.getId () != null) {
            service.update (entity);
        } else {
            service.save (entity);
        }
        return "";
    }

    /**
     * 删除对象处理,批量,单一删除均支持 调用groupRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "groupRemove")
    public void groupRemove(GroupRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }
}