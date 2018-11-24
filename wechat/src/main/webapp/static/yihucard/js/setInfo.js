$(function () {
	  
	$(".rulebtn").on("touchstart",function(){
		$("#ruleOverlay").removeClass("dn");
		$("#ruleOverlay").fadeIn(); 
	})
	
	$(".ruleTxt").on("touchend",function(){
		$("#ruleOverlay").fadeOut();
	})

	$(".ruleTxt1").on("touchend",function(){
		$("#succOverlay").fadeOut();
	}) 

	$(".ruleTxt2").on("touchend",function(){
		$("#sorryOverlay").fadeOut();
	}) 

	$(".formsubmitBtn").on("touchstart",function(){ 
		var mobile = $(".inputMobi").val();
		var name = $(".inputName").val();

		
		if(name==''){
			alert('请输入您的姓名！')
			return false;
		} 
		if(mobile == '' || !(/^1[34578]\d{9}/.test(mobile)) ){
			alert('请输入正确的手机号！')
			return false;
		} 

		saveinfor(name,mobile);
	})
}) 

function saveinfor(name,mobile){ 
	$.ajax({  
		url: '/fordwechat/control?state=16002',
		data:{
			'yihuname':name,
			'yihumobile':mobile
		},
		type: 'post',
		cache: false,
		async: false,
		dataType: 'json',
		success: function(value) {
			if(value.flag == '1'){ 
				$("#succOverlay").fadeIn();
				$("#succOverlay").removeClass("dn");
			}else{
				$("#sorryOverlay").fadeIn();
				$("#sorryOverlay").removeClass("dn");
			}
		},
		error :function() {

		}					
	})
}	 

