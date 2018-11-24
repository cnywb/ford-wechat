/**
 *
 * @title   JavaScript Document
 * @authors Your Name (you@example.org)
 * @date    2015-06-04 19:48:40
 * @version Ver 1.0.0
 */
var car = car || {};
(function() {
	var me = this;
	this.options = {
		imgsrc: '',
		sourceX: 0,
		sourceY: 0,
		sourceWidth: 0,
		sourceHeight: 0
	};
	this.drawBeauty = function(beauty) {
		var mycv = document.getElementById("cv");
		var myctx = mycv.getContext("2d");
		myctx.drawImage(beauty, me.options.sourceX, me.options.sourceY, me.options.sourceWidth, me.options.sourceHeight);
	};

	this.loadBeauty = function() {
		var beauty = new Image();
		beauty.src = me.options.imgsrc;
		if (beauty.complete) {
			me.drawBeauty(beauty);
		} else {
			beauty.onload = function() {
				me.drawBeauty(beauty);
			};
			beauty.onerror = function() {
				window.alert('加载失败，请重试');
			};
		}
	};

	this.init = function() {
		if (document.all) {
			window.attachEvent('onload', me.loadBeauty);
		} else {
			window.addEventListener('load', me.loadBeauty, false);
		}
	};

}).call(car);

$(function() {
	/*
	var win_width = $(window).width();
	var win_height = $(window).height();
	$('#cv').attr({
		width: win_width,
		height: win_height
	});
	car.options.imgsrc = 'images/bg.png';
	car.options.sourceWidth = win_width;
	car.options.sourceHeight = win_height+1;
	car.init();
	*/
});