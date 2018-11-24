(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-64287083-1', 'auto');
// ga('send', 'pageview');
$(function() {
    


    var a = location.href;
    var b = a.split("/");
    var c = b.slice(b.length - 1, b.length).toString(String).split(".");
    var page_name = c.slice(0, 1).toString();
    page_name =  page_name.toUpperCase();
  
    page_view_name = "PAGE_VIEW_" + page_name;
    
   
    doPageview(page_view_name);
    /***
     * do tracking about the mobile click
     */

});

function doTrack(name) {
    console.log('track', name)
    name = name.toUpperCase();
    ga('send', {
        'hitType': 'event', // Required.
        'eventCategory': "UA", // Required.
        'eventAction': 'click', // Required.
        'eventLabel': "BUT_" + name,
        'eventValue': 1
    });
}
;
function doPageview(name) {
    console.log('doPageview', name);
    ga('send', 'pageview', {
        'page': name,
        'title': "翼虎微刊第六期",
    });
};

/**
 * 模仿request.getParameter(); 但是只能接收三种提交 <form method=GET ..>...</form> <a
 * href="xxx.html?a=b&dc=3">xxx</a> 从浏览器直接输入URL中带有信息xxx.html return
 * 返回的是string类型，
 */
function getParameter(name) {
    var paramStr = location.search;
    if (paramStr.length == 0)
        return "";
    if (paramStr.charAt(0) != '?')
        return "";
    paramStr = unescape(paramStr);
    paramStr = paramStr.substring(1);
    if (paramStr.length == 0)
        return "";
    var params = paramStr.split('&');
    for (var i = 0; i < params.length; i++) {
        var parts = params[i].split('=', 2);
        if (parts[0] == name) {
            if (parts.length < 2 || typeof (parts[1]) == "undefined"
                    || parts[1] == "undefined" || parts[1] == "null")
                return "";
            return parts[1];
        }
    }
    return "";
}



	