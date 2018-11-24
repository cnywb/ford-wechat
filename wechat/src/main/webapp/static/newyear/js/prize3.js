(function ($) {
    Game = function() {
		this.init();
    };
    $.extend(Game.prototype, {
		
        init: function(data) {
			$('.prize a').click(function(){
				
				$('.popup,.commitsuccess2').fadeIn();
				 
			})
			$('.prizeclose').on('touchstart',function(){
				$('.popup>div,.popup').hide()
			})
			$('.commitsuccess2 a').on('click',function(e){
				var ckindex = $(this).index()
					switch(ckindex){
						case 0:
							ga('send', {
							  'hitType': 'event',          // Required.
							  'eventCategory': "平安福",   // Required.
							  'eventAction': 'click',      // Required.
							  'eventLabel': 'Prize3',
							  'eventValue': 1
							});
							window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
						break;
						case 1:
							$('.prizeclose').hide();
							$('.commitsuccess2').hide();
							$('.share').fadeIn()
						break;
						}
						
			})
        },
	
	
	})
		
})(jQuery);



$(function() {
  var game = new Game();
});


  