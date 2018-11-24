package com.ford.wechat.service.pageinterface;

import com.ford.wechat.entity.pageinterface.IsVerifyOwnerReq;
import com.ford.wechat.entity.pageinterface.IsVerifyOwnerRes;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeReq;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeRes;

/**
 * Created by huangwe on 2017/06/26.
 */
public interface PageInterfaceService {

    /**
     *  验证是否是认证车主
     * @param open_id
     * @return
     */
	public  IsVerifyOwnerRes vaildIsVerifyOwner(IsVerifyOwnerReq req);
		

    /**
     *  根据相关车主信息返回二维码路径
     * @param 
     * @return
     */
	public  OwnerqrCodeRes saveOwnerqrCodeUrl(OwnerqrCodeReq req);
		
}
