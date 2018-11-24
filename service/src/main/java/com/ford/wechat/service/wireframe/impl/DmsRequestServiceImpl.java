package com.ford.wechat.service.wireframe.impl;

import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsRequest;
import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsResponse;
import com.ford.wechat.service.wireframe.DmsRequestService;
import com.ford.wechat.service.wireframe.https.HttpsClientUtil;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.JSONUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lc on 2017/9/18.
 */
@Service
public class DmsRequestServiceImpl  implements DmsRequestService {


     public static Logger logger = LoggerFactory.getLogger(DmsRequestServiceImpl.class);

    @Value("${wireframe.request.dms.url}")
     private String url;

    @Value("${wireframe.request.dms.contentType}")
    private String contentType;


    @Override
    public WireframeDmsResponse sourceNumSyn(WireframeDmsRequest request) {
        return getWireframeDmsResponse(request);
    }

    @Override
    public WireframeDmsResponse orderRequest(WireframeDmsRequest request) {
        return getWireframeDmsResponse(request);
    }

    private WireframeDmsResponse getWireframeDmsResponse(WireframeDmsRequest request) {
        String requestJson = JSONUtil.objectToJson(request);
        String responseBody = HttpsClientUtil.doPost(url, requestJson, contentType);
        WireframeDmsResponse wireframeDmsResponse = JSONUtil.toObject(responseBody, WireframeDmsResponse.class);
        return wireframeDmsResponse;
    }
}
