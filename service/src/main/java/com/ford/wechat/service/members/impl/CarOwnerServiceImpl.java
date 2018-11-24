package com.ford.wechat.service.members.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ford.wechat.Constant;
import com.ford.wechat.entity.elec.ElecListResponseBody;
import com.ford.wechat.entity.repair.HistoryDetailResponseBody;
import com.ford.wechat.entity.repair.HistoryResponseBody;
import com.ford.wechat.service.members.CarOwnerService;
import com.ford.wechat.service.token.AccessTokenService;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wanglijun on 16/11/6.
 */
@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    @Autowired
    private AccessTokenService accessTokenService;
    private static final Logger logger = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

    private static Map<String, Integer> models = new HashMap<>();

    static {
        models.put("新嘉年华", 180);
        models.put("福睿斯", 183);
        models.put("新福克斯", 184);
        models.put("新蒙迪欧", 185);
        models.put("致胜", 186);
        models.put("S-MAX", 187);
        models.put("全新福克斯", 188);
        models.put("金牛座", 189);
        models.put("翼博", 190);
        models.put("翼虎", 191);
        models.put("锐界", 192);
        models.put("福克斯", 182);

    }

    /**
     * 车主认证和绑定，返回状态值
     *
     * @param name
     * @param mobile
     * @param vincode
     * @param openid
     * @param rzbd_state
     * @return
     */
    @Override
    public String carOwnerAuth(String name, String mobile, String vincode, String openid, String rzbd_state) {
        String owner_auth_url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "owner_auth_url");
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("name", name);
        jsonMap.put("mobile", mobile);
        jsonMap.put("vin", vincode);
        jsonMap.put("wechatUsername", openid);
        jsonMap.put("rzbd_state", rzbd_state);//1表示认证，2表示绑定

        try {
            String json = HttpClientUtil.post(owner_auth_url, jsonMap, 30000, 30000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("code") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                if (!CollectionUtils.isEmpty (map)&&"1".equals (map.get ("code"))) {
                    aotuGroup(openid, map.get("model"));
                }
                return map.get("code");
            }
        } catch (Exception ioe) {
            logger.info(ioe.getMessage(), ioe);
        }
        return "-1";
    }

    @Override
    public HistoryResponseBody getRepairList(String openid) {
        HistoryResponseBody body = null;
        try {
            String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "repair_list_url");
            Map<String, String> jsonMap = new HashMap<String, String>();
            jsonMap.put("wechatName", openid);
            String json = HttpClientUtil.post(url, jsonMap, 1200000, 1200000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("0000000") >= 0)
                body = JSON.parseObject(json, HistoryResponseBody.class);
        } catch (Exception ex) {

        }
        return body;
    }

    @Override
    public HistoryDetailResponseBody getRepairDetail(String openid, String billid) {
        HistoryDetailResponseBody body = null;
        try {
            String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "repair_details_url");
            Map<String, String> jsonMap = new HashMap<String, String>();
            jsonMap.put("wechatName", openid);
            jsonMap.put("vrepairId", billid);
            String json = HttpClientUtil.post(url, jsonMap, 1200000, 1200000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("0000000") >= 0)
                body = JSON.parseObject(json, HistoryDetailResponseBody.class);
        } catch (Exception ex) {

        }
        return body;
    }

    @Override
    public ElecListResponseBody getElec(String openid) {
        ElecListResponseBody body = null;
        try {
            String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "elec_details_url");
            Map<String, String> jsonMap = new HashMap<String, String>();
            jsonMap.put("wechatName", openid);
            //String content = JSON.toJSONString(jsonMap);
            String json = HttpClientUtil.post(url, jsonMap, 1200000, 1200000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("0000000") >= 0)
                body = JSON.parseObject(json, ElecListResponseBody.class);
        } catch (Exception ex) {

        }
        return body;
    }

    private void aotuGroup(String openid, String model) {
        Iterator<String> ite = models.keySet().iterator();
        int groupid = 193;
        while (ite.hasNext()) {
            String key = ite.next();
            if (model.indexOf(key) >= 0)
                groupid = models.get(key);
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("openid", openid);
        jsonMap.put("to_groupid", groupid);
        String content = JSON.toJSONString(jsonMap);
        try {
            String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "group_touser_url") + accessTokenService.getAccessToken();
            HttpClientUtil.postJSON(url, content);
        } catch (Exception ex) {

        }
    }




}
