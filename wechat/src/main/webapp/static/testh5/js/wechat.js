$(document).ready(function () {

	window.share = {
		imgUrl : 'http://'+window.location.host+'/html/img/sharelogo.jpg',
		link : 'http://'+window.location.host+'/html/index.html',
		title : "",
		desc : "",
		}
	
		shareConfig();
	});
	
	function shareConfig(){
		var thisurl = window.location.href;
		$.ajax({
			url : '../we-chat-api/auth-share',
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
						  'eventCategory': "PHYSIOGEL",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'SensitiveTest_H5_moment',
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
						  'eventCategory': "PHYSIOGEL",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'SensitiveTest_H5_repost',
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