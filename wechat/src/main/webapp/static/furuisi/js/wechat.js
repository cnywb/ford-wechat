window.cardExt = {
    code: '',
    openid: '{{openid}}',
    timestamp: '',
    signature: '',
	cardid: 'pULu_jm2rJjgEuvbBCciFp1A2_28',
}
//获取微信版本号：例如5.2.1
function get() {
    var reg = /MicroMessenger\/([\d\.]+)/i,
            ret = ua.match(reg);

    if (ret && ret[1]) {
        return ret[1];
    }
    return false;
}
function onBridgeReadyold() {
    return false;
}
if (typeof WeixinJSBridge == "undefined") {
    if (get() >= '5.5') {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady);
    } else {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReadyold);
    }
} else {
    if (get() >= '5.5') {
        onBridgeReady();
    } else {
        onBridgeReadyold();
    }
}

function check(){
	var reg = /^1[1-9][0-9]\d{4,8}$/;
	openid = '{{openid}}'
	$(".cardPop .note").hide();
	if($.trim($("#tel").val())=="" || !reg.test($.trim($('#tel').val())))
	{
		$('.kaqcont').hide();	
		$(".cardPop").show();
		$(".cardPop .note01").show();
		return false;
	}
	$.ajax( {
		url :'/fordwechat/control?state=1103',
		type : 'post',
		data: {
			tel:$("#tel").val()
			},
		dataType:'text',
		success : function(result) {
		if (result=='0') {
			$('.kaqcont').hide();	
			$(".cardPop").show();
			$(".cardPop .note03").show();
			
		}else{
			$('.kaqcont').hide();	
			$(".cardPop").show();
			$(".cardPop .note02").show();
		}
	},
	error:function(){
		$('.kaqcont').hide();	
		$(".cardPop").show();
		$(".cardPop .note02").show();
	}
	}); 
}