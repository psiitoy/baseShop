var staticInfoMap = {};
function addInit() {
    $("#li-list").toggleClass("active");
    $("#li-add").toggleClass("active");
    fixCalender();
    bind();
    //loadStaticInfo($("#domainname").val());
}

function bindSearchAutoComplete(domainname) {
    switch (domainname) {
        case "artist":
            $("input[search='orgIds']").keyup(function () {
                var map = loadLikeByQuery("organization", "orgIds", "name", $(this).val());
                $(this).autocomplete({
                    source: staticInfoMap["orgIds"].keySet()
                });
            });
            break;
        case "artistWork":
            $("input[search='orgIds']").keyup(function () {
                var map = loadLikeByQuery("organization", "orgIds", "name", $(this).val());
                $(this).autocomplete({
                    source: staticInfoMap["orgIds"].keySet()
                });
            });
            $("input[search='artistId']").keyup(function () {
                var map = loadLikeByQuery("artist", "artistId", "name", $(this).val());
                $(this).autocomplete({
                    source: staticInfoMap["artistId"].keySet()
                });
            });
            break;
    }
}

//获取信息
function loadLikeByQuery(domainname, searchId, paramName, inputValue) {
    var artstMap = new Map();
    $.ajax({
        type: "GET",
        async: false,
        url: "/" + domainname + "/likeQueryByName",
        jsonParams: "{" + paramName + ":'" + inputValue + "'}",
        dataType: "json",
        success: function (domainList) {
            if (domainList != null) {
                //console.log(JSON.stringify(domainList));
                for (var x in domainList) {
                    var domain = domainList[x];
                    artstMap.put(domain.name, domain.id);
                }
                staticInfoMap[searchId] = artstMap;
            } else {
                alert("loadArtstInfos fail");
            }
        }
    });
    return artstMap;
}

function bind() {
    $("span[name='addInputBtn']").click(function () {
        try {
            var domainname = $("#domainname").val();
            var zhname = $(this).attr("zhname");
            var link = $(this).attr("linkfor");
            var $currentInput = $("input[link='" + link + "']");
            var val = $currentInput.val();
            var fieldName = $currentInput.attr("fieldName");
            var search = $currentInput.attr("search");
            //不是模糊搜索
            if (search == null) {
                $(this).after("<input type='text' class='form-inline required' name='" + fieldName + "' value='" + val + "' readonly='true'/> ");
                return;
            } else if (val == "") {
                throw (zhname + "请输入值");
            } else if (!$currentInput.valid()) {
                throw (zhname + "输入值不合法");
            } else if (search != "") {
                var mapgetvalue = staticInfoMap[search].get(val);
                if (mapgetvalue == null) {
                    throw (zhname + " 不在数据库内");
                }
                var $searchid = $("#" + search);
                var genericType = $searchid.attr("genericType");
                if (genericType == "class java.lang.Long") {
                    if ($("input[name='" + search + "']").length > 0) {
                        throw (zhname + " 只能设置单一值");
                    }
                }
                $searchid.siblings("input").each(function () {
                    var title = $(this).attr("title");
                    if (title == val) {
                        throw (zhname + "重复值[" + val + "]");
                    }
                });
                $searchid.hide();
                var readonlyinput = "";
                readonlyinput += "<span class='text-warning'>" + val + "</span>";
                readonlyinput += "<input type='text' class='form-inline digits' name='" + search + "' value='" + mapgetvalue + "' title='" + val + "' readonly='true'/> ";
                readonlyinput += "<input type='hidden' name='" + fieldName + "' value='" + val + "' readonly='true'/> ";
                $searchid.after(readonlyinput);
            }
        } catch (e) {
            alert(e);
        }
    });

    $("span[name='addInputFileBtn']").click(function () {
        var file_link_id = $(this).attr("file-link-id");
        ajaxFileUpload(file_link_id);
    });

    bindSearchAutoComplete($("#domainname").val());
}

function deleteImgObj(imgId) {
    if (confirm("确定要删除张图片吗？")) {
        $.ajax({
            type: "GET",
            async: false,
            url: "/imageInfo/delete/" + imgId,
            dataType: "json",
            success: function (code) {
                if (code >= 0) {
                    $("#div_img_" + imgId).remove();
                } else {
                    alert("deleteImgObj fail");
                }
            }
        });
    }
}

function ajaxFileUpload(file_link_id) {
    $("#" + file_link_id).attr("name", "myfiles");
    var remark = $("#imageremark_" + file_link_id).val();
    if (remark == null || remark == "") {
        alert("图片备注不能为空");
        return;
    }
    $.ajaxFileUpload
    (
        {
            url: '/upload/' + $("#domainname").val() + "/" + file_link_id + "/" + remark, //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: file_link_id, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
                if (data.success) {
                    alert("图片上传成功");
                    var imageInfo = data.obj;
                    var imgHtml = "<div id='div_img_" + imageInfo.id + "'><img class='img-circle' src='" + imageInfo.imageUrl + "'/>";
                    imgHtml += "<a class='glyphicon glyphicon-trash btn' title='删除' href='javascript:void(0)'  onclick='deleteImgObj(" + imageInfo.id + ")'></a>";
                    imgHtml += "<div>" + imageInfo.imageRemark + "</div><input type='hidden' name='" + file_link_id + "' value='" + imageInfo.id + "'/>";
                    imgHtml += "</div>";
                    $("#help-block").append(imgHtml);
                    //$("#up-img-" + file_link_id).attr("src", data.obj.imageUrl);
                    //$("#remark-img-" + file_link_id).html(data.obj.imageRemark);
                    //$("input[name='" + file_link_id + "']").val(data.obj.imageId);
                } else {
                    alert(data.msg);
                }
                $("#" + file_link_id).removeAttr("name");
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                alert(e);
                $("#" + file_link_id).removeAttr("name");
            }
        }
    )
    return false;
}

function beforeSubmitCheck() {
    try {
        var domainname = $("#domainname").val();
        //设置所有时间控件
        $("input[dt=true]").each(function () {
            var val = $(this).val();
            if (val != "") {
                if (val == "NaN") {
                    $("input[dt=true]").val("");
                    throw "重置所有时间字段，请手动更改";
                }
                var remindTime = $(this).val() + " 00:00:00";
                var str = remindTime.toString();
                str = str.replace(/-/g, "/");
                var longdate = Date.parse(str);
                if (longdate == "NaN") {
                    throw "请重新选择所有时间";
                }
                $(this).val(longdate);
            } else {
                $(this).val(0);
            }
        });
        return true;
    } catch (e) {
        alert(e);
        return false;
    }
}
$(function () {
    try {
        console.log("add.js init");
        addInit();
        $("#form-add").validate();
    } catch (err) {
        console.log(err);
    }
});
