package com.ford.wechat.service.wireframe;

import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsRequest;
import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsResponse;

/**
 * Created by lc on 2017/9/18.
 */
public interface DmsRequestService {


    public WireframeDmsResponse sourceNumSyn(WireframeDmsRequest request);

    public WireframeDmsResponse orderRequest(WireframeDmsRequest request);
}
