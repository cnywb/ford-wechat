/**
 * Created by qiuhong on 15/7/24.
 */
var subcount = 0;
function initScrape(count) {
    //设置屏高
    var oHeight = document.documentElement.clientHeight;
    var oMain = document.getElementById("main");
	
    oMain.style.height = oHeight + "px";

    $(".close").click(function () {
       $(".dialog-mod").hide();
       $(".rule-dialog").hide();
       $(".win-mod").hide();
       $(".mask").hide();
    });
	
	 $(".rule").click(function(){
        $(".mask").show();
        $("#rule-dialog").show();
    });

    $(".name-btn").click(function(){
       $(".mask").show();
       $("#prizeList").show();
    });

    window.onload = function () {
        var oWidth = $(".gift_list li").width(),
            oHeight = $(".gift_list img").height(),
            oLilength = $(".gift_list li").length,
            countWidth = oWidth * oLilength + 60 + "px";
            move = 0,
            page = Math.ceil(oLilength / 3),
            hitCount = 1;
        $(".gift_list ul").width(countWidth);
        $(".gift_list li").width(oHeight);
        $(".gift_list").height(oHeight);


        $("#right_btn").click(function () {
			move = $(".gift_list li").width() * 3;
			if(hitCount < page){
				$(".gift_list ul").css("-webkit-transform", "translateX(-" + move * hitCount + "px)");
				hitCount++;
			}
		});
		$("#left_btn").click(function () {
			move = $(".gift_list li").width() * 3;
			if(hitCount !== 1){
				--hitCount;
				$(".gift_list ul").css("-webkit-transform", "translateX(-" + move * (hitCount-1) + "px)");
			}
		});


        //刮奖区宽、高
        var s_height = $("#scrape_cont").height();
        var s_width = $("#scrape_cont").width();
		
		if(parseInt(count)>0){
			//刮奖效果
			var lottery = new Lottery('scrape_cont', '#8b8b8d', 'color', s_width, s_height, function (percent) {
				console.log(percent);
				if (percent > 60) {
					$("canvas").eq(1).remove();
					if(subcount==0)
						ajaxcontrol(parseInt(count));
				}
			});
			lottery.init("抽奖中。。。", "text");
		}else{
			$("#scrape_cont").append('<div style="width: 100%;height: 100%;background:#8b8b8d; z-index:99"></div>');
			/*
			var lottery = new Lottery('scrape_cont', '#8b8b8d', 'color', s_width, s_height, function (percent) {
				console.log(percent);
				if (percent > 50) {
					$("canvas").eq(1).remove();
					
				}
			});
			lottery.init("你未中奖", "text");*/
		}

        
    };

	$(".input-btn").click(function(){
		inputMsg();
	});
	
	$(".dialog-btn").click(function(){
		confirmMsg();
	})
	
	$("#re_input").click(function(){
		inputMsg();
	})
	
	function ajaxcontrol(luckcount){
		$.ajax({
			type: 'GET',
			url: '/fordwechat/control?state=307',
			dataType: 'json',
			//timeout: 1800,
			//context: $('body'),
			success: function(data){
				subcount = subcount + 1;
				$("#luckcount").empty();
				$("#luckcount").append(luckcount-1);
				if(luckcount-1 == 0)
					$("#againlucky").empty();
				//中奖
				if(parseInt(data.flag) == 1){
					//ll.init("恭喜，您获得了" + data.productTitle, "text");
				//var wprize = "恭喜，您获得了" + data.productTitle;
					//alert(wprize)
					$("#productTitle").empty();
					$("#productTitle").append(data.productTitle);
					$("#imageLargeURL").empty();
					$("#imageLargeURL").append('<img src="' + data.imageLargeURL + '">');
					$("#gid").val(data.gid);
					$("#scrape_cont").empty();
					$("#scrape_cont").append('<p style="margin-left:5%;margin-right:5%;margin-top:15%;color:red;font-size: 0.5em;text-align:center;" width="189" height="80">恭喜，您获得'+data.productTitle+'</p>');
					prize();
				}else{
					$("#scrape_cont").empty();
					$("#scrape_cont").append('<p style="margin-left:5%;margin-right:5%;margin-top:15%;color:red;font-size: 0.5em;text-align:center;" width="189" height="80">很遗憾，您未中奖</p>');
					notPrize();
					//ll.init("谢谢您的参与", "text");
				}
			},
		});
	}
	
    function notPrize() {
        $("#mask").show();
        $("#share").show();
        $("#not_prize").show();
    }

    function prize() {
        $("#mask").show();
        $("#yes_prize").show(); 
    }

    function inputMsg() {
        $("#yes_prize").hide();
        $("#input_msg").show(); 
    }

    function confirmMsg() {
        //校验页面数据
		user_name = $("#user_name").val();
		user_mobile= $("#user_mobile").val();
		user_address = $("#user_address").val();
		var reg = /^1[34587]\d{9}/;
		if($.trim(user_address)=="" || $.trim(user_name)=="" || $.trim(user_mobile)=="" || !reg.test($.trim(user_mobile)) || $.trim(user_mobile).length!='11'){
			fail();
			return;
		}
		gid = $("#gid").val();
        //校验ok的话提交
        //TODO 保存用户领奖信息
        $.ajax({
            type: 'POST',
            url: '/fordwechat/control?state=306',
            data: {'user_name':user_name,'user_mobile':user_mobile,'user_address':user_address,'gid':gid},
            dataType: 'json',
            //timeout: 1800,
            //context: $('body'),
            success: function(data){
                //假设data为true 表示成功   false表示失败
                if(data.flag=='ok'){
                    suc();
                }else{
                    fail();
                }
            },
            /*error: function(xhr, type){
                alert('Ajax error!')
            }*/
        });
         

        //成功回调
        function suc(){
            $("#input_msg").hide();
            $("#msg_ok").show();
            $("#share").show();
        }
        //失败回调
        function fail() {
            $("#input_msg").hide();
            $("#msg_error").show();
        }
    }

}




