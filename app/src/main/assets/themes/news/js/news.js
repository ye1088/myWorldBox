var API = 'http://info.mc.huluxia.com/news/detail/ANDROID/1.0';
var isDebug = false

var timeOutEvent = 0;

$(function(){

	// 超时时间设置
	$.ajaxSetup({
		timeout: 10000 // 10s
	});

	// 请求数据
	requestPage();

	// 预显示数据
	preDisplay();
});

// 请求页面数据
function requestPage() {

	$('.loading').show();
	$('.err').hide();

	$.getJSON(getUrl(), function(data){
		var title = data['result']['title']
		var imgs = data['result']['imgs']
		var content = data['result']['content']
		var tag = data['result']['tag']
		var publishTime = getDateDiff(new Date(data['result']['publishTime']))
		var cmtCount = data['result']['cmtCount']
		content = formatContent(content, imgs)

		$('.article-title').html(title)
		$('.text').html(content)
		$('.source').html(tag)
		$('.time').html(publishTime)

		// 绑定图片延迟加载
		$(".lazy").Lazy({
			effect: "fadeIn",
			effectTime: 500,
			// beforeLoad - before item is about to be loaded
			// afterLoad - after the item was loaded successfully
			// onError - whenever an item could not be loaded
			// onFinishedAll - after all items in instance was loaded or returned an error
			defaultImage: null,
			beforeLoad: function(element) {
				//console.log("beforeLoad " + element.prop("tagName"));
			},
			afterLoad: function(element) {
				//console.log("afterLoad " + element.prop("tagName"));
				
				element.css('height', 'auto')
				
				var pageH = $(document).height()
				if (!isDebug) {
					window.obj.contentHeight('' + pageH)
				}
			},
			onError: function(element) {
				//console.log("onError " + element.prop("tagName"));
				element.removeClass('pic_loading').addClass('pic_error')
				bindImgManualReload(element)
			},
			onFinishedAll: function(element) {
				console.log("onFinishedAll");
			},
		});

		// 视频适应宽度
		var f_w = $('.text.clearfix').width()
		$('video').css('width', f_w+'px')

		$('.pageMain').show();

		// 图片列表资源
		var srcList = ''
		var srcIndexFirst = true
		$('.lazy').each(function(){
			var src = $(this).attr('data-src');
			if (typeof(src) == "undefined") {
				src = $(this).attr('src');
			}
			if (srcIndexFirst) {
				srcList = src;
				srcIndexFirst = false;
			} else {
				srcList += ',' + src;
			}
		})
			
		// 绑定图片点击，调用客户端打开图片事件
		$('.lazy').click(function() {
			var imgsrc = $(this).attr('src')
			if (isDebug) {
				alert(imgsrc + '\n\n\n' + srcList)
			} else {
				window.obj.openImage(imgsrc, srcList)
			}
		});

		// 绑定图片长按事件，调用客户端下载图片
		/*$('.lazy').on({
			touchstart: function(e) {
				var imgsrc = $(this).attr('src')
				timeOutEvent = setTimeout("onImageLongPress('" + imgsrc + "')", 520);
				e.preventDefault();  
			},  
			touchmove: function() {
				clearTimeout(timeOutEvent);
				timeOutEvent = 0;   
			},  
			touchend: function() {
				clearTimeout(timeOutEvent);  
				if(timeOutEvent != 0) {
					// alert("你这是点击，不是长按");   
				}   
				return false;   
			}  
		}) */
    
	}).fail( function(d, textStatus, error) {
        onPageLoadFail(error)
    }).always(function(d) {
		$('.loading').hide();
		
		// 通知客户端页面高度
		var pageH = $(document).height()
		if (!isDebug) {
			window.obj.contentHeight('' + pageH)
		}
	});
}

// 图片长按
function onImageLongPress(imgsrc){   
    timeOutEvent = 0;   
    if (isDebug) {
		alert(imgsrc)
	} else {
		window.obj.downloadImage(imgsrc)
	} 
}   

// 页面加载失败
function onPageLoadFail(error) {
	$('.err').show();

	if ('timeout' == error) {
		$('.err .msg').text('访问超时！请稍后点击重试')
	} else {
		$('.err .msg').text('加载失败！请检查网络后重试')
	}

	$('.err input').bind('click', function(){
		$(this).unbind('click');
		requestPage();
	});
}

// 绑定图片手动加载
function bindImgManualReload(img) {
	img.bind('click', function(){
		$(this).unbind('click');

		$(this).removeClass('pic_error').addClass('pic_loading')
		img.attr('src', img.attr('data-src'))
	});
	img.bind('error', function() {
		$(this).unbind('error');

		$(this).attr('src', 'img/pixel.gif')
		$(this).removeClass('pic_loading').addClass('pic_error')
		bindImgManualReload($(this))
	})
}

// 预显示数据
function preDisplay() {
	var extras = "{}";
	if (!isDebug) {
		extras = window.obj.getExtras();
	}
	var extrasObj = JSON.parse(extras);
	var title = extrasObj['title']
	var tag = extrasObj['tag']
	$('.article-title').html(title)
	$('.source').html(tag)
}

// 组装请求的url
function getUrl() {
	var qStr = "info_id=123305";
	if (!isDebug) {
		qStr = window.obj.getQueryStr();
	}
	var url = API;

	if (url.indexOf('?') < 0) {
		url = url + '?' + qStr
	} else {
		url = url + qStr
	}
	return url
}

// 替换内容中的图片标签
function formatContent(c, imgs) {

	// 获取容器宽度
	var f_w = $('.text.clearfix').width()
	var f_h = f_w * (1-0.618)

	for (var i=0;i<imgs.length;i++) {
		var img = imgs[i];
		var url = img['src'];
		// var w = img['pixel'].split('*')[0];
		// var h = img['pixel'].split('*')[1];

		var replacement = format('<img class="lazy pic_loading" data-src="%1" src="img/pixel.gif" style="width:%2px;height:%3px;"/>', url, f_w, f_h)
		
		c = c.replace(img['ref'], replacement);
	}
	return c
}

// 格式化时间
function formatTime(timestamp) {
	return new Date(timestamp).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

// 时间戳转换
Date.prototype.MMDDHHMM = function () {
	var mm = (this.getMonth() + 1).toString(),
		dd = this.getDate().toString(),
		hh = this.getHours().toString(),
		min = this.getMinutes().toString();
	return (mm[1] ? mm : "0" + mm[0]) + '-' +  (dd[1] ? dd : "0" + dd[0]) + ' ' +  (hh[1] ? hh : "0" + hh[0]) + ':' +  (min[1] ? min : "0" + min[0]);
};

// 计算时间差
function getDateDiff(dateTimeStamp) {
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();
    var diffValue = now - dateTimeStamp;
    if (diffValue < 0) {
        return;
    }
    var monthC = diffValue / month;
    var weekC = diffValue / (7 * day);
    var dayC = diffValue / day;
    var hourC = diffValue / hour;
    var minC = diffValue / minute;
    if (monthC >= 1) {
        result = "" + parseInt(monthC) + "月前";
    } else if (weekC >= 1) {
        result = "" + parseInt(weekC) + "周前";
    } else if (dayC >= 1) {
        result = "" + parseInt(dayC) + "天前";
    } else if (hourC >= 1) {
        result = "" + parseInt(hourC) + "小时前";
    } else if (minC >= 1) {
        result = "" + parseInt(minC) + "分钟前";
    } else
        result = "刚刚";
    return result;
}

// 字符串格式化
function format(string) {
	var args = arguments;
	var pattern = new RegExp("%([1-" + arguments.length + "])", "g");
	return String(string).replace(pattern, function(match, index) {
	return args[index];
	});
};