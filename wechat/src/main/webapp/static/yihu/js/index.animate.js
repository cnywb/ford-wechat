(function ($) {
    Ford = function() {
		this.init();
    };
    $.extend(Ford.prototype, {
		
        init: function(data) {
			$('.page').hide();
			this.link = '/yihu';
			this.imgurl = '/fordwechat/static/yihu/'
			if(location.href.indexOf('?state=120') > -1){
				this.preloadimages([''+this.imgurl+'img/home.jpg',''+this.imgurl+'img/home_btn.png']);
			}
			if(location.href.indexOf('?state=121') > -1){
				this.preloadimages([''+this.imgurl+'img/category.jpg']);
			}
			if(location.href.indexOf('?state=122') > -1){
				this.preloadimages([''+this.imgurl+'img/cycle1.png',''+this.imgurl+'img/cycle2.png',''+this.imgurl+'img/cycle3.png',''+this.imgurl+'img/part1_1.jpg',''+this.imgurl+'img/part1_2.jpg',''+this.imgurl+'img/part1_3.jpg',''+this.imgurl+'img/part1_4.jpg']);
			}
			if(location.href.indexOf('?state=123') > -1){
				this.preloadimages([''+this.imgurl+'img/cycle1.png',''+this.imgurl+'img/cycle2.png',''+this.imgurl+'img/cycle3.png',''+this.imgurl+'img/part2_1.jpg',''+this.imgurl+'img/part2_2.jpg']);
			}
			if(location.href.indexOf('?state=124') > -1){
				this.preloadimages([''+this.imgurl+'img/cycle1.png',''+this.imgurl+'img/cycle2.png',''+this.imgurl+'img/cycle3.png',''+this.imgurl+'img/part3_1.jpg']);
			}
			if(location.href.indexOf('?state=125') > -1){
				this.preloadimages([''+this.imgurl+'img/cycle1.png',''+this.imgurl+'img/cycle2.png',''+this.imgurl+'img/cycle3.png',''+this.imgurl+'img/part4_1.jpg',''+this.imgurl+'img/part4_2.jpg']);
			}
			if(location.href.indexOf('?state=126') > -1){
				this.preloadimages([''+this.imgurl+'img/end.jpg']);
			}
			if(location.href.indexOf('?state=127') > -1){
				this.preloadimages([''+this.imgurl+'img/qrcode.jpg']);
			}
		},
		
		preloadimages : function(arr,t){
			var self=this;
			var newimages=[], loadedimages=0
			var arr=(typeof arr!="object")? [arr] : arr
			function imageloadpost(){
				loadedimages++
				
				if (loadedimages==arr.length ){
					$('.page').show();
					self.afterload();
				}
			}
			for (var i=0; i<arr.length; i++){
				newimages[i]=new Image()
				newimages[i].src=arr[i]
				newimages[i].onload=function(){
					imageloadpost()
				}
				newimages[i].onerror=function(){
				imageloadpost()
				}
			}
		},
		afterload:function(){
			var that = this;
			$('.category a').click(function(e){
				var index = Number($(this).index()+1);
				window.location.href='http://'+window.location.host+that.link+'?state=12'+index+'';
			})
			$('.home a').click(function(){
				window.location.href='http://'+window.location.host+that.link+'?state=121';
			})
		/*	$('.detail').click(function(){
				window.location.href='http://'+window.location.host+'/category.html';
			})*/
			$('.back').click(function(){
				window.location.href='http://'+window.location.host+that.link+'?state=120';
			})
			$('.getqrcode').click(function(){
				$('.qrcode').show();
				$('.wrap').hide();
			})
			$('.shadow').click(function(){
				$('.qrcode').hide();
				$('.wrap').show();
			})
			
			$('.pback,.p3back').click(function(){
				window.location.href='http://'+window.location.host+that.link+'?state=121';
			})
			
			$('.goQrcode').click(function(){
				window.location.href='http://'+window.location.host+that.link+'?state=127';
			})
			
			$('.share').click(function(){
				$('.overlay').show()
			})
			$('.overlay').click(function(){
				$('.overlay').hide()
			})
			$('.animate').click(function(){
				$('.landing').hide();
				$('.wrap').show();
			})
			$('.p3 .animate').click(function(){
				$('.landing').hide();
				$('.p3detail').show();
			})
			$('.gonext,.p3gonext').click(function(){
				var id = $(this).attr('data-id')
				window.location.href='http://'+window.location.host+that.link+'?state=12'+id+'';
			})
			that.quesanimate(0)
			if(window.location.href.indexOf('?state=122')>=0 || window.location.href.indexOf('?state=123')>=0 || window.location.href.indexOf('?state=125')>=0){
				that.load();
			}
			
		},
		
		
		quesanimate:function(num){
				var that = this;
				num++;
				if(num<4){
					$('.animate>img:nth-child('+num+')').fadeIn(500)
					setTimeout(function(){
						that.quesanimate(num);
					},500)
				}else{
					num=0;
					$('.animate>img').hide();
					that.quesanimate(num);
				}
		},	
		
		load: function(){
            var self = this;
            $('.wrap').fullpage({
                scrollingSpeed: 500,
                easing: 'swing',
            });
		},
    });
})(jQuery);



$(function() {

  var ford = new Ford();
   // share();
});



function doTrack(name) {
	console.log('track',name)
	ga('send', {
	  'hitType': 'event',          // Required.
	  'eventCategory': "LEVI'S Look Book",   // Required.
	  'eventAction': 'click',      // Required.
	  'eventLabel': name,
	  'eventValue': 1
	});
};
function doPageview(name) {
	console.log('doPageview',name)
	ga('send', {
	  'hitType': 'event',          // Required.
	  'eventCategory': "LEVI'S Look Book",   // Required.
	  'eventAction': 'page',      // Required.
	  'eventLabel': name,
	  'eventValue': 1
	});
};
