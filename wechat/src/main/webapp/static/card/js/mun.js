$(function(){

    // document.addEventListener('touchmove',function(e){
    //     e.preventDefault();
    // });
    //失去焦点效果
    var _hieght = $(window).height();
    $('.content_box').css({
        // 'height' : _hieght + 70 +'px'
        'height' : _hieght
    });

    var j = 0 ;
    var $imgs = $('img');
    // var imgobj=[];
    $imgs.each(function(k,v) {
      var imgData = $(this).data('src');
      if(imgData){
        var img = new Image();
        img.src = imgData;
        img.onload = imgload;
      }
    });

    function imgload(){
      j++
      if($imgs.length == j){
        $imgs.each(function(){
          var $this = $(this);
          var imgData = $this.data('src');
          if( imgData ){
            $this.attr('src',imgData);
          }
        });
        $('.loading').hide();
        $('.wrap').show();
      }
    }

    function g_pop(text){
      $('.g_text').html(text);
      $('#g_pop').show();
      $('.overlay').show();
    }

    function error(text){
      $('.content_text').html(text);
    }

    $('.car').on('keyup',function(){
      var $this = $(this);
      if($this.val().length <=6){
        $this.val('福特');
      }
    });

    $('#pro_indent').on('keyup',function(){
      var $this = $(this);
      if($this.val().length <=1){
        $this.val('A');
      }
    });

    $('.pro_dealers').on('keyup',function(){
      var $this = $(this);
      if($this.val().length <=6){
        $this.val('SN1604');
      }
    });



    var $car = $('.car');
    $car.on('change',function(){
      var p = $(this).val();
      $(this).prev('span').html(p);
    });

    var total = 0;
    var $agg_box = $('.agg_box');

    function initTotal(num){
      var html = '';
      total += num;
      var numtext = total+'';
      if(numtext.length<4){
          numtext = '0'+numtext;
      }
      for(var i=0; i<numtext.length;i++){
        html += '<span>'+numtext[i]+'</span>';
      }
      $agg_box.html(html);
    }

    function card($ele,num,times){
      setTimeout(function(){
        $ele.find('.face').addClass('back_bg5');
        $ele.addClass('card-flipped');
        setTimeout(function(){
          initTotal(num);
        },200*times);
      },800*times);
    }


    var lucky_false = true
    $(document).on('click','.submit_btn',function(){
        //情况错误提示
        error('');

        var nameVal = $("#sign_name").val();
        var phone  = $("#sign_phone").val();
        //var codeVal = $("#code").val();
        var dealers = $('#pro_indent').val();
        var indent1 = $('.pro_dealers').eq(0).val();
        var indent2 = $('.pro_dealers').eq(1).val();
        var cartype1  = $('.car').eq(0).val();
        var cartype2  = $('.car').eq(1).val();
        var test =  /^1[0-9]{10}$/;
        var dealerstest = /^[a|A][0-9]{5}$/;
        var indentest = /^(SN|Sn|sn|sN)1604[0-9]{6}$/;
        var ordernoarr = [];
        var cartypearr = [];

        if(nameVal=='姓名：' || nameVal==''){
            //alert("姓名不能为空");
            error('*姓名不能为空');
            return false;
        }
        if(phone ==''){
            // alert("手机号码格式不正确");
            error('*请您填写手机号码');
            return false;
        }else{
        if(!test.test(phone)){
            // alert("手机号码格式不正确");
            error('*您填写的手机号码有误');
            return false;
          }
        }

        if(dealers.length==6){
          if(!dealerstest.test(dealers)){
            // alert("手机号码格式不正确");
            error('*您填写的经销商销售代码有误');
            return false;
          }
        }else{
          error('*您填写的经销商销售代码有误');
          return false;
        }

        if(cartype1 == ''){
           error('*您请选择车型');
           return false;
        }else{
          cartypearr[0] = cartype1;
        }

        if(indent1.length==12){
          if(!indentest.test(indent1)){
            error('*您填写的订单编号有误');
            return false;
          }else{
            ordernoarr[0] = indent1;
          }
        }else{
          error('*您填写的订单编号有误');
          return false;
        }


        if(cartype2 == "" && indent2 == "SN1604"){

        }else{
          if(cartype2 == ""){
            error('*您请选择车型');
            return false;
          }else{
            cartypearr[1] = cartype2;
          }
          if(indent2 == ""){
            error('*您填写的订单编号有误');
            return false;
          }else{
            if(indent2.length==12){
              if(!indentest.test(indent2)){
                error('*您填写的订单编号有误');
                return false;
              }else{
                  ordernoarr[1] = indent2;
                }
            }else{
              error('*您填写的订单编号有误');
              return false;
            }
          }
        }

        if(indent1==indent2){
          error('*订单编号重复');
          return false;
        }


        if(!lucky_false)return;
        lucky_false=false;

        var obj = {
          name: nameVal,
          phone : phone,
          dealercode: dealers,
          orderno:ordernoarr,
          cartype:cartypearr,
        };

        $.ajax({
            url:"../allcars/sales/saveinfo",
            dataType: "json",
            type : "POST",
            data: obj,
            success: function(res) {
             if(res.result == 1){
                lucky_false=true;
                $('.content').css({'display':'none'});
                $('.lucky_content').css({'display':'block'});
                $('.grid_ul').html('');
                $('.lucky_c span').html(res.gift.length);
                $.each(res.gift,function(k,v){
                  //强制转换int
                  var gridhtml='<div class="grid_box">\
                                <div class="card">\
                                <div class="face front"><img src="images/1.jpg" ></div>\
                                <div class="face back"><div class="awardsGroup awards-sprite awards-p-'+v+'"></div></div>\
                              </div>\
                            </div>';
                  $('.grid_ul').append(gridhtml);
                  card($('.grid_box').eq(k),v,(k+1));
                })

              }else{
                  //提交失败
                  //alert(res.msg);//输出错误原因
                  lucky_false=true;
                  error('*'+res.msg);
              }
            }
        });

    });

    $('.lucky_btn').on('click',function(){
           window.location.reload();
    });

    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    var myIscroll;
    // var lucky_false = true
    $(document).on('click','.inquire_form_btn',function(){
        //if(!lucky_false)return;
        //lucky_false=false
        var nameVal = $("#inquire_name").val();
        var mobileVal = $("#inquire_phone").val();
        var test =  /^1[0-9]{10}$/;

        if(nameVal=='姓名：' || nameVal==''){
            //alert("姓名不能为空");
            alert('*姓名不能为空');
            return false;
        }

        if(mobileVal ==''){
            // alert("手机号码格式不正确");
            alert('*请您填写手机号码');
            return false;
        }else{
        if(!test.test(mobileVal)){
            // alert("手机号码格式不正确");
            alert('*您填写的手机号码有误');
            return false;
          }
        }

        var obj = {
            name: nameVal,
            phone: mobileVal,
        }

        $.ajax({
            url:"../allcars/sales/searchlottery",
            dataType: "json",
            type : "POST",
            data: obj ,
            success: function(res) {
                   //console.log(res);
              //lucky_false=true;
             if(res.result == 1){
                if( res.gift.length>0 ){
                  $.each(res.gift,function(k,v){
                    var txt = "";
                    var total = 0;
                    $.each(v.list,function(key,val){
                       txt = txt + val +"元";
                       total = Number(val) + Number(total);
                      if(v.list.length != key+1)
                          txt = txt + "、";
                    });
                    var inquirehtml=' <p class="title">您累计抽奖次数为：'+v.list.length+' 次</p>\
                    <p class="lj"><strong>累计中奖金额为：'+total+'元</strong></p>\
                    <p class="lj">中奖金额分别为：'+txt+'。</p>\
                    <p class="zz">*最终中奖信息以长安福特复核筛查过的名单为准</p>';
                    $('#scroller').html(inquirehtml);
                    if(myIscroll){
                      myIscroll.refresh();
                    }else{
                      myIscroll = new IScroll('#wrapper02', { scrollX: true, scrollY: true, mouseWheel: true, freeScroll: true });
                    }
                  });
                }else{
                    $('#scroller').html('<p class="title">没有查询到您的中奖记录</p>');
                }
              }else{
                  //提交失败
                  //alert(res.msg);//输出错误原因
                  alert(res.msg);
              }
            }
        });
   });



    var $sign_pop = $('.pop_box');
    var $overlay = $('.overlay');


    $('.rules_btn').on('click',function(){
        $overlay.show();
        $('#prompt_box').show();
    });

    $('.view').on('click',function(){
        $('#prompt_box').hide();
        $('#rules_box').show();
        new IScroll('#wrapper', { scrollX: true, scrollY: true, mouseWheel: true, freeScroll: true });
    });

    $('.rules_view').on('click',function(){
        $('#rules_box').hide();
        $('#prompt_box').show();
    });

    $('.public_colse').on('click',function(){
      $overlay.hide();
      $sign_pop.hide();
    });



    $.ajax({
        url:"get_time.php",
        dataType: "json",
        type : "GET",
        success: function(res) {
          //res.now_time  2015-11-18 15:33:33
          console.log(res);
          if(res.status == '-1'){
            $('.qt_content').css({'display':'block'});
          }else if(res.status == '-2'){
            $('.over_content').css({'display':'block'});
          }else{
            $('.content').css({'display':'block'});
          }
        }
    });


});
