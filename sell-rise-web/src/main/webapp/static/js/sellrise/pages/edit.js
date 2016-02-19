function artistEditInit() {
    $("div[name='div-domain']").each(function () {
        var type = $(this).attr("type");
        var genericType = $(this).attr("genericType");
        var fieldname = $(this).attr("fieldname");
        var reffield = $(this).attr("reffield");
        var value = $("#div-value-" + fieldname).html();
        console.log("type=" + type + ",reffield=" + reffield + ",genericType=" + genericType);
        switch (type) {
            case "ratio":
                $("input[name='" + fieldname + "'][value='" + value + "']").attr("checked", "checked");
                return;
            case "timestamp":
                if (Number(value) != 0) {
                    $("input[name='" + fieldname + "']").val(formatDate(new Date(Number(value))));
                    $("#timeshow-" + fieldname).val(formatDate(new Date(Number(value))));
                }
                return;
            case "img":
                var imglist = eval(value);
                for (var x in imglist) {
                    var imageInfo = getImageAjax(imglist[x]);
                    //$("input[name='" + fieldname + "']").val(value);
                    var imgHtml = "<div id='div_img_" + imageInfo.id + "'><img class='img-circle' src='" + imageInfo.imageUrl + "'/>";
                    imgHtml += "<a class='glyphicon glyphicon-trash btn' title='删除' href='javascript:void(0)'  onclick='deleteImgObj(" + imageInfo.id + ")'></a>";
                    imgHtml += "<div>" + imageInfo.imageRemark + "</div><input type='hidden' name='" + fieldname + "' value='" + imageInfo.id + "'/>";
                    imgHtml += "</div>";
                    $("#help-block").append(imgHtml);
                }
                return;
            case "search.id":
                var sclist = eval(value);
                for (var x in sclist) {
                    $("#" + fieldname).after("<input type='text' class='form-inline digits' name='" + fieldname + "' value='" + sclist[x] + "' readonly='true'/> ");
                }
                return;
            case "search.name":
                var $searchid = $("#" + reffield);
                $searchid.hide();
                var sclist = eval(value);
                for (var x in sclist) {
                    var showHtml = "";
                    showHtml += "<span class='text-warning'>" + sclist[x] + "</span>";
                    showHtml += "<input type='hidden' name='" + fieldname + "' value='" + sclist[x] + "' readonly='true'/> ";
                    $searchid.after(showHtml);
                }
                return;
            default :
                ;
        }
        console.log("#######genericType=" + genericType);
        switch (genericType) {
            case "java.util.List<java.lang.String>":
            case "java.util.List<java.lang.Long>":
                var objlist = eval(value);
                var $input = $("input[name='" + fieldname + "']");
                for (var x in objlist) {
                    if (x != 0) {
                        $input.after($input.clone());
                    }
                }
                for (var x in objlist) {
                    $("input[name='" + fieldname + "']:eq(" + x + ")").val(objlist[x]);
                }
                break;
            default:
                $("input[name='" + fieldname + "']").val(value);
        }
    });
}

$(function () {
    try {
        console.log("edit.js init");
        artistEditInit();
        $("#li-add").html("<a href='#'>编辑</a>");
    } catch (err) {
        console.log(err);
    }
});
