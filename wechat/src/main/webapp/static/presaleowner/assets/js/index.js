$(function(){
block = false;

window.onload = isTouchDevice;
    $(document).on('touchmove.prevent', function(e) {
        e.preventDefault();
    });

function isTouchDevice() {
  //document.getElementById("version").innerHTML = navigator.appVersion;

   try {
      //document.createEvent("TouchEvent");
      console.log("支持TouchEvent事件！");

       //bindEvent(); //绑定事件
        }
    catch (e) {
      console.log("不支持TouchEvent事件！" + e.message);
      }
}
	var iw = $(window).width();
	var ih = $(window).height();
	$(".wrap").css("width",iw+"px");
	$(".wrap").css("height",ih+"px");
})


function sendInfo(name,mobile,city,snumber,utm_source){

	$.ajax({  
		url: '../api/order/submitInfo',
		data:{
			'user_name':name,
			'city':city,
			'mobile':mobile,
			'utm_source':utm_source,
			'invite_code':snumber
		},
		type: 'post',
		cache: false,
		async: false,
		dataType: 'json',
		success: function(value) {
			
		},
		error :function() {
			alert('system error');
			block = false;
		}					
	})
}	




function urlRequest(paras)  //获取url参数方法
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