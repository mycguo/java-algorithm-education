(function($){

  	$.fn.vCenter = function(options) {
	    var pos = {
	      sTop : function() {
	        return window.pageYOffset || document.documentElement && document.documentElement.scrollTop ||	document.body.scrollTop;
	      },
	      wHeight : function() {
	        return window.innerHeight || document.documentElement && document.documentElement.clientHeight || document.body.clientHeight;
	      }
	    };
	    return this.each(function(index) {
	      if (index == 0) {
	        var $this = $(this);
	        var elHeight = $this.height();
			    var elTop = pos.sTop() + (pos.wHeight() / 2) - (elHeight / 2);
	        $this.css({
	          position: 'absolute',
	          marginTop: '0',
	          top: elTop
	        });
	      }
	    });
	};

  	$.fn.hide_show = function(show) {
  		if (arguments.length == 0)
	  		var show = this.is(':hidden');
		if (show)
			this.fadeIn()
		else
			this.hide();
	};

	$.fn.set_date = function(date) {
		var month = (date.getMonth() < 9) ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = (date.getDate() < 10) ? '0' + date.getDate() : date.getDate();
		$(this).val(month + "/" + day + "/" + date.getFullYear());
	};

	$.fn.placeholder = function() {
		return this.each(function() {
			var $this = $(this);
			if($this.val() === "") {
				$this.val($this.attr("placeholder"));
				$this.css("color","#777777");
			}
			$this.focus(function() {
				if($this.val() === $this.attr("placeholder")) {
					$this.css("color","#000000");
					$this.val("");
				}
			}).blur(function() {
				if($this.val() === "") {
					$this.css("color","#777777");
					$this.val($this.attr("placeholder"));
				}
			});
		});
	}

})(jQuery);

function hotspot_start(show_msg)
{
  	if (arguments.length == 0)
  		show_msg = 1;
	$('.hotspot').each( function() {
		var content = $(this).attr('id') + '_content';
		$('#' + content).hide();
		$(this).click( function() {
			$('#' + content).hide_show();
		});
	});
	$('.hotcheck').each( function() {
		var content = $(this).attr('id') + '_content';
		$('#' + content).hide_show($(this).attr('checked'));
		$(this).change( function() {
			$('#' + content).hide_show($(this).attr('checked'));
		});
	});
	if (show_msg)
	{
		$('span.msg:not(:empty)').each( function() {
			$(this).parents('.hotspot_content').hide_show(1);
		});
	}
}