$(document).ready(function () {

	window.share = {
		imgUrl : 'http://'+window.location.host+'/fordwechat/static/yihu/img/sharelogo.jpg',
		link : window.location.href,
		title : "我们是翼家人-福特翼虎微刊第3期",
		desc : "2015无尽之旅，等你探索",
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
						  'eventCategory': "ford",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'fordpart3_ShareTimeline',
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
						  'eventCategory': "ford",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'fordpart3_ShareFriend',
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