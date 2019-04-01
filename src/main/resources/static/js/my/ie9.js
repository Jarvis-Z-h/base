/**
 * 
 */
$("#side-menu li").click(function(){
	var aria = $(this).children("ul").attr("show");
	if(typeof(aria) == "undefined" && !$(this).children("ul").hasClass("in")){
		$(this).children("ul").addClass("in");
		$(this).children("ul").attr("show","true");
	}else{
	//	$(this).children("ul").removeClass("in");
	//	$(this).children("ul").attr("aria-expanded","false");
	}
	
	
	
});
 