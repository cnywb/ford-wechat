package com.ford.wechat.service.pageinterface.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ford.wechat.entity.infocollection.InfoCollectionCampaign;
import com.ford.wechat.entity.infocollection.InfoCollectionChannel;
import com.ford.wechat.entity.member.BackTempOwner;
import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.entity.pageinterface.IsVerifyOwnerReq;
import com.ford.wechat.entity.pageinterface.IsVerifyOwnerRes;
import com.ford.wechat.entity.pageinterface.IsVerifyOwnerVO;
import com.ford.wechat.entity.pageinterface.OwnerQrCodeVO;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeReq;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeRes;
import com.ford.wechat.entity.pageinterface.ValidateCampaginParamRes;
import com.ford.wechat.entity.user.ClubDictionary;
import com.ford.wechat.entity.user.FordCar;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.entity.user.OwnerQrCodeUrl;
import com.ford.wechat.respository.infocollection.InfoCollectionCampaignRepository;
import com.ford.wechat.respository.infocollection.InfoCollectionChannelRepository;
import com.ford.wechat.respository.member.BackTempOwnerRepository;
import com.ford.wechat.respository.user.ClubDictionaryRepository;
import com.ford.wechat.respository.user.FordCarRepository;
import com.ford.wechat.respository.user.FordClubMemberRepository;
import com.ford.wechat.respository.user.JoUserRepository;
import com.ford.wechat.respository.user.OwnerQrCodeUrlRepository;
import com.ford.wechat.service.pageinterface.PageInterfaceService;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import io.dabing.common.util.DateUtils;
import io.dabing.common.util.QRCodeUtil;
import io.dabing.common.util.StrUtils;
import io.dabing.core.service.AbstractService;

/**
 * 描述： PageInterfaceServiceImpl 页面ajax接口实现层代码类
 * 2017-06-27
 *
 * @author huangwen 
 * @since 1.0
 */
@Service
public class PageInterfaceServiceImpl extends AbstractService implements PageInterfaceService {
    @Autowired
    private JoUserRepository joUserRepository;
    @Autowired
    private FordClubMemberRepository fordClubMemberRepository;
    @Autowired
    private  BackTempOwnerRepository backTempOwnerRepository;
    @Autowired
    private  FordCarRepository fordCarRepository;
    @Autowired
    private ClubDictionaryRepository clubDictionaryRepository;
    @Autowired
    private InfoCollectionCampaignRepository infoCollectionCampaignRepository;
    @Autowired
    private  InfoCollectionChannelRepository infoCollectionChannelRepository;
    @Autowired
    private  OwnerQrCodeUrlRepository ownerQrCodeUrlRepository;
//    @Autowired
//    private  AzureBlobService azureBlobService;
    /***
     * 编码
     */
    private static final String UTF8 = "UTF-8";
  
    /***
     * Blob Client
     */
    private static CloudBlobClient serviceClient;
    
    private final String blobPath = "campaignQRCode/";
    
	@Override
	public IsVerifyOwnerRes vaildIsVerifyOwner(IsVerifyOwnerReq req) {
		IsVerifyOwnerRes res = new IsVerifyOwnerRes();	
		try {  
			log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:1 req.getCampaignCode():"+req.getCampaignCode()+"req.getChannelCode():"+req.getChannelCode());		
			//进行基础校验
			ValidateCampaginParamRes validateCampaginParamRes = vaildCampaginParam(req.getCampaignCode(),req.getChannelCode());
			log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2");
			if(validateCampaginParamRes.getStatus() != 1){
				res.setStatus(validateCampaginParamRes.getStatus());
				res.setMessage(validateCampaginParamRes.getMessage());
				return res;
			}
			
			if(StringUtils.isBlank(req.getOpenid()) || req.getOpenid().length() >= 100){
				res.setStatus(-7);
	       		res.setMessage("操作失败,openid不能为空或格式错误！");
	    		return res;
	         }
			  log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:3");
			  //根据会员信息获取相关数据信息 
			  IsVerifyOwnerVO vo = getOwnerVO("1",req.getOpenid(), null,null,null);
			  log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:4");
			  if (null == vo) {
	        	  res.setStatus(1);
		    	  res.setIsverifyOwner(0);// 是否认证车主:  1，是；0 否 
		    	  res.setMessage("操做成功！");
		 		  return res;
	         }else{
	        	 log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:5");
	        	  //查询是否已有保存信息二维码信息
				  OwnerQrCodeUrl url=  ownerQrCodeUrlRepository.getOwnerQrCodeUrlByReferrerVin(vo.getVin());
				  log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:6");
				  if (null == url){
					  res.setQrcode(""); 
				  }else{
					  res.setQrcode(url.getQrcodeUrl()); 
				  }
	        	  res.setStatus(1);//状态 1.成功，0 失败
		    	  res.setIsverifyOwner(1);//是否认证车主: 1，是；0 否
		    	  res.setData(vo);
		    	  res.setMessage("操做成功！");
		 		  return res;	          
	         }
        	
		} catch (Exception e) {
			  log.error(e.getMessage(),e);
			  res.setStatus(-99);
			  res.setMessage("操作失败,异常");
			  return res;
	    } 
	}

	@Override
	public OwnerqrCodeRes saveOwnerqrCodeUrl(OwnerqrCodeReq req) {
		 log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl saveOwnerqrCodeUrl:1");
		OwnerqrCodeRes res = new OwnerqrCodeRes();	
		try {  
			//进行基础校验
			ValidateCampaginParamRes validateCampaginParamRes = vaildCampaginParam(req.getCampaignCode(),req.getChannelCode());
			if(null == validateCampaginParamRes){
				res.setStatus(-99);
				res.setMessage("操作失败,异常validateCampaginParamRes");
				return res;
			}
			if(validateCampaginParamRes.getStatus() != 1){
				res.setStatus(validateCampaginParamRes.getStatus());
				res.setMessage(validateCampaginParamRes.getMessage());
				return res;
			}
			if(StringUtils.isBlank(req.getName())){
				res.setStatus(-8);
	       		res.setMessage("操作失败,name不能为空！");
	    		return res;
	         }
			if(StringUtils.isBlank(req.getMobile())){
				res.setStatus(-9);
	       		res.setMessage("操作失败,mobile不能为空！");
	    		return res;
	         }
			if(StringUtils.isBlank(req.getVin())){
				res.setStatus(-10);
	       		res.setMessage("操作失败,vin不能为空！");
	    		return res;
	         }
			if(StringUtils.isBlank(req.getUrl())){
				res.setStatus(-11);
	       		res.setMessage("操作失败,url不能为空！");
	    		return res;
	         }
			  //根据会员信息获取相关数据信息 
			  IsVerifyOwnerVO vo = getOwnerVO("2", null, req.getName(), req.getMobile(), req.getVin());
			  if (null == vo) {
	        	  res.setStatus(-12);
		    	  res.setMessage("操作失败，未找到对应车主！");
		 		  return res;
	         }
			     // 赋值  
				 log.info("03getOwnerqrCode qrcodeUrl");
	        	 OwnerQrCodeVO ownerQrCodeVO = new OwnerQrCodeVO();
	        	 BeanUtils.copyProperties(ownerQrCodeVO, vo);  
	        	 ownerQrCodeVO.setUrl(req.getUrl());
	        	 StringBuffer qrCodeUrl = new StringBuffer();
	        	 qrCodeUrl.append(ownerQrCodeVO.getUrl());
	        	 qrCodeUrl.append("&buyCarTime=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getBuyCarTime()), UTF8));
	        	 qrCodeUrl.append("&mobile=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getMobile()), UTF8));
	        	 qrCodeUrl.append("&model=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getModel()), UTF8));
	        	 qrCodeUrl.append("&name=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getName()), UTF8));
	        	 qrCodeUrl.append("&referrerOpenId=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getOpenId()), UTF8));
	        	 qrCodeUrl.append("&referrerVin=");
	        	 qrCodeUrl.append(URLEncoder.encode(StrUtils.trim(ownerQrCodeVO.getVin()), UTF8));
	        	 log.info("02getOwnerqrCode ownerInfoUrl"+qrCodeUrl.toString());
	        	 String ownerInfoUrl =  qrCodeUrl.toString();
	        	 res.setOwnerInfoUrl(ownerInfoUrl);  
				 res.setStatus(1);//状态 1.成功，0 失败
		    	 res.setMessage("操做成功！");
		 		 return res;	  
		} catch (Exception e) {
			  log.error(e.getMessage(),e);
			  res.setStatus(-99);
			  res.setMessage("操作失败,异常");
			  return res;
	    } 
	}
		
		
		public ValidateCampaginParamRes vaildCampaginParam(String campaignCode,String  channelCode) {
			log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 vaildCampaginParam1");
			ValidateCampaginParamRes res = new ValidateCampaginParamRes();
			res.setStatus(1);
	   		res.setMessage("操作成功");
			try{
				log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 vaildCampaginParam2");
				if(StringUtils.isEmpty(campaignCode)){
		       		res.setStatus(-1);
		       		res.setMessage("操作失败,campaignCode不能为空！");
		    		return res;
		    	}
		    	if(StringUtils.isEmpty(channelCode)){
		       		res.setStatus(-2);
		       		res.setMessage("操作失败,channelCode不能为空！");
		    		return res;
		    	}
		    	List<InfoCollectionCampaign> campaignList=infoCollectionCampaignRepository.findByCampaignCode(campaignCode);
		    	log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 findByCampaignCode");
		    	if(campaignList.size()==0){
		        	res.setStatus(-3);
		       		res.setMessage("操作失败,campaignCode不存在！");
		    		return res;
		        }
		      	log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 findByCampaignCode2");
		        InfoCollectionCampaign infoCollectionCampaign=campaignList.get(0);
		        if(infoCollectionCampaign.getTimeCheck()==true){
		            Date currentTime=new Date();
			        if(infoCollectionCampaign.getStartTime().getTime()>currentTime.getTime()){
			        	res.setStatus(-4);
			       		res.setMessage("操作失败,活动未开始！");
			    		return res;
			        }
			        if(infoCollectionCampaign.getEndTime().getTime()<currentTime.getTime()){
			        	res.setStatus(-5);
			       		res.setMessage("操作失败,活动已结束！");
			    		return res;
			        }
		        }
		       	List<InfoCollectionChannel> channelList=infoCollectionChannelRepository.findByChannelCode(channelCode);
		    	log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 findByChannelCode");
		        if(channelList.size()==0){
		        	res.setStatus(-6);
		       		res.setMessage("操作失败,channelCode不存在！");
		    		return res;
		        }
				
				} catch (Exception e) {
					  log.error(e.getMessage(),e);
			    } 
			log.info("/fordwechat/data/isverifyowner.do-in; PageInterfaceServiceImpl vaildIsVerifyOwner:2 findByChannelCode return");
			return  res;
		}
		
		
		
		
		public IsVerifyOwnerVO getOwnerVO(String type,String Openid,String  name,String mobile ,String vin) {
			FordClubMember fordClubMember = null;
			IsVerifyOwnerVO vo =null;
	
				if(type == "1"){
					//根据会员信息获取验证车主信息
					 JoUser joUser = joUserRepository.getJoUserByWechatId(Openid);
			         if (null == joUser) {
				 		  return null;
			          }
			         List<FordClubMember> memberList = fordClubMemberRepository.findByUserId(joUser.getId());
			         if (memberList.isEmpty() || memberList.size() < 1) {
				 		  return null;
			         }else {
			        	 fordClubMember = memberList.get(0);
			        	  vo =new IsVerifyOwnerVO();
				       	  vo.setMobile(fordClubMember.getMobile());//手机号
				       	  vo.setName(fordClubMember.getName());//客户姓名
				       	  vo.setVin(fordClubMember.getVvin());//车架号
				      	  vo.setOpenId(fordClubMember.getOpenId());//微信号
			         }
				}else if(type == "2"){
					//这个查的是验证车主信息
					//fordClubMember = fordClubMemberRepository.getFordClubMemberbyParam(name, mobile, vin);
					//查找车主信息
					log.info("/fordwechat/data/getOwnerVO newVin:"+vin.trim().toUpperCase());
				    BackTempOwner backTempOwner = backTempOwnerRepository.findByVin(vin.trim().toUpperCase());				  
				    if(null != backTempOwner){
				    	  log.info("/fordwechat/data/getOwnerVO backTempOwner getVin:"+backTempOwner.getVin()+";");
					    vo =new IsVerifyOwnerVO();
				     	vo.setVin(backTempOwner.getVin());
				     	vo.setName(backTempOwner.getOwnerName());
				     	vo.setMobile(backTempOwner.getMobile());
				    }  else {
				    	  log.info("/fordwechat/data/getOwnerVO backTempOwner is null");
				    	 return null;
				    } 
				}		
				if(null != vo){
			          FordCar fordCar = fordCarRepository.findFordCarByVin(vo.getVin());//找到对应的车辆信息 
			          if(null != fordCar){
			        	   ClubDictionary clubDictionary = clubDictionaryRepository.getClubDictoryNameByCode(fordCar.getVmodel());//根据车型去找车型名称
			        	   vo.setBuyCarTime(fordCar.getVpurchasedDate());//购车时间
			        	   vo.setModel((null==clubDictionary)?"":clubDictionary.getCdname());//车型
			          }
	   
				}				
			return vo;
		}
		
		 public static void main(String[] args) throws Exception {  
//			 URLEncoder.encode("", UTF8);
//			   BackTempOwner backTempOwner = backTempOwnerRepository.findByVin("LVSHCADB5GE904731");
//			   System.out.print(backTempOwner.getMobile());
		 } 
		 	
		/**
		 * 
		 ## azureBlob
			azure.blob.url= https://xiqing.blob.core.chinacloudapi.cn/callcenter/
			azure.blob.account.name=xiqing
			azure.blob.account.key=NEbUcwHWChzwj0B4y3RHi7I6WTVwRsIvjzVNY9Rc/y6WDVFK58w93pKHWYpc9wHhIfvOltOEAsxwRVXvIX9DDw==
			azure.blob.container.name=callcenter
		 */
			public String  getOwnerVOUrl(byte[] bytes ,String blobPath, String fileName){
				try {  
				    final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=http;AccountName={0};AccountKey={1};EndpointSuffix=core.chinacloudapi.cn";
			        String accountName= "xiqing";
			        String azureContainerName = "callcenter";
			        String azureBlobUrl=  "https://xiqing.blob.core.chinacloudapi.cn/callcenter/";
			        String accountKey = "NEbUcwHWChzwj0B4y3RHi7I6WTVwRsIvjzVNY9Rc/y6WDVFK58w93pKHWYpc9wHhIfvOltOEAsxwRVXvIX9DDw==";
			        String connection = MessageFormat.format(STORAGE_CONNECTION_STRING, accountName, accountKey);
			        CloudStorageAccount account= CloudStorageAccount.parse(connection); serviceClient = account.createCloudBlobClient();
			        CloudBlobContainer container = serviceClient.getContainerReference(azureContainerName);
			        boolean flag2 =   container.createIfNotExists();
			        System.out.print(flag2);
		            CloudBlockBlob blob = container.getBlockBlobReference(blobPath + fileName);
		            blob.uploadFromByteArray(bytes, 0, bytes.length);
		            String url = azureBlobUrl+ blobPath + fileName;
		            return url;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				return "";
			}
			//生产測試blob
			/**
			 * ##azure blob 对象存储
				azure.blob.url=https://cafcreporting.blob.core.chinacloudapi.cn/callcenter/
				azure.blob.account.name=cafcreporting
				azure.blob.account.key=yr1P/ZAqLMD+TB5lrBHefoFlB+7FW5liBun5b6Jt9iTxHexs+VTdKpw7Sk97sscwopQW5STGq8mhi5F4Mxr0Lw==
				azure.blob.container.name=callcenter
			 */
			public String  getOwnerVOUrlPro(byte[] bytes ,String blobPath, String fileName){
				try {  
				    final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=http;AccountName={0};AccountKey={1};EndpointSuffix=core.chinacloudapi.cn";
			        String accountName= "cafcreporting";
			        String azureContainerName = "callcenter";
			        String azureBlobUrl=  "https://cafcreporting.blob.core.chinacloudapi.cn/callcenter/";
			        String accountKey = "yr1P/ZAqLMD+TB5lrBHefoFlB+7FW5liBun5b6Jt9iTxHexs+VTdKpw7Sk97sscwopQW5STGq8mhi5F4Mxr0Lw==";
			        String connection = MessageFormat.format(STORAGE_CONNECTION_STRING, accountName, accountKey);
			        CloudStorageAccount account= CloudStorageAccount.parse(connection); serviceClient = account.createCloudBlobClient();
			        CloudBlobContainer container = serviceClient.getContainerReference(azureContainerName);
			        boolean flag2 =   container.createIfNotExists();
			        System.out.print(flag2);
		            CloudBlockBlob blob = container.getBlockBlobReference(blobPath + fileName);
		            blob.uploadFromByteArray(bytes, 0, bytes.length);
		            String url = azureBlobUrl+ blobPath + fileName;
		            return url;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				return "";
			}		
}
