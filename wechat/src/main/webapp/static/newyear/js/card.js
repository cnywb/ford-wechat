(function ($) {
    Game = function() {
		this.init();
    };
    $.extend(Game.prototype, {
		
        init: function(data) {
    	    this.myCanvas=document.getElementById('mycanvas');
			this.context=this.myCanvas.getContext('2d');
			var img = new Image();
            img.src='static/newyear/images/fu_bg.png';
            var that = this;
			if(img.complete) {  
					that.context.drawImage(img,0,0);
				 }  
			img.onload = function () { 
					that.context.drawImage(img,0,0);
				}; 
				
			$('.fu a').click(function(){
				var copy = $('.nycopy').val()
 				that.context.font="24px/40px '黑体'";     
				that.context.fillStyle = "#000000";
				that.context.fillText(copy,100,830);	
				//that.convertCanvasToImage(that.myCanvas)
				var pimage = that.myCanvas.toDataURL("image/png").replace("data:image/png;base64,", ""); 
				setTimeout(function(){
					that.ajaxuploadPic(pimage)
				},1000)
				
			})	
        },
        	
		ajaxuploadPic:function(image){
			var that = this;
			$.ajax({
				url: '/fordwechat/control?state=1211',
				type: "POST",
				dataType: "json",
				async: false,
				data: {'base64data':image},
				success: function (response) {
					//alert(response.filepath);
					$('.share,.popup').fadeIn();
					window.share.imgUrl = 'http://'+window.location.host+'/fordwechat/static/newyear/images/f1.gif';
					window.share.title = '长安福特 新春”福“务月，限时狂欢惠';
					window.share.desc = '求得长安福特平安福一枚！让亲朋好友人人有福，岁岁平安！';
					window.share.link = 'http://'+window.location.host+'/fordwechat/control?state=1212&filepath=' + response.filepath;
					that.shareConfig();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
				}
					
			})
		},
		shareConfig:function(){
			$.getJSON('http://'+window.location.host+'/getinfo?state=3',
			{ 'url' :  window.location.href },
			function(data)
			{
			
			
				wx.config({
					debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: data.appid, // 必填，公众号的唯一标识
					timestamp: data.timestamp, // 必填，生成签名的时间戳
					nonceStr: data.nonceStr, // 必填，生成签名的随机串
					signature: data.signature,// 必填，签名，见附录1
					jsApiList: [
						'checkJsApi',
						'onMenuShareTimeline',
						'onMenuShareAppMessage'
					] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});

				wx.ready(function () {
					wxcheck();
					function wxcheck(){
						wx.checkJsApi({
							jsApiList: [
								'onMenuShareTimeline',
								'onMenuShareAppMessage'
							],
							success: function (res) {
								//alert(JSON.stringify(res));

							}
						});
					}

					wx.onMenuShareTimeline({
						imgUrl : window.share.imgUrl,
						link : window.share.link,
						title : window.share.desc,
						desc : window.share.desc,
						success: function () {
							// 用户确认分享到朋友圈后执行的回调函数
							ga('send', {
							  'hitType': 'event',          // Required.
							  'eventCategory': "平安福",   // Required.
							  'eventAction': 'click',      // Required.
							  'eventLabel': 'ShareTimeline',
							  'eventValue': 1
							});
						},
						cancel: function () {
							// 用户取消分享后执行的回调函数
						}
					});
					
					wx.onMenuShareAppMessage({
						title: window.share.title, // 分享标题
						link: window.share.link, // 分享链接
						imgUrl: window.share.imgUrl,
						desc:  window.share.desc,
						trigger: function (res) {
						//	alert('用户点击分享给朋友');
						},
						success: function (res) {
							//用户确认分享给朋友后执行的回调函数
							ga('send', {
							  'hitType': 'event',          // Required.
							  'eventCategory': "平安福",   // Required.
							  'eventAction': 'click',      // Required.
							  'eventLabel': 'ShareFriend',
							  'eventValue': 1
							});

						},
						cancel: function (res) {
						//	alert('已取消');
						},
						fail: function (res) {
						//	alert(JSON.stringify(res));
						}
					});

					wx.onMenuShareAppMessage({
						imgUrl : window.share.imgUrl,
						link : window.share.link,
						title : window.share.desc,
						desc : window.share.title,
						trigger: function (res) {
							//	alert('用户点击分享到朋友圈');
						},
						success: function (res) {
							//	alert('已分享');
						},
						cancel: function (res) {
							//	alert('已取消');
						},
						fail: function (res) {
							//	alert(JSON.stringify(res));
						}
					});

				});
			});
		}
		
	})
})(jQuery);

$(function() {
	window.share = {
		imgUrl : 'http://'+window.location.host+'/fordwechat/static/newyear/images/f2.gif',
		link : '',
		title : "不用拼运气，不用拼RP！长安福特微客服车主红包人人有礼！快来试试手气吧！",
		desc : "不用拼运气，不用拼RP！长安福特微客服车主红包人人有礼！快来试试手气吧！",
	}
  var game = new Game();
});


  