Element.addMethods({
    absPosition: function(d, e){
        d = $(d);
        d.setStyle("position:absolute;");
        var c = d.getHeight();
        var b = d.getWidth();
        var a = function(){
            var j = document.viewport.getHeight();
            var h = document.viewport.getWidth();
            var i = document.viewport.getScrollOffsets().top;
            var g = document.viewport.getScrollOffsets().left;
            var f = {
                "top-left": "left:" + g + "px; top:" + i + "px",
                "bottom-left": "left:" + g + "px; top:" + (j + i - c) + "px",
                "top-right": "left:" + (h + g - b) + "px; top:" + i + "px",
                "bottom-right": "left:" + (h + g - b) + "px; top:" + (j + i - c) + "px",
                center: "left:" + (h / 2 + g - b / 2) + "px; top:" + (j / 2 + i - c / 2) + "px"
            };
            if (!f[e]) {
                e = "center"
            }
            d.setStyle(f[e])
        };
        a();
        Event.observe(window, "scroll", a);
        Event.observe(window, "resize", a);
        return Element.extend(d)
    }
});
var obj = Class.create({
    initialize: function(){
        this.version = "3.7.1";
        this.author = "Jim Yuan";
        this.maskID = "GRMask"
    },
    request: function(a){
        var b = window.location.href.toQueryParams();
        return (b[a]) ? b[a] : ""
    },
    _getCookieVal: function(b){
        var a = document.cookie.indexOf(";", b);
        if (a == -1) {
            a = document.cookie.length
        }
        return unescape(document.cookie.substring(b, a))
    },
    setCookie: function(a, b){
        document.cookie = a + "=" + escape(b)
    },
    getCookie: function(d){
        if (d == "path" || d == "expires" || d == "domain" || d == "version") {
            d = "badCookieName"
        }
        var b = d + "=";
        var f = b.length;
        var a = document.cookie.length;
        var e = 0;
        while (e < a) {
            var c = e + f;
            if (document.cookie.substring(e, c) == b) {
                return this._getCookieVal(c)
            }
            e = document.cookie.indexOf(" ", e) + 1;
            if (e == 0) {
                break
            }
        }
        return null
    },
    newWin: function(){
        var a = $$("a[href][rel=external]");
        a.map(function(b){
            b.target = "_blank"
        })
    },
    colorInvert: function(a){
        a = a.toUpperCase();
        var f = "01234567##89ABCDEF".toArray();
        var e = /^#[0-9A-F]{3}$/;
        var d = /^#[0-9A-F]{6}$/;
        var g = "";
        var b = [];
        if (d.test(a)) {
            g = a
        } else {
            if (e.test(a)) {
                g = a.gsub(/[0-9A-F]/, function(c){
                    return c[0].times(2)
                })
            }
        }
        if (!g.empty()) {
            g.toArray().each(function(c){
                b.push(f.reverse(false)[f.indexOf(c)])
            });
            return b.join("")
        } else {
            return null
        }
    },
    getPageSize: function(){
        var c, a;
        if (window.innerHeight && window.scrollMaxY) {
            c = document.body.scrollWidth;
            a = window.innerHeight + window.scrollMaxY
        } else {
            if (document.body.scrollHeight > document.body.offsetHeight) {
                c = document.body.scrollWidth;
                a = document.body.scrollHeight
            } else {
                c = document.body.offsetWidth;
                a = document.body.offsetHeight
            }
        }
        var b, d;
        if (self.innerHeight) {
            b = self.innerWidth;
            d = self.innerHeight
        } else {
            if (document.documentElement && document.documentElement.clientHeight) {
                b = document.documentElement.clientWidth;
                d = document.documentElement.clientHeight
            } else {
                if (document.body) {
                    b = document.body.clientWidth;
                    d = document.body.clientHeight
                }
            }
        }
        if (a < d) {
            pageHeight = d
        } else {
            pageHeight = a
        }
        pageWidth = c;
        pageSize = {
            cX: pageWidth,
            cY: pageHeight
        };
        return pageSize
    },
    setWinInMask: function(a){
        var b = "GRMaskWin";
        var d = Object.extend({
            maskColor: "#F90",
            alpha: 0.7,
            winWidth: 400,
            winHeight: 300
        }, arguments[1] || {});
        var e = this.getPageSize().cX;
        var f = this.getPageSize().cY;
        $(document.body).insert(new Element("div", {
            id: this.maskID
        }).setStyle("position:absolute; background:" + d.maskColor + "; top:0; left:0; width:100%; height:" + f + "px;"));
        $(this.maskID).setOpacity(d.alpha);
        var c = '<iframe frameborder="0" id="GRMdivFrame"></iframe>';
        $(document.body).insert(new Element("div", {
            id: b
        }).setStyle("width:" + d.winWidth + "px; height:" + d.winHeight + "px; background:white; z-index:1000;").update(c));
        $(b).absPosition("center");
        $("GRMdivFrame").setStyle("width:" + d.winWidth + "px; height:" + d.winHeight + "px");
        $("GRMdivFrame").src = a
    },
    clearMask: function(){
        if (window.parent.location.href != window.location.href) {
            window.parent.$(this.maskID).remove();
            window.parent.$("GRMaskWin").remove()
        } else {
            if ($(this.maskID)) {
                $(this.maskID).remove();
                $("GRMaskWin").remove()
            }
        }
    },
    copyText: function(b){
        var a = Object.extend({
            success: "Copy Successfully!",
            failure: "Your Browser does not support the method!"
        }, arguments[1] || {});
        if (window.clipboardData) {
            window.clipboardData.setData("text", b);
            alert(a.success)
        } else {
            alert(a.failure)
        }
    },
    mixArray: function(c){
        var b = [];
        var a = c.length;
        c.each(function(e, d){
            var f = Math.floor(Math.random() * (a - d));
            b[d] = c[f];
            c[f] = c[a - 1 - d]
        });
        return b
    }
});
var W = new obj();
document.observe("dom:loaded", W.newWin);
var myExp = {
    pattern_DB: /[^\x00-\xff]/g,
    pattern_CN: /[\u4e00-\u9fa5]/,
    pattern_CN2: /^[\u4e00-\u9fa5]+$/,
    pattern_NB: /^\d+$/,
    pattern_FN: /^\d+\.\d+$/,
    pattern_EM: /^\w(\w*\.*-*)*@([\w-]+\.)+\w{2,4}$/,
    pattern_UL: /^(http|https|ftp):\/\/.*/
};
if (!document.all) {
    document.all = document.getElementById
}
Object.extend(String.prototype, {
    hasCN: function(){
        return myExp.pattern_CN.test(this)
    },
    isCN: function(){
        return myExp.pattern_CN2.test(this)
    },
    isInt: function(){
        return myExp.pattern_NB.test(this)
    },
    isFloat: function(){
        return myExp.pattern_FN.test(this)
    },
    isEmail: function(){
        return myExp.pattern_EM.test(this)
    },
    isURL: function(){
        return myExp.pattern_UL.test(this)
    },
    isDate: function(){
        var e = this.gsub(/\.|-|#/, "");
        if (e.length == 6) {
            e = "19" + e
        }
        if (e.length == 8) {
            var f = e.substring(0, 4) - 0;
            var a = e.substring(4, 6) - 0;
            var c = e.substring(6) - 0;
            var b = new Date(f, a - 1, c);
            if ((b.getMonth() != a - 1) || (b.getDate() != c)) {
                return false
            } else {
                return true
            }
        } else {
            return false
        }
    },
    isLeapYear: function(){
        if (this.isInt()) {
            var a = parseInt(this, 10);
            if (a % 400 == 0 || (a % 100 != 0 && a % 4 == 0)) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    },
    lenB: function(){
        len = this.replace(myExp.pattern_DB, "aa").length;
        return len
    },
    left: function(a){
        return this.substring(0, a)
    },
    right: function(a){
        return this.substring(this.length - a)
    },
    cutStr: function(f){
        var a = arguments[1] || "...";
        var e = this.length;
        var d = 0;
        var c = f;
        var b = "";
        if (e < c / 2) {
            b = this
        } else {
            $R(0, e, true).each(function(g){
                var h = this.charCodeAt(g);
                d += 1;
                if (d > c) {
                    b += a;
                    throw $break
                }
                if (h < 0 || h > 255) {
                    d++
                }
                b += this.charAt(g)
            }, this)
        }
        return b
    },
    fillChar: function(c, a){
        var b = "";
        if (this.length < c) {
            var e = c - this.length;
            for (var d = 0; d < e; d++) {
                b += a
            }
            return b + this
        } else {
            return this
        }
    },
    isIDCard: function(e){
        var b = [11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82, 91];
        var c, i;
        var g = parseInt(this.left(2), 10);
        var a = this.toArray().map(function(k){
            return parseInt(k, 10)
        });
        switch (this.length) {
            default:
                c = 1;
                break;
            case 15:
                if (b.indexOf(g) == -1) {
                    return c = 4
                }
                i = "19" + this.substr(6, 6);
                if (i.toString().isDate()) {
                    c = 0
                } else {
                    c = 2
                }
                break;
            case 18:
                if (b.indexOf(g) == -1) {
                    return c = 4
                }
                i = this.substr(6, 8);
                if (i.isDate()) {
                    var d = (a[0] + a[10]) * 7 + (a[1] + a[11]) * 9 + (a[2] + a[12]) * 10 + (a[3] + a[13]) * 5 + (a[4] + a[14]) * 8 + (a[5] + a[15]) * 4 + (a[6] + a[16]) * 2 + a[7] * 1 + a[8] * 6 + a[9] * 3;
                    var h = "10X98765432".substr(d % 11, 1);
                    if (h == a[17]) {
                        c = 0
                    } else {
                        c = 3
                    }
                } else {
                    c = 2
                }
                break
        }
        var f = arguments[0] || false;
        if (f) {
            var j = ["\u6960\u5c83\u7609\u95ab\u6c33\u7e43\u951b\ufffd", "\u97ec\ue0a1\u5524\u7487\u4f78\u5f7f\u942e\u4f77\u7d85\u93c1\u9881\u7b09\u7035\u7678\u7d12", "\u97ec\ue0a1\u5524\u7487\u4f78\u5f7f\u942e\u4f78\u56ad\u9422\u71b8\u68e9\u93c8\u71bb\u79f4\u9351\u9e3f\u5bd6\u9365\u5b58\u57a8\u935a\ue0a3\u6e41\u95c8\u70b4\u7876\u701b\u6943\ue0c1\u951b\ufffd", "\u97ec\ue0a1\u5524\u7487\u4f78\u5f7f\u942e\u4f79\u724e\u6960\u5c84\u654a\u7487\ue224\u7d12", "\u97ec\ue0a1\u5524\u7487\u4f78\u6e74\u9356\u8f70\u552c\u942e\u4f80\u654a\u7487\ue224\u7d12"];
            return j[c]
        } else {
            return (c == 0) ? true : false
        }
    }
});
Object.extend(Array.prototype, {
    mixArray: function(){
        return W.mixArray(this)
    }
});
