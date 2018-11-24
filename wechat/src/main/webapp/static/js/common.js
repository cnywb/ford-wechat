var phoneWidth = parseInt(window.screen.width);
var phoneScale = phoneWidth / 640;
var ua = navigator.userAgent;
if (/Android (\d+\.\d+)/.test(ua))
{
	var version = parseFloat(RegExp.$1);
	if (version > 2.3)
	{
		document.write('<meta name="viewport" content="width=640, minimum-scale = ' + phoneScale + ', maximum-scale = ' + phoneScale + ', target-densitydpi=device-dpi">');
	} else
	{
		document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
	}
} else
{
	document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
}

var openid = getck("openid");
function getck(sname) {//获取单个cookies
    var acookie = document.cookie.split("; ");
    for (var i = 0; i < acookie.length; i++) {
        var arr = acookie[i].split("=");
        if (sname == $.trim(arr[0])) {
            if (arr.length > 1)
                return unescape($.trim(arr[1]));
            else
                return "";
        }
    }
    return "";
}

function queryByName(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
};

function getparamvalue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return decodeURIComponent(r[2]);
    return null; //返回参数值
};