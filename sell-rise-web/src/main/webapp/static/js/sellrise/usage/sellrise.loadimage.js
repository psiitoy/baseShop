//获取图片信息
function loadImageInfos() {
    $("a[name='show-img']").each(function () {
        var imageId = $(this).attr("imageId");
        var imageInfo = getImageAjax(imageId);
        console.log("imageInfo=" + JSON.stringify(imageInfo));
        $(this).attr("href", imageInfo.imageUrl);
        $(this).find("img").attr("src", imageInfo.imageUrl);
        $(this).find("img").attr("title", imageInfo.imageRemark);
    });
}

function getImageAjax(id) {
    var ret = {};
    $.ajax({
        type: "POST",
        async: false,
        url: "/imageInfo/getAjax/" + id,
        dataType: "json",
        success: function (domain) {
            console.log("domain=" + JSON.stringify(domain));
            if (domain != null) {
                ret = domain;
            } else {
                alert("loadImageInfos fail");
            }
        }
    });
    return ret;
}

function queryByIds(ids) {
    var ret = {};
    $.ajax({
        type: "POST",
        async: false,
        url: "/imageInfo/queryByIds",
        data: {ids: ids},
        dataType: "json",
        success: function (domainList) {
            console.log("domainList=" + JSON.stringify(domainList));
            if (domainList != null) {
                ret = domainList;
            } else {
                alert("loadImageInfos fail");
            }
        }
    });
    return ret;
}