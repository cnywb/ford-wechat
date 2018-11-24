package com.ford.wechat.batch.item.processor.verify.dealer;/**
 * Created by jovi on 26/03/2017.
 */

import com.ford.wechat.batch.model.verify.DealerVerify;
import com.ford.wechat.entity.user.FordClubMember;
import org.springframework.batch.item.ItemProcessor;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-26 15:14
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class DealerVerifyProcessor implements ItemProcessor<FordClubMember,DealerVerify> {

    @Override
    public DealerVerify process(FordClubMember fordClubMember) throws Exception {
        DealerVerify verify = new DealerVerify();
        verify.setVin(fordClubMember.getVvin());
        return verify;
    }
}
