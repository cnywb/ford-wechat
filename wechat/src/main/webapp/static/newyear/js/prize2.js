(function ($) {
    Game = function() {
		this.init();
    };
    $.extend(Game.prototype, {
		
        init: function(data) {

        	//$('.province').empty().html('北京')
        	//this.getcity('北京');
        	//this.getvin()
        	var that =this;
			$('.prize a').click(function(){
				
				$('.popup,.setcoupon').fadeIn();
				 
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
							  'eventLabel': 'Prize2',
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
			$(".s_province").change(function(){
				$(".province").html($(this).val());
				$(".city").html('请选择市');
				that.getcity($(this).val());
				$(".dealer").empty().html('请选择经销商');
				$('.s_dealer').empty().append('<option>请选择经销商</option>')
				//that.getdealer($(this).val());
			});
			$(".s_city").change(function(){
				$(".city").html($(this).val());
				//that.getcity($(this).val());
				$(".dealer").empty().html('请选择经销商');
				that.getdealer($(this).val());
			});
			$(".s_dealer").change(function(){
				//alert($(this).find('option:selected').attr('id'));
				$('#did').val($(this).find('option:selected').attr('id'))
				$(".dealer").html($(this).val());
			});
			
			
			$(".selectVIN select").change(function(){
				$(".vin").html($(this).val());
			});
			
			$('.setcoupon form a').on('click',function(){
				
				var vin = $('.vin').html();
				var province = $('.province').html();
				var city = $('.city').html();
				var dealer = $('.dealer').html();
				var did = $('#did').val();
				var gid = $('#gid').val();
				var mobile = $('#mobile').val();
				if(vin=='请选择VIN码'|| city=='请选择市' || dealer=='请选择经销商' || mobile == ''){
					alert('请填写详细信息')
				}else{
					that.setinfo(vin,province,city,did,gid,mobile)
				}
				
			
       		 })
			
        },
        
         /**getvin:function(){
				
			$.ajax({
        	    url: '',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {
                	
                },
                success: function (response) {
					
					$.each(response.vin,function(i,data){
						$('.s_vin').append('<option>'+data.vin+'</option>')
					})
					
				
				}
				
			})
        },**/
        
         getcity:function(province){
			$('.s_city').empty().append('<option>请选择市</option>');	
			$.ajax({
        	    url: '/fordwechat/control?state=1208',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'province':province},
                success: function (response) {
					$.each(response.city,function(i,data){
						$('.s_city').append('<option>'+data.dealer_city+'</option>')
					})
					
				
				},
		
				
				
			})
        },
        
         getdealer:function(city){
			$('.s_dealer').empty().append('<option>请选择经销商</option>');	
			$.ajax({
        	    url: '/fordwechat/control?state=1209',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'city':city},
                success: function (response) {
					$.each(response.dealer,function(i,data){
						$('.s_dealer').append('<option id='+data.id+'>'+data.dealer_name+'</option>')
					})
					
					
				}
				
			})
        },
        
        
        setinfo:function(vin,province,city,did,gid,mobile){
				
			$.ajax({
        	    url: '/fordwechat/control?state=1207',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {'vin':vin,'gid':gid,'did':did,'mobile':mobile},
                success: function (response) {
					ga('send', {
					  'hitType': 'event',          // Required.
					  'eventCategory': "代金券奖品领取",   // Required.
					  'eventAction': 'click',      // Required.
					  'eventLabel': 'Vouchers',
					  'eventValue': 1
					});
					$('.setcoupon').hide();
					$('.commitsuccess').fadeIn()
					
				}
				
			})
        }
        
        
	
	
	})
		
})(jQuery);



$(function() {
  var game = new Game();
});


  