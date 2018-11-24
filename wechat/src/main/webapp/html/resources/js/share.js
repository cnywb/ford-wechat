/**
 * Created by jovi on 3/11/16.
 */
$(document).ready(function () {

    window.share = {
        imgUrl : 'http://point.xiqing.info/byws/resources/imgs/indexBg.jpg',
        link : 'https://www.changanfordclub.com/infocollection/potentialcustomer/byws/index.jspx?channel=1',
        title : '长安福特·全方位保养卫士',
        desc : '即刻检测爱车，在线获取长安福特为您定制的全方位专业保养建议'
    }
    shareConfig();
});

function shareConfig(){
    var thisurl = window.location.href;
    //$.getJSON('http://www.changanfordwechat.com/getinfo?state=4&url=' + window.location.href, function (response) {
    //    wxshare(response);
    //});
  
    $.getJSON('http://test.changanfordwechat.com/getinfo?state=4&url=' + window.location.href, function (response) {
        wxshare(response);
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

        });

    }
}