/**
 * 
 */
/**下拉刷新
//添加'refresh'监听器
$(document).on('refresh', '.pull-to-refresh-content',function(e) {
    // 模拟2s的加载过程
    setTimeout(function() {
        var cardHTML = '<li class="item-content"><div class="item-inner"><div class="item-title">Item </div></div></li>';

        $(e.target).find('.list-container').html(cardHTML);
        // 加载完毕需要重置
        $.pullToRefreshDone('.pull-to-refresh-content');
    }, 2000);
    
});
 $(document).on("pageAnimationStart", "#router2", function(e, pageId, $page) {
	 console.log(e._args)
 });
  $.init();  
*/

function getPhoneType(){
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if(isiOS){
		return "ios";
	}else if(isAndroid){
		return "android";
	}else{
		return "";
	}
}
//setUser("zhangjiabin","202d3d88dfd8a59442f4ea35d6275fcd");
//调用app方法获取用户信息
function getUser(){
	var phoneType=getPhoneType();
	if(phoneType=="ios"){
		window.webkit.messageHandlers.getUser.postMessage("");
	}else if(phoneType=="android"){
		window.AndroidWebView.getUser();
	}
}
//app调用我的方法设置信息
function setUser(userCode,sign){
	  	sessionStorage.setItem("userCode",userCode);
	  	sessionStorage.setItem("sign",sign);
	  	getData(userCode,sign);
}
//设置经纬度
function jsenterCheck() {
	var jwd="";
	var phoneType=getPhoneType();
	if(phoneType=="ios"){
		jwd=window.webkit.messageHandlers.getJwd.postMessage("");
	}else if(phoneType=="android"){
		jwd=window.AndroidWebView.getJwd();
	}
	
  }
function setJwd(jd,wd){
	sessionStorage.setItem("jd",jd);
	sessionStorage.setItem("wd",wd);
}
//调用app 展示图片
function showimg(index,imgs){
	 var phoneType=getPhoneType();
		if(phoneType=="ios"){
			window.webkit.messageHandlers.showPicture.postMessage(index+","+imgs);
		}else if(phoneType=="android"){
			window.AndroidWebView.showPicture(imgs,index);
		}
}
//调用app 播放视频
function showvideo(url){
	 var phoneType=getPhoneType();
	if(phoneType=="ios"){
		window.webkit.messageHandlers.showVideo.postMessage(url);
	}else if(phoneType=="android"){
		window.AndroidWebView.showVideo(url);
	}
}

function getPowerDep(path,userCode,sign){
	$.post(path, 
				{ userCode: userCode,sign:sign }, 
				function(data){
					data=JSON.parse(data);
					tree(data);
			})
}
function clear(type){
	sessionStorage.removeItem("userId"); 
	sessionStorage.removeItem("userMsg"); 
	sessionStorage.removeItem("noAgree");
}

function getFront(mainStr,searchStr){ 
	foundOffset=mainStr.indexOf(searchStr); 
	if(foundOffset==-1){ 
	return null; 
	} 
	return mainStr.substring(0,foundOffset); 
	}
function getEnd(mainStr,searchStr){ 
	foundOffset=mainStr.indexOf(searchStr); 
	if(foundOffset==-1){ 
	return null; 
	} 
	return mainStr.substring(foundOffset+searchStr.length,mainStr.length); 
	} 
//在字符串 searchStr 前面插入字符串 insertStr 
function insertString(mainStr,searchStr,insertStr){ 
	var front=getFront(mainStr,searchStr); 
	var end=getEnd(mainStr,searchStr); 
	if(front!=null && end!=null){ 
	return front+insertStr+searchStr+end; 
	} 
	return null; 
}

