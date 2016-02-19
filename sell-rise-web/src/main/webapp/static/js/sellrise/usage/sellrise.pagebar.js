function connectPagebar(target, options) {
    var $table = $(target);
    $table.after("<span style='font-size:14px;'><ul class='pagination' id='pageUl'></ul></span>");

    var element = $('#pageUl');
    element.bootstrapPaginator(options);
    $("#pageUl a").attr("href", "javascript:void(0);");
    $("#pageUl a").click(function () {
        try {
            var no = $(this).html();
            var toPageNo;
            if (!isNaN(no)) {
                toPageNo = no;
            } else {
                var nowno = $("#pageUl .active a").html();
                nowno = parseInt(nowno);
                if (!isNaN(nowno)) {
                    switch (no) {
                        case "&lt;&lt;":
                            toPageNo = 1;
                            break;
                        case "&lt;":
                            toPageNo = nowno;
                            break;
                        case "&gt;":
                            toPageNo = nowno;
                            break;
                        case "&gt;&gt;":
                            toPageNo = options.totalPages;
                            break;
                    }
                } else {
                    throw("nowno not num");
                }
            }
            if (options.searchFormId != null && "" != options.searchFormId) {
                $("#" + options.searchFormId).submit(function (e) {
                    $(this).append("<input type='hidden' name='pageNo' value='" + toPageNo + "'/>");
                });
                $("#" + options.searchFormId).submit();
            }
        } catch (e) {
            alert(e);
        }
    });
}
/**
 * 基于bootstrap3
 * bootstrapPaginator js版翻页查询 带搜索条件
 * @param settings
 * @returns {*}
 */
$.fn.makePagebar = function (settings) {
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,//当前页面
        numberOfPages: 5,//一页显示几个按钮（在ul里面生成5个li）
        totalPages: 1, //总页数
        searchFormId: ""
    }
    // customise the calendar object
    $.extend(options, settings || {totalPages: 0});
    // attach the calendar to each nominated input element
    return this.each(function () {
        if (this.nodeName.toLowerCase() == 'table') {
            connectPagebar(this, options);
        } else {
            alert("makePagebar must table");
        }
    });
};
