/**
 * @Date 2018年3月15日
 * @Author 刘开阳
 * @Link https://my.oschina.net/javawdw/blog/517500
 * @Content 本JS文件用于在textarea中的光标位置插入指定内容 示例：<textarea id="content"></textarea>
 *          $("#content").insertAtCursor("测试插入文本");
 */
$.fn.extend({
    insertAtCursor : function(myValue) {
        var $t = $(this)[0];
        if (document.selection) {
            this.focus();
            sel = document.selection.createRange();
            sel.text = myValue;
            this.focus();
        } else if ($t.selectionStart || $t.selectionStart == '0') {
            var startPos = $t.selectionStart;
            var endPos = $t.selectionEnd;
            var scrollTop = $t.scrollTop;
            $t.value = $t.value.substring(0, startPos) + myValue
                    + $t.value.substring(endPos, $t.value.length);
            this.focus();
            $t.selectionStart = startPos + myValue.length;
            $t.selectionEnd = startPos + myValue.length;
            $t.scrollTop = scrollTop;
        } else {
            this.value += myValue;
            this.focus();
        }
    }
});