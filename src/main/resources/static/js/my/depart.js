/**
 * 
 */
 var setting = {
		check: {
			enable: false,
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
			onClick: onClick
		}
	};
function showMenu() {
	var cityObj = $("#cdepName");
	var cityOffset = $("#cdepName").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "cdepName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
function onClick(event, treeId, treeNode) {
	$("#cdepCode").val(treeNode.id);
	$("#cdepName").val(treeNode.name);
	if($("#cdepName").attr("point") == "1"){
		getPointOld(treeNode.id)
	}
}
function treeinit(){
	$("body").append('<div id="menuContent" class="menuContent" style="z-index:10000;" ><ul id="treeDemo" class="ztree checkztree" ></ul></div>');
	var path = window.location.href.split("/jump")[0];
	 $.post(path+"/archives/dep?type=query&rows=100000",
		  {},
		  function(data,status){
			  data=JSON.parse(data);
			 if(typeof data.rows != "undefined" && data.rows.length>0){
				$.fn.zTree.init($("#treeDemo"), setting, data.rows);
			 }
		  });
}
 