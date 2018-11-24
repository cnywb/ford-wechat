(function ($) {
    Ford = function() {
		this.init();
    };
    $.extend(Ford.prototype, {
		
        init: function(data) {
			this.imgurl = '/fordwechat/static/yihu4/'
			this.preloadimages([''+this.imgurl+'img/home.jpg',''+this.imgurl+'img/part3_1.jpg',''+this.imgurl+'img/part3_2.jpg',''+this.imgurl+'img/category.jpg',''+this.imgurl+'img/part1.jpg',''+this.imgurl+'img/end.jpg',''+this.imgurl+'img/part1_1.jpg',''+this.imgurl+'img/part1_2.jpg',''+this.imgurl+'img/part1_3.jpg',''+this.imgurl+'img/part1_4.jpg',
			''+this.imgurl+'img/part2.jpg',''+this.imgurl+'img/part2_1.jpg',''+this.imgurl+'img/part2_2.jpg',''+this.imgurl+'img/part3.jpg',''+this.imgurl+'img/part4.jpg',''+this.imgurl+'img/part4_1.jpg',''+this.imgurl+'img/part4_2.jpg']);
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
			
			$('.video a').click(function(){
				$('.overlay').show()
			})
			
			$('.overlay a').click(function(){
				$('.overlay').hide()
				
			})
			
			$('.back').click(function(){
				window.location.href=''
			})
			
			$('.section2').on('touchstart',function(){
				$('.video').fadeIn(1000)
			})
			
		},
		
	
		
		
    });
})(jQuery);



$(function() {

  var ford = new Ford();
   // share();
});

