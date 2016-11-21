/*(function($){
    $.fn.fixedMenu=function(){
        return this.each(function(){
            var menu= $(this);
            menu.find('ul li > a').bind('mouseover',function(){
            if ($(this).parent().hasClass('active')){
                $(this).parent().removeClass('active');
            }
            else{
                $(this).parent().parent().find('.active').removeClass('active');
                $(this).parent().addClass('active');
            }
            })
        });
    }
})(jQuery);*/

(function($){
    $.fn.fixedMenu=function(){
        return this.each(function(){
			var linkClicked= false;
            var menu= $(this);
			$('body').bind('mouseover',function(){
			
					if(menu.find('.active').size()>0 && !linkClicked)
					{
						menu.find('.active').removeClass('active');
					}
					else
					{
						linkClicked = false; 
					}
			});
			
            menu.find('ul li > a').bind('mouseover',function(){
				linkClicked = true;
				if ($(this).parent().hasClass('active')){
					$(this).parent().removeClass('active');
				}
				else{
					$(this).parent().parent().find('.active').removeClass('active');
					$(this).parent().addClass('active');
				}
            })
        });
    }
})(jQuery);