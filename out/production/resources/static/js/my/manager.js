/**************树*************/
var setting = {
			check: {
				enable: true,//checkbox树
				chkboxType: {"Y":"ps", "N":"ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: onCheck
			}
		};
    function showMenu(obj) {
		var width = $(obj).outerWidth();
		$(obj).siblings(".combo-tree").css({maxWidth:width + "px",width:width+"px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$(".combo-tree").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "ztreeinp" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var id=e.target.id;
		if(typeof($("#"+id).parent(".combo-tree").html()) != "undefined"){
			$("#"+id).parent(".combo-tree").siblings(".control-tree").val(v);
		}
	}

/************设置表格高度*******************/
function getIEVersion(){
	var version = false;
	if(navigator.userAgent.indexOf("MSIE")>0){   
        if(navigator.userAgent.indexOf("MSIE 9.0")>0){  
      	  version = true;
        }   
      }
	return version;
}

function getHeight(){
	var height=$("#ibox").height()-$("#ibox-title").height()-$("#pager_list_1").height()-120;
    //判断是否是ie9
    if(getIEVersion()){
    	 height=height-50; 
    }
    return height;
}


function getData(url,args){
	$.ajax({
		url : url,
		type : 'POST',
	//	timeout : 5000,
		data:args,
		dataType : "json",
		success:function(data,textStatus,jqXHR){
			setArg(data);
	    },
	    error:function(xhr,textStatus){
	    }
	});
}

function getNowDate(){
	var date = new Date();
 	var now = date.getFullYear();
 	if(date.getMonth()+1 < 10 ){
 		now = now + '-0'+ (date.getMonth()+1);  
 	}else{
 		now = now + '-'+ (date.getMonth()+1);  
 	}
 	if(date.getDate() < 10 ){
 		now = now + '-0'+ date.getDate(); 
 	}else{
 		now = now + '-'+ date.getDate(); 
 	}
 	return now;
}
//日期加
function addDate(days){
    var d=new Date(); 
    d.setDate(d.getDate()+days); 
    var m=d.getMonth()+1; 
    if(m<10){
    	m="0"+m;
    }
    var y=d.getDate();
    if(y<10){
    	y="0"+y;
    }
    return d.getFullYear()+'-'+m+'-'+y; 
}
function DateDiff(d1,d2){
    var day = 24 * 60 * 60 *1000;
	try{    
	        var dateArr = d1.split("-");
	   var checkDate = new Date();
	        checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
	   var checkTime = checkDate.getTime();
	  
	   var dateArr2 = d2.split("-");
	   var checkDate2 = new Date();
	        checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
	   var checkTime2 = checkDate2.getTime();
	    
	   var cha = (checkTime - checkTime2)/day;  
	        return cha;
	    }catch(e){
	   return false;
	}
}
function DateAdd(interval, number, date) {
    switch (interval) {
    case "y ": {
        date.setFullYear(date.getFullYear() + number);
        return date;
        break;
    }
    case "q ": {
        date.setMonth(date.getMonth() + number * 3);
        return date;
        break;
    }
    case "m ": {
        date.setMonth(date.getMonth() + number);
        return date;
        break;
    }
    case "w ": {
        date.setDate(date.getDate() + number * 7);
        return date;
        break;
    }
    case "d ": {
        date.setDate(date.getDate() + number);
        return Format(date,"yyyy-MM-dd");
        break;
    }
    case "h ": {
        date.setHours(date.getHours() + number);
        return date;
        break;
    }
    case "m ": {
        date.setMinutes(date.getMinutes() + number);
        return date;
        break;
    }
    case "s ": {
        date.setSeconds(date.getSeconds() + number);
        return date;
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date;
        break;
    }
    }
}
function Format(now,mask)
{
    var d = now;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};

