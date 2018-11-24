package com.ford.wechat.controller.coupon;

import com.ford.wechat.controller.coupon.vo.*;
import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.excel.ExcelService;
import com.ford.wechat.service.user.FordClubMemberService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Controller
public class CouponController {

    @Autowired
    private CouponService service;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private CouponService couponService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "couponPage")
    public Page<CouponPageResp> page(CouponGetReq req) {
        String createStartDate = null;
        String createEndDate = null;
        if(req.getCreateEndDate() != null && req.getCreateStartDate() != null){
            createStartDate = req.getCreateStartDate().replaceAll("-","");
            createEndDate = req.getCreateEndDate().replaceAll("-","");
        }
        Page<Coupon> pages = service.pagingBy(req.getProjectCode(),req.getOpenId(),req.getVin(),req.getBatchStatus(),req.getStatus(),createStartDate,createEndDate,req.getPage());
        //Page<Coupon> pages = service.pagingBy (req.getPage ());
        Page<CouponPageResp> respS = pages.map (new Converter<Coupon, CouponPageResp>() {
            public CouponPageResp convert(Coupon source) {
                CouponPageResp resp = new CouponPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     *代金券列表导出
     */
    @RequestMapping(value = "couponListExport.ctl")
    public void resultExport(CouponGetReq req, HttpServletRequest request, HttpServletResponse response) {
        String createStartDate = null;
        String createEndDate = null;
        if(req.getCreateEndDate() != null && req.getCreateStartDate() != null){
            createStartDate = req.getCreateStartDate().replaceAll("-","");
            createEndDate = req.getCreateEndDate().replaceAll("-","");
        }
        GridPage page = new GridPage();
        page.setPageNumber(1);
        page.setPageSize(10000);

        Page<Coupon> pages = service.pagingBy(req.getProjectCode(),req.getOpenId(),req.getVin(),req.getBatchStatus(),req.getStatus(),createStartDate,createEndDate,page);
       //Page<Coupon> pages = service.pagingBy (page);
        Page<CouponPageResp> respS = pages.map (new Converter<Coupon, CouponPageResp>() {
            public CouponPageResp convert(Coupon source) {
                CouponPageResp resp = new CouponPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        excelService.lionExportData(respS.getContent(),"代金券管理列表","couponListExport", request, response);
    }

    //获取vin
    @ApiService(transCode = "vinEntityGet")
    public List<VinGetReq> vinEntityGet(VinHandleReq req) {

        List<VinGetReq> body=new ArrayList<>();
        List<FordClubMember> fordClubMembers = fordClubMemberService.findVinByOpenId(req.getOpenId());
        if (fordClubMembers.isEmpty()) {
            throw new BusinessException("500002", "未认证");
        }
        for (FordClubMember item : fordClubMembers) {
            VinGetReq vinGetReq = new VinGetReq();
            vinGetReq.setVin(item.getVvin());
            body.add(vinGetReq);
        }
        return body;
    }

    /**
     * 代金券绑定vin
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "drawVinHandle")
    public String handle(drawVinHandleReq req) {

        FordClubMember fordClubMember = this.fordClubMemberService.findBy(req.getOpenId(), req.getVin());

        if (fordClubMember == null) {
            throw new BusinessException("100006", "未认证");
        }
        String mobile = fordClubMember.getMobile();

        this.couponService.saveBackCouponVin(req.getDateNo(), req.getProjectCode(), req.getOpenId(), req.getCouponNo(), req.getVin(), mobile, fordClubMember.getDcrtDate());

        return "";
    }
    /**
     * 批量代金券绑定vin
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "fastBindCouponVin")
    public VinFastBindResp fastBind(CouponIDsReq req) {

        int success = 0;
        int failed = 0;
        for(String couponNo : req.getNos()) {
            Coupon coupon = couponService.findBy(null, null, null, couponNo, Coupon.STATUS_RECEIVING);

            if (coupon == null) {
                failed ++;
                continue;
            }

            List<FordClubMember> fordClubMembers = this.fordClubMemberService.findVinByOpenId(coupon.getOpenId());
            if (fordClubMembers.isEmpty() || fordClubMembers.size() > 1) {
                failed ++;
                continue;
            }

            try {
                FordClubMember fordClubMember = fordClubMembers.get(0);
                String mobile = fordClubMember.getMobile();
                this.couponService.saveBackCouponVin(coupon.getDateNo(), coupon.getProjectCode(), coupon.getOpenId(), coupon.getCouponNo(), fordClubMember.getVvin(), mobile, fordClubMember.getDcrtDate());
                success++;
                continue;
            } catch (BusinessException e) {
                failed++;
                continue;
            }
        }

        VinFastBindResp resp = new VinFastBindResp();
        resp.setSuccess(success);
        resp.setFailed(failed);

        return resp;
    }


}

