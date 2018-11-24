package com.ford.wechat.controller.user;

import com.ford.wechat.controller.user.vo.*;
import com.ford.wechat.entity.user.UserInfo;
import com.ford.wechat.service.user.UserInfoService;
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
public class UserInfoController {

    @Autowired
    UserInfoService service;

    /**
     * 按关键字分页查询对象 调用userInfoPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "userInfoPage")
    public Page<UserInfoPageResp> userInfoPage(UserInfoPageReq req) {
        Page<UserInfo> pages = service.pagingBy (req.getOpenId(),req.getUserName(),req.getLicense(),req.getMobile(),req.getEmail(),req.getPage ());
        Page<UserInfoPageResp> respS = pages.map (new Converter<UserInfo, UserInfoPageResp> () {
            public UserInfoPageResp convert(UserInfo source) {
                UserInfoPageResp resp = new UserInfoPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用userInfoHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "userInfoHandle")
    public String userInfoHandle(UserInfoHandleReq req) {
        UserInfo entity = new UserInfo ();
        if (req.getId () != null) {
            entity = service.get (req.getOpenId ());
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
     * 删除对象处理,批量,单一删除均支持 调用userInfoRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "userInfoRemove")
    public void userInfoRemove(UserInfoRemoveReq req) {
//        for (UserInfoRemoveVo vo : req.getDataVo ()) {
//            service.delete (vo.getId ());
//        }
    }
}