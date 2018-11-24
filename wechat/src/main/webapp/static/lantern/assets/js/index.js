$(function () {
		
		block = false;
		//initSelectData("buyingDealerProvince","","buyingDealerCity","", "carBuyingDealer", "");
		
		
		$('.btnSubmit').on('touchstart',function(){
			var mobile = $('.inputTel').val();
			var name = $('.inputName').val();
			var car = $(".selectCar").find("option:selected").val();
			var tryDate = $('.selectDate').find("option:selected").text();
			var prov = $("#province").find("option:selected").text();
			var city = $("#city").find("option:selected").text();
			var dealer = $("#dealer").find("option:selected").text();
			
			if(name==''){
				alert('请输入您的姓名！')
				return false;
			}
			if(mobile == '' || !(/^1[34578]\d{9}/.test(mobile)) ){
				alert('请输入正确的手机号！')
				return false;
			}
			if (car === "0") {
				alert("请选择试驾车型");
				return false;
			}
			if (city === "" || city === "请选择市") {
				alert("请选择省市");
				return false;
			}
//			if (dealer === "" || dealer === "(可选填)") {
//				alert("请选择经销商");
//				return false;
//			}
			if (tryDate === "" || tryDate === "请选择") {
				alert("请选择预计购车时间");
				return false;
			}
			if(block == false){
				block = true
				saveinfor(name,mobile,car,tryDate,prov,city,dealer)
			}
		});
		$("#province").change(function(){
			var provID = $(this).find("option:selected").text();
			getcity(provID);
		});
		$("#city").change(function(){
			var cityID = $(this).find("option:selected").text();
			getdealer(cityID);
		});
		
		
		
		//FirstPage显示NOtice
		$(".noticeLink").on("touchstart",function(){
			$(".noticeLayer").fadeIn();
		})
		//notice关闭按钮
		$(".noticeLayer .close").on("touchstart",function(){
			$(".noticeLayer").fadeOut();
		})
		//车主认证按钮点击
		$(".btnCarAuth").on("touchstart",function(){
			ga('send', 'event', 'Link', 'Click','元宵活动跳转认证页面',1);
			window.location.href = "/fordwechat/control?state=31";
		})
		//粉丝试驾按钮点击
		$(".btnDriveApply").on("touchstart",function(){
			ga('send', 'event', 'Link', 'Click','元宵活动跳转试驾页面',1);
			window.location.href = "/fordwechat/control?state=503";
		})
		//表单提交成功弹窗关闭按钮 跳转第一页
		$('.successLayer .close').on("touchstart",function(){
			window.location.href = "/fordwechat/control?state=505";
		})
		//表单提交成功弹窗分享按钮点击
		$('.successLayer .shareBtn').on("touchstart",function(){
			$(".successLayer").fadeOut(300,function(){
				$(".shareLayer").fadeIn();
			})
		})
		//分享弹出窗，关闭按钮
		$('.shareLayer').on("touchstart",function(){
			window.location.href = "/fordwechat/control?state=505";
		})
})

/**
 * 初始化三级联动下拉菜单
 */
//function initSelectData(provinceSelectId,defaultProvince,citySelectId,defaultCity,dealerSelectId,defaultDealer){
//	loadProvince(provinceSelectId,defaultProvince,citySelectId,defaultCity,dealerSelectId,defaultDealer);
//	$("#"+provinceSelectId).change(function(){
//		$("#"+citySelectId).html("");
//		$("#"+dealerSelectId).html("");
//		var provinceId=$(this).find("option:selected").attr("data");
//		loadCity(citySelectId,'',provinceId,'','');
//	});
//	$("#"+citySelectId).change(function(){
//		$("#"+dealerSelectId).html("");
//		var cityId=$(this).find("option:selected").attr("data");
//		loadDealer(dealerSelectId,'',cityId);
//	});
//	
//}

// * 加载省
// */
//function loadProvince(selectId,defaultValue,citySelectId,defaultCity,dealerSelectId,defaultDealer){
//	var submitData={};
//	$.ajax( {
//		url :urlHost + 'dealer/findAllProvince.jspx',
//		type : 'post',
//		data:submitData,
//		dataType:'json',
//		success : function(result) {
//			$("#"+selectId).empty();
//			$("#"+selectId).append('<option value="" data="" >请选择</option>');
//			for(var i=0;i<result.length;i++){
//				var t=result[i];
//				if(defaultValue==t.name){
//					$("#"+selectId).append('<option selected="selected" value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//				    loadCity(citySelectId, defaultCity, t.id,dealerSelectId,defaultDealer);
//				}else{
//					$("#"+selectId).append('<option value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//				}
//			}
//      },error:function(){
//        alert("网络或数据异常，操作失败！");
//     }
//	});
//}

// * 加载城市
// */
//function loadCity(selectId,defaultValue,provinceId,dealerSelectId,defaultDealer){
//	if(provinceId==""){
//		return;
//	}
//	var submitData={"provinceId":provinceId};
//	$.ajax( {
//		url :urlHost + 'dealer/findAllCityByProvinceId.jspx',
//		type : 'post',
//		data:submitData,
//		dataType:'json',
//		success : function(result) {
//			$("#"+selectId).empty();
//			$("#"+selectId).append('<option value="" data="" >请选择</option>');
//			for(var i=0;i<result.length;i++){
//				var t=result[i];
//				if(defaultValue==t.name){
//					$("#"+selectId).append('<option selected="selected" value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//					loadDealer(dealerSelectId, defaultDealer, t.id);
//				}else{
//					$("#"+selectId).append('<option value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//				}
//			}
//      },error:function(){
//        alert("网络或数据异常，操作失败！");
//     }
//	});
//}
// * 加载经销商
// */
//function loadDealer(selectId,defaultValue,cityId){
//	if(cityId==""){
//		return ;
//	}
//	var submitData={"cityId":cityId};
//	$.ajax( {
//		url :urlHost + 'dealer/findAllDealerByCityId.jspx',
//		type : 'post',
//		data:submitData,
//		dataType:'json',
//		success : function(result) {
//			$("#"+selectId).empty();
//			$("#"+selectId).append('<option value="" data="" >请选择</option>');
//			for(var i=0;i<result.length;i++){
//				var t=result[i];
//				if(defaultValue==t.name){
//					$("#"+selectId).append('<option selected="selected" value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//				}else{
//					$("#"+selectId).append('<option value="'+t.name+'" data="'+t.id+'" >'+t.name+'</option>');
//				}
//			}
//      },error:function(){
//        alert("网络或数据异常，操作失败！");
//     }
//	});
//}



	
function getcity(province){
			$('#city').empty().append('<option>请选择市</option>');	
			$.ajax({
        	    url: '/fordwechat/control?state=506',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'province':province},
                success: function (response) {
					$.each(response.city,function(i,data){
						$('#city').append('<option>'+data.dealer_city+'</option>')
					})
				},
			})
        }
	
function getdealer(city){
			$('#dealer').empty().append('<option>(可选填)</option>');	
			$.ajax({
        	    url: '/fordwechat/control?state=507',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'city':city},
                success: function (response) {
					$.each(response.dealer,function(i,data){
						$('#dealer').append('<option id='+data.id+'>'+data.dealer_name+'</option>')
					})
					
					
				}
				
			})
        }
	
function saveinfor(name,mobile,car,Dealdate,province,city,dealer){
	content = name  + "|" + mobile + "|" + car;
	ga('set','campaignContent',content);
	ga('send', 'event', 'Link', 'Click','元宵活动试驾提交',1);
	$.ajax({  
		url: '/fordwechat/control?state=504',
		data:{
			'privance':province,
			'city':city,
			'uname':name,
			'umobile':mobile,
			'dealer':dealer,
			'buytime':Dealdate,
			'favourcar':car
		},
		type: 'post',
		cache: false,
		async: false,
		dataType: 'json',
		success: function(value) {
			block = false;
			if(value.status == '1'){
//				$('.popup').hide();
				$('.successLayer').fadeIn();
				//alert("信息提交成功");
			}else{
				alert(value.message)
			}
		},
		error :function() {
			alert('system error');
			block = false;
		}					
	})
}	



//获取url中?后的值的方法
function urlRequest(paras)
	{ 
	var url = location.href; 
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
	var paraObj = {} 
	for (i=0; j=paraString[i]; i++){ 
	paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
	var returnValue = paraObj[paras.toLowerCase()]; 
	if(typeof(returnValue)=="undefined"){ 
	return ""; 
	}else{ 
	return returnValue; 
	} 
	}


