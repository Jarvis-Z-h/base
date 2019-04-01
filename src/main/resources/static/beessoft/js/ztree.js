/*
 * 本JS的引入，必须在jQuery之后，只适用于一个页面只有一个树的情况，
 * 如果有多个树，需要为每个树配置下面的JS，id按实际情况替换，下面三个函数必须全部重写，不能只重写其中一个或两个，
 * 多个树的情况下，需要为每个树的包裹层定义一个独立的class属性，然后修改对应的hiddenTree里面的class，这样才能正常关闭树展示
 * 包裹树的元素，必须和触发树展现的元素放在同级，否则不能使用。
 */
/* 使用注意：包裹ztree<ul></ul>的div元素，id和class属性均需要设置为ztree-wrap */
/* 最基本的定义如：<div id="ztree-wrap" class="ztree-wrap"><ul id="" class="ztree"></ul></div>，不能再省略任何id或样式 */

/* 在HTML元素上，将本方法绑定到onclick事件上，css样式中存在ztree-wrap样式的元素，就会由隐藏展示出来，并绑定鼠标点击事件 */
function showTree(event) {
    var width = $(event.target).outerWidth();
    $(event.target).siblings(".ztree-wrap").css({
        maxWidth: width + "px",
        width: width + "px",
        zIndex: "9999"
    }).slideDown("fast");
    $("body").on("mousedown", onBodyDown);
}

/* 将css样式中存在ztree-wrap样式的元素，隐藏，并解除鼠标点击绑定的事件 */
function hiddenTree() {
    $(".ztree-wrap").fadeOut("fast");
    $("body").off("mousedown", onBodyDown);
}

/* 监听鼠标事件，根据鼠标点击位置，判断是否调用hiddenTree()方法 */
function onBodyDown(event) {
    // 如果如果点击不在id为ztree-wrap的元素上，如果点击在ztree-warp元素上查询到id为ztree-wrap的祖先元素的个数小于等于0，则调用hiddenTree()方法
    if (!(event.target.id == "ztree-wrap" || $(event.target).parents("#ztree-wrap").length > 0)) {
        hiddenTree();
    }
}