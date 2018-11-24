
"use strict";

var canvas = (function () {

	var canvas, stage;
	var mouseTarget;	// the display object currently under the mouse, or being dragged
	var dragStarted;	// indicates whether we are currently in a drag operation
	var offset;
	var update = true;

    return {
        init: function () {
			examples.showDistractor();
			canvas = document.getElementById("mycanvas");
			stage = new createjs.Stage(canvas);
			createjs.Touch.enable(stage);
			stage.enableMouseOver(10);
			stage.mouseMoveOutside = true; 
			var image = new Image();
			image.src = "./images/clock.jpg";
			image.onload = handleImageLoad;

        }
		
    };

function stop() {
	createjs.Ticker.removeEventListener("tick", tick);
}

function handleImageLoad(event) {
	var image = event.target;
	var bitmap;
	var container = new createjs.Container();
	stage.addChild(container);

	for (var i = 0; i < 1; i++) {
		bitmap = new createjs.Bitmap(image);
		container.addChild(bitmap);
		bitmap.x = canvas.width * Math.random() | 0;
		bitmap.y = canvas.height * Math.random() | 0;
		bitmap.rotation = 90 * Math.random() | 0;
		bitmap.regX = bitmap.image.width / 1 | 0;
		bitmap.regY = bitmap.image.height / 1 | 0;
		bitmap.scaleX = bitmap.scaleY = bitmap.scale = Math.random() * 0.4 + 0.6;
		bitmap.name = "bmp_" + i;
		bitmap.cursor = "pointer";
	}

	examples.hideDistractor();
	createjs.Ticker.addEventListener("tick", tick);
}

function tick(event) {
	// this set makes it so the stage only re-renders when an event handler indicates a change has happened.
	if (update) {
		update = false; // only update once
		stage.update(event);
	}
}
	
   

})();

