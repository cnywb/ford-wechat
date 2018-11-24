package com.ford.wechat.web.members;

import com.ford.wechat.entity.elec.ElecListResponseBody;
import com.ford.wechat.entity.repair.CarInfoList;
import com.ford.wechat.entity.repair.CarRepairs;
import com.ford.wechat.entity.repair.HistoryDetailResponseBody;
import com.ford.wechat.entity.repair.HistoryResponseBody;
import com.ford.wechat.entity.user.CarInfo;
import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.entity.user.UserInfo;
import com.ford.wechat.service.members.CarOwnerService;
import com.ford.wechat.service.user.CarInfoService;
import com.ford.wechat.service.user.CarOwnerAuthenStatusService;
import com.ford.wechat.service.user.JoUserService;
import com.ford.wechat.service.user.UserInfoService;
import com.ford.wechat.web.auth.AuthController;
import com.ford.wechat.web.members.vo.CarInfoVo;
import com.ford.wechat.web.members.vo.CarOwnerHandleReq;
import com.ford.wechat.web.members.vo.UserInfoVo;
import io.dabing.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglijun on 16/11/6.
 */
@Controller
@RequestMapping("/fordwechat")
public class MembersController {

    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(MembersController.class);

    @Autowired
    private CarOwnerService service;
    @Autowired
    private JoUserService joUserService;
    @Autowired
    private CarOwnerAuthenStatusService carOwnerAuthenStatusService;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 车主认证
     *
     * @param req
     * @return
     */
    @RequestMapping("/carownerauth.do")
    @ResponseBody
    public String carOwnerAuth(CarOwnerHandleReq req) {
        if (joUserService.getCountByWechatId(req.getOpenid()) > 0) {
            return "110";
        }
        if (req.getSubcount() >= 3)
            return "119";
        String flag = service.carOwnerAuth(req.getName(), req.getMobile(), req.getVincode(), req.getOpenid(), "1");//1表示认证，2表示绑定
        if (flag.equals("1") && StringUtils.isNotEmpty(req.getDealercode())) {
            JoUser user = joUserService.getJoUserByWechatId(req.getOpenid());
            if (null != user) {
                user.setDealerCode(req.getDealercode());
                joUserService.update(user);//update
            }
        }
        return flag;
    }

    /**
     * 车主绑定
     *
     * @param req
     * @return
     */
    @RequestMapping("/carownerbind.do")
    @ResponseBody
    public String carOwnerBind(CarOwnerHandleReq req) {
        if (req.getSubcount() >= 3)
            return "119";
        return service.carOwnerAuth(req.getName(), req.getMobile(), req.getVincode(), req.getOpenid(), "2");//1表示认证，2表示绑定
    }

    @RequestMapping("/checkmember.do")
    @ResponseBody
    public int checkMember(String openid) {
        return joUserService.getCountByWechatId(openid);
    }

    @RequestMapping("/repairlist.do")
    @ResponseBody
    public HistoryResponseBody getRepairList(String openid) {

        return service.getRepairList(openid);
    }

    @RequestMapping("/repairdetail.do")
    @ResponseBody
    public HistoryDetailResponseBody getRepairDetail(String openid, String vrepairId) {
        return service.getRepairDetail(openid, vrepairId);
    }

    @RequestMapping("/eleclist.do")
    @ResponseBody
    public ElecListResponseBody getElecList(String openid) {
        return service.getElec(openid);
    }

    @RequestMapping("/repairtest.do")
    public ModelAndView index() {
        return new ModelAndView("templates/repairlist");
    }

    @RequestMapping("/carownerauthenstatus.do")
    @ResponseBody
    public String subPreCarOwner(CarOwnerHandleReq req) {
        CarOwnerAuthenStatus entity = carOwnerAuthenStatusService.get(req.getVincode());
        if (null != entity)
            return "1";
        saveCarOwnerAuthenStatus(req);
        return "2";
    }

    private void saveCarOwnerAuthenStatus(CarOwnerHandleReq req) {
        CarOwnerAuthenStatus entity = new CarOwnerAuthenStatus();
        entity.setAuthState(0L);
        entity.setCreateDate(Long.parseLong(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMMSS)));
        entity.setOpenId(req.getOpenid());
        entity.setUserMobile(req.getMobile());
        entity.setUserName(req.getName());
        entity.setUserVin(req.getVincode());
        carOwnerAuthenStatusService.save(entity);
    }


    @RequestMapping("/czclwsinit.do")
    @ResponseBody
    public CarInfoVo czclwsInit(String openid) {
        CarInfo info = carInfoService.get(openid);
        if (null != info) {
            CarInfoVo vo = new CarInfoVo();
            vo.setCarBuyingDealer(info.getBuyDealerName());
            vo.setCarColor(info.getColor());
            vo.setCarModel(info.getModel());
            vo.setCarRepairDealer(info.getRepairDealerName());
            vo.setCarStyle(info.getStyle());
            if (StringUtils.isNotEmpty(info.getBuyDate())) {
                String[] date = info.getBuyDate().split("-");
                if (date.length == 3) {
                    vo.setYear(date[0]);
                    vo.setMonth(date[1]);
                    vo.setDay(date[2]);
                }
            }
            return vo;
        }
        return null;
    }

    @RequestMapping("/subcarinfo.do")
    @ResponseBody
    public String subCarInfo(CarInfoVo vo) {
        CarInfo info = carInfoService.get(vo.getOpenid());
        if (info == null) {
            info = new CarInfo();
            setCarInfo(info, vo);
            carInfoService.save(info);
        } else {
            setCarInfo(info, vo);
            carInfoService.update(info);
        }
        return "1";
    }

    private void setCarInfo(CarInfo info, CarInfoVo vo) {
        info.setBuyDealerName(vo.getCarBuyingDealer());
        info.setRepairDealerName(vo.getCarRepairDealer());
        info.setOpenId(vo.getOpenid());
        info.setModel(vo.getCarModel());
        info.setColor(vo.getCarColor());
        info.setStyle(vo.getCarStyle());
        info.setBuyDate(vo.getYear() + "-" + vo.getMonth() + "-" + vo.getDay());
    }

    @RequestMapping("/czxxwsinit.do")
    @ResponseBody
    public UserInfoVo czxxwsInit(String openid) {
        UserInfo info = userInfoService.get(openid);
        if (null != info) {
            UserInfoVo vo = new UserInfoVo();
            vo.setUserName(info.getUserName());
            vo.setUserSex(info.getGender());
            vo.setUserMobile(info.getMobile());
            vo.setUserLicense(info.getLicense());
            vo.setUserAddress(info.getAddress());
            vo.setUserEmail(info.getEmail());
            if (StringUtils.isNotEmpty(info.getBirthday())) {
                String[] date = info.getBirthday().split("-");
                if (date.length == 3) {
                    vo.setYear(date[0]);
                    vo.setMonth(date[1]);
                    vo.setDay(date[2]);
                }
            }
            return vo;
        }
        return null;
    }

    @RequestMapping("/subuserinfo.do")
    @ResponseBody
    public String subUserInfo(UserInfoVo vo) {
        UserInfo info = userInfoService.get(vo.getOpenid());
        if (null == info) {
            info = new UserInfo();
            setUserInfo(vo, info);
            userInfoService.save(info);
        } else {
            setUserInfo(vo, info);
            userInfoService.update(info);
        }
        return "1";
    }

    private void setUserInfo(UserInfoVo vo, UserInfo info) {
        info.setUserName(vo.getUserName());
        info.setOpenId(vo.getOpenid());
        info.setGender(vo.getUserSex());
        info.setBirthday(vo.getYear() + "-" + vo.getMonth() + "-" + vo.getDay());
        info.setLicense(vo.getUserLicense());
        info.setMobile(vo.getUserMobile());
        info.setAddress(vo.getUserAddress());
        info.setEmail(vo.getUserEmail());
    }


//    HistoryResponseBody test(){
//        HistoryResponseBody body = new HistoryResponseBody();
//        body.setResponseCode("0000000");
//        List<CarInfoList> list = new ArrayList<>();
//        CarInfoList car = new CarInfoList();
//        car.setCarType("mengdio");
//        car.setCarColor("bai");
//        car.setVin("11111111");
//        List<CarRepairs> rs = new ArrayList<>();
//        CarRepairs re = new CarRepairs();
//        re.setNbalanceFee("500");
//        re.setRepairId("123456");
//        re.setVrepairDate("2015-11-11");
//        re.setVsstName("shanghai");
//        CarRepairs re1 = new CarRepairs();
//        re1.setNbalanceFee("400");
//        re1.setRepairId("654321");
//        re1.setVrepairDate("2014-11-01");
//        re1.setVsstName("shanghai");
//        rs.add(re);
//        rs.add(re1);
//        car.setCarRepairs(rs);
//
//        CarInfoList car1 = new CarInfoList();
//        car1.setCarType("mengdio");
//        car1.setCarColor("bai");
//        car1.setVin("2222222");
//        List<CarRepairs> rs2 = new ArrayList<>();
//        CarRepairs re2 = new CarRepairs();
//        re2.setNbalanceFee("500");
//        re2.setRepairId("123456");
//        re2.setVrepairDate("2015-11-11");
//        re2.setVsstName("shanghai");
//        CarRepairs re3 = new CarRepairs();
//        re3.setNbalanceFee("400");
//        re3.setRepairId("654321");
//        re3.setVrepairDate("2014-11-01");
//        re3.setVsstName("shanghai");
//        rs2.add(re2);
//        rs2.add(re3);
//        car1.setCarRepairs(rs2);
//
//        list.add(car);
//        list.add(car1);
//        body.setCarInfoList(list);
//        return body;
//    }

}
