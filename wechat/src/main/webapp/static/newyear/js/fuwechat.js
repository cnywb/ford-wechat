$(function() {
	window.share = {
		imgUrl : 'http://'+window.location.host+'/fordwechat/static/newyear/images/f1.gif',
		link : '',
		title : "求得长安福特平安福一枚！让亲朋好友人人有福，岁岁平安！",
		desc : "求得长安福特平安福一枚！让亲朋好友人人有福，岁岁平安！",
		}
		//shareConfig();
	});
	
	function shareConfig(){
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
                        // 用户确认分享后执行的回调函数
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
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
	
		
		



