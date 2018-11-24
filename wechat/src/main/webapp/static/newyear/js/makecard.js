$(function () {
	$('.fu').fadeIn(500);
			
	$('.fu a').click(function(){
		var copy = $('.nycopy').val()
		var bg = $('.gubg')[0]
		makecard(bg,copy)
	})	
	
});

function makecard(image,copy){
 	var myCanvas=document.getElementById('mycanvas');
	var context=myCanvas.getContext('2d');
		context.font="24px/40px '黑体'";     
		context.fillStyle = "#ffffff";
		context.drawImage(image,0,0);
		context.fillText(copy,100,830);	

		
	var pimage = myCanvas.toDataURL("image/png")
			console.log(pimage)
			$('#p').attr('src',pimage)
			
		setTimeout(function(){
			ajaxuploadPic()
		},1000)
		//window.location.href=getimage;			
}	

	