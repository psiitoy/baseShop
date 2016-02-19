function listInit() {
    var authCode = $("#sessionAuthCode").val();
    if (authCode == 1 || authCode == 2) {//1只读 2可编辑 3全部
        $("th[name='delBtnTh']").remove();
        $("td[name='delBtnTd']").remove();
    }
    if (authCode == 1) {
        $("th[name='editBtnTh']").remove();
        $("td[name='editBtnTd']").remove();
    }
    loadImageInfos();
}

function deleteDomainObj(id) {
    if (confirm("确定要删除这条数据吗？")) {
        $.ajax({
            type: "GET",
            async: false,
            url: "/" + $("#domainname").val() + "/delete/" + id,
            dataType: "json",
            success: function (code) {
                if (code >= 0) {
                    $("#tr_" + id).remove();
                } else {
                    alert("deleteDomainObj fail");
                }
            }
        });
    }
}

$(function () {
    try {
        console.log("list.js init");
        console.log("sessionAuthCode=" + $("#sessionAuthCode").val());
        listInit();
        //js版翻页查询 带搜索条件
        $("#table-domain").makePagebar({
            currentPage: $("#table-domain").attr("currentPage"),//当前页面
            totalPages: $("#table-domain").attr("totalPages"), //总页数
            searchFormId: "header-form-search"
        });
        $("#tbody [name='show-img']").lightBox();
    } catch (err) {
        console.log(err);
    }
});