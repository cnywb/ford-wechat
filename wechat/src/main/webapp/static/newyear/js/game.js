(function ($) {
    Game = function() {
		this.init();
    };
    $.extend(Game.prototype, {
		
        init: function(data) {
			//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			this.addNum = 0; // 红包数量
			this.oWrap=$("#wrap");
			this.oChild=$(".child1");
			this.maxLeft=519;
			this.maxTop=650;
			this.oDiv1=$("#div1");
			this.flag = 3; // 车主状态
			this.ajaxUserInfo();
		//	this.initgame()	
			this.loadimages(['/fordwechat/static/newyear/images/home.png','/fordwechat/static/newyear/images/rule.png','/fordwechat/static/newyear/images/gamebg.png','/fordwechat/static/newyear/images/info.png','/fordwechat/static/newyear/images/mammon.png','/fordwechat/static/newyear/images/popup1.png','/fordwechat/static/newyear/images/popup2.png'])
		//	this.countdown(4);
        },
        
        loadimages : function(arr){
        	$('.loading').fadeIn(500)
			$('#fire')[0].play()
			var self=this;
			var newimages=[], loadedimages=0
			var arr=(typeof arr!="object")? [arr] : arr
			function imageloadpost(){
				loadedimages++
				var num = loadedimages/7
				var movelenth = (1-num.toFixed(2))*332+386
				//$('.percent').empty().html(num.toFixed(2)*100+'%')
				var pct = Math.round(num*100)/100
				$('.percent').empty().html(pct*100+'%')
					$('.firecrackers').animate({
						height:movelenth
							},500,function(){
								if(movelenth == 386){
									$('#fire')[0].pause()
									self.initpage();
								}
							})
							
				if (loadedimages==arr.length){
					
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
				imageloadpost()
				}
			}
		},
		
		
		initpage:function(){
			$('.loading').hide();
			$('.part1,.page').fadeIn(500)
			var that = this;
			$('.part1 a').on('click',function(e){
				var ckindex = $(this).index()
					switch(ckindex){
						case 0:
							that.flagstate()
						break;
						case 1:
								$('.part1').hide();
								ga('send', {
								  'hitType': 'event',          // Required.
								  'eventCategory': "平安福",   // Required.
								  'eventAction': 'click',      // Required.
								  'eventLabel': 'Index',
								  'eventValue': 1
								});
								window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
								//$('.fu').fadeIn(500);
						break;
						case 2:
								$('.part1').hide();
								$('.rule').fadeIn(500);
						break;
						}
			})
			$('.rule a').on('click',function(e){
				var ckindex = $(this).index()
					switch(ckindex){
						case 0:
							that.flagstate()
						break;
						case 1:
								$('.part1').hide();
								ga('send', {
								  'hitType': 'event',          // Required.
								  'eventCategory': "平安福",   // Required.
								  'eventAction': 'click',      // Required.
								  'eventLabel': 'Rule',
								  'eventValue': 1
								});
								window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
								
							//	$('.fu').fadeIn(500);
						break;
						}
						
			})
			
			$('.secondtime a').on('touchstart',function(){
				$('.popup,.secondtime,.part1').hide();
				ga('send', {
				  'hitType': 'event',          // Required.
				  'eventCategory': "平安福",   // Required.
				  'eventAction': 'click',      // Required.
				  'eventLabel': 'Index',
				  'eventValue': 1
				});
				window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
			//	$('.fu').fadeIn(500);
			})
		/*	$('.fu a').on('touchstart',function(){
				$('.share,.popup').fadeIn()
			})
			*/
			
			
			//12.21
			
			$('.close').on('touchstart',function(){
				$('.popup>div,.popup').hide()
			})
			
			$('.isowner a').on('touchstart',function(e){
				var ckindex = $(this).index()
					switch(ckindex){
						case 0:
								window.location.href='http://'+window.location.host+'/fordwechat/control?state=31';
						break;
						case 1:
								$('.part1,.isowner,.popup,.rule').hide();
								//$('.fu').fadeIn(500);
								ga('send', {
								  'hitType': 'event',          // Required.
								  'eventCategory': "平安福",   // Required.
								  'eventAction': 'click',      // Required.
								  'eventLabel': 'Index',
								  'eventValue': 1
								});
								window.location.href='http://'+window.location.host+'/fordwechat/control?state=1210';
						break;
						}
			})
			
		},
		
		//车主状态
		
		flagstate:function(){
		
			var that = this;
			$.ajax({
        	    url: '/fordwechat/control?state=1203',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {},
                success: function (response) {
					//alert(response.flag);
					//that.flag = response.flag;
					if(response.flag == 1){
						ga('send', {
						  'hitType': 'event',          // Required.
						  'eventCategory': "点击并且进入抢红包游戏",   // Required.
						  'eventAction': 'click',      // Required.
						  'eventLabel': 'Lottery',
						  'eventValue': 1
						});
						$('.part1,.rule').hide();
						$('#wrap').show();	
						$('#gamemusic')[0].play()
						setTimeout(function(){
							that.countdown(4);			
						},200)		
					}
					if(response.flag == 2){
						$('.popup,.secondtime').fadeIn();
						}		
					if(response.flag == 3){
						$('.popup,.isowner').fadeIn();
					}			

				}
				
			})
		
		},
		
		
		
		initgame:function(){
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			var startX= this.random(5,340);
			this.start(this.oChild,startX);
			this.startpst(this.oDiv1)
			this.setTimer(1,'timer1',this.oChild,startX,0);
			this.addbag(60);
			this.touchdiv();
			this.line(-720)
			
		},
		
		countdown:function(num){
			var that = this;
				num--;
				if(num<1){
					$('.countdown span').hide()
					setTimeout(function(){
						that.initgame();
					},100)
					
				}else{
					$('.countdown span').attr('class','').addClass('num'+num)
					setTimeout(function(){
						that.countdown(num);
					},1000)
				}
				
		},
		
		start:function(redbag,startleft){
			redbag.css('left',startleft)
		},
		
		//汽车移动
		touchdiv:function(){
			
			touch.on('#div1', 'touchstart', function(ev){
				ev.preventDefault();
			});

			var dx, dy;

			touch.on('#div1', 'drag', function(ev){
				dx = dx || 0;
				var offx = dx + ev.x ;
				if(offx>=-160 && offx<=161)
				{
					var sx = offx+'px'
					this.style.webkitTransform = "translate3d(" + sx + ",0,0)";
				}
					
				
				
			});
			var that = this;
			touch.on('#div1', 'dragend', function(ev){
				dx += ev.x;
				that.startpst(that.oDiv1)
			});
		},
		
		//汽车接红包
		startpst:function($div){
			
			var offset = $div.offset();
			
			this.bagleft=offset.left-175;
			this.bagright=offset.left-80;
			this.bagtop=offset.top-46-288;
			this.bagbottom=this.maxTop;
			
		},
		
		setTimer:function(y,timer,child,startleft,movestartY){
			var that = this;
		
			timer=setInterval(function(){
			if(movestartY>=that.bagtop&&startleft>=that.bagleft&&startleft<=that.bagright){
				y*=-1;
				that.addNum++;
			//	$('.num').empty().html(that.addNum+'分')
				clearInterval(timer);
				child.addClass('getbag')
				setTimeout(function(){
						child.remove()
					},200)
			
				
			}

			if(movestartY>800){
				clearInterval(timer);
				child.remove()
			}
			movestartY+=y*5;
			child.css('top',movestartY+"px")	
			},30)
		},
		
		//红包随机下落
		random:function(min,range) {
			return Math.floor(Math.random() * range+min);
		},
		
		addbag:function(num){
			var that = this;
				num--;
				var len = (60-num)*5.35
				var percent = parseInt(len*100/313)
				
				$('.timebar span').css('width',len)
				$('.timebar span').empty().html(percent+'%')
			
				if(num>1){
					var remainder = num%3;
					if(remainder == 0){
						$('.content').append("<div id='child' class='child"+num+"'></div>")
						var startX= this.random(5,340);
						that.start($(".child"+num+""),startX);
						that.setTimer(1,'timer'+num+'',$(".child"+num+""),startX,0);
					}	
					setTimeout(function(){
						that.addbag(num);
					},250)
				}else{
					clearInterval();
					$('#gamemusic')[0].pause()
					setTimeout(function(){
						that.ajaxsetNum(that.addNum);
					},1000)
				}
		},
		
		//马路滚动
		line:function(num){
			var that = this;
				num++;
				if(num>0){
				/*
					$('.content').css('background-position','0 20px')
					setTimeout(function(){
						that.line(-720);
					},10)*/
				}else{
					$('.content').css('background-position','0 '+num+'px')
					setTimeout(function(){
						that.line(num);
					},30)
				}
				
		},
		
		//页面开始－－用户状态	
		ajaxUserInfo:function(){
     		var that = this;
			$.ajax({
        	    url: '/fordwechat/control?state=1203',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {},
                success: function (response) {
					//alert(response.flag);
					that.flag = response.flag;

				}
				
			})
		
		},
		
		//游戏结束－－中奖结果	
		ajaxsetNum:function(num){
			window.location.href = 'http://'+window.location.host+'/fordwechat/control?state=1205&num=' + num;
     		/**var that = this;
			$.ajax({
        	    url: '/fordwechat/control?state=1205',
         	    type: "POST",
          		dataType: "json",
                async: false,
                data: {
                	num:num
                },
                success: function (response) {
					
					
					
					
				}
				
			})**/
		
		},	

	
	})
		
})(jQuery);



$(function() {
  var game = new Game();
});


  