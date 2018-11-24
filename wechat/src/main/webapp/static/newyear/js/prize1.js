(function ($) {
    Game = function() {
		this.init();
    };
    $.extend(Game.prototype, {
		
        init: function(data) {
      	  var that =this;
			$('.prize a').click(function(){
				
				$('.popup,.setaddr').fadeIn();
				 
			})
			$('.prizeclose').on('touchstart',function(){
				$('.popup>div,.popup').hide()
			})
			$('.commitsuccess a').on('click',function(e){
				var ckindex = $(this).index()
					switch(ckindex){
						case 0:
							ga('send', {
							  'hitType': 'event',          // Required.
							  'eventCategory': "平安福",   // Required.
							  'eventAction': 'click',      // Required.
							  'eventLabel': 'Prize1',
							  'eventValue': 1
							});
							window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
						break;
						case 1:
							$('.prizeclose').hide();
							$('.commitsuccess').hide();
							$('.share').fadeIn()
						break;
						}
						
			})
			$("#s_province").change(function(){
				$("#province").html($(this).val())
				$("#city").empty().html('请选择市');
				$("#county").empty().html('请选择县/区');
			});
			
			$("#s_city").change(function(){
				$("#city").html($(this).val());
				$("#county").empty().html('请选择县/区');
			});
			$("#s_county").change(function(){
				$("#county").html($(this).val());
			});
			
			$('.setaddr form a').on('click',function(){
				
				var name = $('.name').val();
				var province = $('#province').html();
				var city = $('#city').html();
				var county = $('#county').html();
				var address = $('.addr').val();
				var mobile = $('.mobile').val();
				var gid = $('#gid').val();
				if(name=='' || province=='请选择省' || city=='请选择市' || address=='' || mobile==''){
				
					alert('请填写详细信息')
					
				}else if(!(/^1[34578]\d{9}/.test(mobile))) {
				
					alert('请填写正确手机号码')
						
 				   }else{

						that.setinfo(name,province,city,county,address,mobile,gid)
 					  
 				   }

			});
			
        },
        
        
        setinfo:function(name,province,city,county,address,mobile,gid){
				
				$.ajax({
        	    url: '/fordwechat/control?state=1206',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'name':name,'province':province,'city':city,'county':county,'address':address,'mobile':mobile,'gid':gid},
                success: function (response) {
					ga('send', {
					  'hitType': 'event',          // Required.
					  'eventCategory': "实物奖品领取",   // Required.
					  'eventAction': 'click',      // Required.
					  'eventLabel': 'KindPrizes',
					  'eventValue': 1
					});
					$('.setaddr').hide();
					$('.commitsuccess').fadeIn()
				}
				
			})
        }
		
		
	})
})(jQuery);



$(function() {
  var game = new Game();
});


  