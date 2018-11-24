$(function () {
    $.ajax({
        async: false,
        url: '/fordwechat/repairlist.do',
        type: 'GET',
        dataType: 'json',
        data: {openid: openid},
        timeout: 1200,
        success: function (data) {
            $("#container").empty();
            if (data && typeof(data.carInfoList) != 'undefined') {
                $("#container").append('<ul class="contentRow" id="contentRow"></ul>');
                $.each(data.carInfoList, function (index, item) {
                    $("#contentRow").append('<li><p>VIN码:' + item.vin
                        + '</p><table class="contentTable" id="contentTable' + index + '"><tr><td class="tableHead">经销商名称</td>' +
                        '<td class="tableHeadDate" colspan="2">日期</td></tr><tr class="lineSpacing"></tr>' +
                        '</table></li>');
                    $.each(item.carRepairs, function (indx, car) {
                        $("#contentTable" + index).append('<tr><td class="tdLeft">' + car.vsstName + '</td><td class="tdRight">' +
                            car.vrepairDate + '</td><td class="tdDetail"><a href="/templates/renzhgdcxC.html?vrepairId=' +
                            car.repairId + '&openid=' + openid + '">详情</a></td></tr><tr class="lineSpacing"></tr>');
                    });
                });
                setTimeout('menu_show("click","contentRow","li","p","table",100)', 300);
            } else {
                $("#container").append('<h3>暂未查到维修保养记录</h3>');
            }
        },
        error: function (xhr) {
            $("#container").append('<h3>暂未查到维修保养记录</h3>');
        }

    });
})


function menu_show(play_type, elementID, firstTag, elementTag, lastTag, _time) {
    //play_type为触发事件方式，值可为click或者hover。
    //elementID为此菜单的ID。
    //firstTag此值为列表项每一项的标签名。
    //elementTag为列表项每一项的标签名的下一级的标签名。
    //lastTag二级菜单的标签名
    //_time为展开二级菜单的时间
    if (!$("." + elementID)) {
        return
    }
    for (var i = 0; i < $(lastTag).length; i++) {
        $(lastTag).css("display", "none");
    }
    if (play_type === "click") {
        $("." + elementID + " " + firstTag + ">" + elementTag).click(
            function () {
                for (var j = 0; j < $(lastTag).length; j++) {
                    $(lastTag).hide();
                }
                if ($(this).parent().children(lastTag).css("display") == "none") {
                    $(this).parent().children(lastTag).fadeIn(_time);
                    $(this).parent().parent().find('li').removeClass('openFolder');
                    // $(this).parent().parent().find('span').removeClass('icoMinus');
                    //$(this).parent().children().find('span').addClass('icoMinus');
                    //$(this).parent().children().find('span').removeClass('icoplus');
                    $(this).parent().addClass('openFolder');
                }
                else {
                    $(this).parent().children(lastTag).slideUp(_time);
                }
            }
        )
    }
    else {
        return
    }
}