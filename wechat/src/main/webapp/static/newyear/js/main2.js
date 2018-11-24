

"use strict";

var orientLayer = document.getElementById("orientLayer");

function checkDirect() {
    if (document.documentElement.clientHeight >= document.documentElement.clientWidth) {
        return "portrait";
    } else {
        return "landscape";
    }
}

function orientNotice() {
    var orient = checkDirect();
    if (orient === "landscape") {
        orientLayer.style.display = "block";
    } else {
        orientLayer.style.display = "none";

    }
}
function init() {


    setTimeout(scrollTo, 0, 0, 0);

    orientNotice();
    window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function () {
        setTimeout(orientNotice, 200);
        console.log($(window).width() + " " + $(window).height());
    });

    
    document.addEventListener('touchmove', preventDefault, false);

    document.body.addEventListener('touchmove', function (ev) {
        var target = ev.target;

        if (isScroller(target)) {
            ev.stopPropagation();
        }
    }, false);


    if (document.body) { setupEmbed(); }
    else { document.addEventListener("DOMContentLoaded", setupEmbed); }

    function setupEmbed() {
        if (window.top != window) {
            document.body.className += " embedded";
        }
    }

    var o = window.examples = {};
    o.showDistractor = function(id) {
        var div = id ? document.getElementById(id) : document.querySelector("div canvas").parentNode;
        div.className += " loading";
    };

    o.hideDistractor = function() {
        var div = document.querySelector(".loading");
        div.className = div.className.replace(/\bloading\b/);
    };

}

function preventDefault(ev) {
    ev.preventDefault();
}

function isScroller(el) {

    return el.classList.contains('scroller');
}



$(function () {
   init();
	

    canvas.init();
	
});


function SetCookie(name, value) {
    var Days = 30 * 12;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}


