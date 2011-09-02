$(function(){
	// ajax loading gif...
	$('body').append('<div id="loading" style="width: 60px; height: 60px; left: 50%; z-index: 9999; border: 2px #999 solid; background-color: #FFFFFF">' +
	'<img src="https://fuse.boomtime.com/img/ajax_pending.gif" style="padding: 6px 0 0 6px;"></div>');
	$('#loading').hide();
	$("#loading").ajaxSend(function(){
		$(this).vCenter();
		$(this).show();
	});
	$("#loading").ajaxStop(function(){
		$(this).hide();
	});
});

function ajax(url, data, callback)
{
	$.getJSON(url, data, function(json) {
		if (callback)
			callback(json);
	});
}

// ajax_load...
(function($){
 	$.fn.ajax_load = function(url, data) {
 		return this.each(function() {
 			var obj = $(this);
  			ajax(url, data, function(json) {
   				obj.html(json.html)
  			});
		});
 	}
})(jQuery);

// article carousel
(function($){
 	$.fn.carousel = function(url, data) {
 		return this.each(function() {
 			var carousel = {};
			carousel.main = $(this);
			carousel.content = $(this).children().css({position: "absolute", "top": 0});
			carousel.wrap = carousel.content.wrap('<div class="carousel_wrap"></div>').parent().css({overflow: "hidden", position: "relative"});
			carousel.steps = {
				first: 0, // first step
				count: carousel.content.find("li").length // items count
			};
			// next, previous buttons...
			carousel.previous = $("<div></div>").prependTo(carousel.main)
				.addClass("carousel_control carousel_previous").data("first_step", carousel.steps.count-1);
			carousel.next = $("<div></div>").appendTo(carousel.main)
				.addClass("carousel_control carousel_next").data("first_step", 1);
			// Bind click event on next / prev buttons
			carousel.next.add(carousel.previous).bind("click", function(e){
				slide(e, this, carousel);
			}).hover(
				function(){
					$(this).addClass("hover");
				},
				function(){
					$(this).removeClass("hover");
				}
			);
			carousel.pages = $('.carousel_pages');
			carousel.pages.children().each(function(i) {
				$(this).data("first_step", i);
				$(this).bind("click", function(e) {
					slide(e, this, carousel);
				})
			});
			$(function(){
				carousel.item_width = carousel.content.find("li").css("width");
				carousel.item_width = carousel.item_width.replace("px", "");
				carousel.content.width(carousel.item_width * carousel.steps.count);
				$(".carousel img").css({display: "block"});
				// autoslide...
				window.setTimeout(function(){
					carousel.autoSlideInterval = window.setInterval(function(){
						carousel.next.click();
					}, 7000);
				}, 1000);
				state(0, carousel);
			});
		});
 	}

	function slide(e, btn, carousel)
	{
		var first_step = $(btn).data("first_step");
		// Stop autoslide on user click...
		if (!!e.clientX && carousel.autoSlideInterval)
			window.clearInterval(carousel.autoSlideInterval);
		state(first_step, carousel);
		carousel.content.stop().animate({
			left : -(carousel.item_width * first_step) + "px"
			}, 'slow', 'linear');
	};

	function state(first_step, carousel)
	{
		carousel.previous.data("first_step", first_step - 1);
		carousel.next.data("first_step", first_step + 1);
		if (carousel.previous.data("first_step") < 0 && carousel.steps.count > 1)
			carousel.previous.data("first_step", carousel.steps.count - 1);
		if (carousel.next.data("first_step") >= carousel.steps.count && carousel.steps.count > 1)
			carousel.next.data("first_step", 0);
		carousel.pages.children().each(function(i) {
			if (i == first_step)
				$(this).addClass('carousel_active_page')
			else
				$(this).removeClass('carousel_active_page')
		})
	};

})(jQuery);

(function($){
	$.fn.countDown = function (options) {
		config = {};
		$.extend(config, options);
		diffSecs = this.setCountDown(config);
		if (config.onComplete)
			$.data($(this)[0], 'callback', config.onComplete);
		$('#' + $(this).attr('id') + ' .digit').html('<div class="top"></div><div class="bottom"></div>');
		$(this).doCountDown($(this).attr('id'), diffSecs, 500);
		return this;
	};

	$.fn.setCountDown = function (options) {
		var targetTime = new Date();
		if (options.targetDate)
			targetTime = new Date(options.targetDate.month + '/' + options.targetDate.day + '/' + options.targetDate.year + ' ' + options.targetDate.hour + ':' + options.targetDate.min + ':' + options.targetDate.sec + (options.targetDate.utc ? ' UTC' : ''));
		var nowTime = new Date();
		diffSecs = Math.floor((targetTime.valueOf()-nowTime.valueOf())/1000);
		$.data(this[0], 'diffSecs', diffSecs);
		return diffSecs;
	};

	$.fn.doCountDown = function (id, diffSecs, duration) {
		$this = $('#' + id);
		if (diffSecs <= 0)
		{
			diffSecs = 0;
			if ($.data($this[0], 'timer'))
			{
				clearTimeout($.data($this[0], 'timer'));
			}
		}
		secs = diffSecs % 60;
		mins = Math.floor(diffSecs/60)%60;
		hours = Math.floor(diffSecs/60/60);
		$this.dashChangeTo(id, 'second_dash', secs, duration ? duration : 800);
		$this.dashChangeTo(id, 'minute_dash', mins, duration ? duration : 1200);
		$this.dashChangeTo(id, 'hour_dash', hours, duration ? duration : 1200);
		$.data($this[0], 'diffSecs', diffSecs);
		if (diffSecs > 0)
		{
			e = $this;
			t = setTimeout(function() { e.doCountDown(id, diffSecs-1) } , 1000);
			$.data(e[0], 'timer', t);
		}
		else if (cb = $.data($this[0], 'callback'))
		{
			$.data($this[0], 'callback')();
		}

	};

	$.fn.dashChangeTo = function(id, dash, n, duration) {
		  $this = $('#' + id);

		  for (var i=($this.find('.' + dash + ' .digit').length-1); i>=0; i--)
		  {
				var d = n%10;
				n = (n - d) / 10;
				$this.digitChangeTo('#' + $this.attr('id') + ' .' + dash + ' .digit:eq('+i+')', d, duration);
		  }
	};

	$.fn.digitChangeTo = function (digit, n, duration) {
		if (!duration)
		{
			duration = 800;
		}
		if ($(digit + ' div.top').html() != n + '')
		{

			$(digit + ' div.top').css({'display': 'none'});
			$(digit + ' div.top').html((n ? n : '0')).slideDown(duration);

			$(digit + ' div.bottom').animate({'height': ''}, duration, function() {
				$(digit + ' div.bottom').html($(digit + ' div.top').html());
				$(digit + ' div.bottom').css({'display': 'block', 'height': ''});
				$(digit + ' div.top').hide().slideUp(10);


			});
		}
	};

})(jQuery);