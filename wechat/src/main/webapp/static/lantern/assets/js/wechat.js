$(document).ready(function () {

	window.share = {
		imgUrl : 'http://'+window.location.host+'/fordwechat/static/lantern/assets/i/share.jpg',
		link : 'http://'+window.location.host+'/fordwechat/control?state=505',
		title : "长安福特新老粉丝齐站队，元宵豪礼送不停！",
		desc : "长安福特新老粉丝齐站队，元宵豪礼送不停！",
		}
		shareConfig();
	});
	
	function shareConfig(){
		var thisurl = window.location.href;
		$.ajax({
			url : 'http://'+window.location.host+'/getinfo?state=3',
			type : "POST",
			dataType : "json",
			async : false,
			data:{url:thisurl},

		success : function(response) {
				wxshare(response);
				}
		});
		
		function wxshare(response) {
		
			wx.config({
			  debug: false,
			  appId: response.appId,
			  timestamp:response.timestamp,
			  nonceStr: response.nonceStr,
			  signature: response.signature,
			  jsApiList: [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'chooseImage',
				'previewImage',
				'uploadImage',
				'downloadImage',
			  ]
		  })
		  
		   wx.ready(function () {
			wxcheck();
			
			function wxcheck(){
					wx.checkJsApi({
						  jsApiList: [
                                                       'checkJsApi',
							'onMenuShareTimeline',
							'onMenuShareAppMessage',
							'onMenuShareQQ',
							'onMenuShareWeibo'
						  ],
						  success: function (res) {
							//alert(JSON.stringify(res));
							
						  }
						});
				}
				wx.onMenuShareTimeline({
					title: window.share.desc, // 分享标题
					link: window.share.link, // 分享链接
					imgUrl: window.share.imgUrl,
					desc:  window.share.desc,
					success: function () { 
						// 用户确认分享后执行的回调函数
						ga('send', {
						  'hitType': 'event',          // Required.
						  'eventCategory': "微刊6",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'fordpart6_ShareTimeline',
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
					//	alert('用户点击分享到朋友圈');
					},
					success: function (res) {
						ga('send', {
						  'hitType': 'event',          // Required.
						  'eventCategory': "微刊5",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'fordpart5_ShareFriend',
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
				
				wx.onMenuShareQQ({
					title:  window.share.title, // 分享标题
					desc:  window.share.desc, // 分享描述
					link: window.share.link, // 分享链接
					imgUrl: window.share.imgUrl, // 分享图标
					success: function () { 
					   // 用户确认分享后执行的回调函数
					},
					cancel: function () { 
					   // 用户取消分享后执行的回调函数
					}
				});
				
				wx.onMenuShareWeibo({
					title:  window.share.title, // 分享标题
					desc:  window.share.desc, // 分享描述
					link: window.share.link, // 分享链接
					imgUrl: window.share.imgUrl, // 分享图标
					success: function () { 
					   // 用户确认分享后执行的回调函数
					},
					cancel: function () { 
						// 用户取消分享后执行的回调函数
					}
				});
				
				
			})
			
			
		}
	
	}