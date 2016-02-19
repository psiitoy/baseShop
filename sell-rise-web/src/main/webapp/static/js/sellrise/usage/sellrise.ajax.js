(function ($) {
    $.extend({
        // 扩展JQuery的ajax函数
        jdAjax: function (element) {
            var defaultParam = {
                type: "post",
                dataType: "json",
                isWrapParamType: false,
                data: (new Date()).getTime(),
                complete: function (jqXHR, textStatus) {
                    if (jqXHR.status == 200) {
                        var jsonObj = $.parseJSON(jqXHR.responseText);
//                        if (jsonObj && jsonObj.redirect) {
//                            window.location.href = jsonObj.url;
//                            return false;
//                        } else if (jsonObj && jsonObj.message) {
//                            $("#context").html(jsonObj.message);
//                            return false;
//                        }
                        // 如页面中设置了回调函数，把返回的结果传给回调函数
                        if (element.callbackmethod) {
                            element.callbackmethod(jsonObj);
                        }
                    } else {
                        //TODO
                        console.log(jqXHR.status + ":	" + textStatus);// 显示除200以外所有HTTP错误消息
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // 异常错误提示
                    //TODO
                    console.log(jqXHR.status + ":	" + textStatus);// 显示除200以外所有HTTP错误消息
                }
            }
            // defaultParam: jdAjax定义的默认属性；element:用户自己定义的属性可以去修改ajax的默认属性
            var settings = $.extend({}, defaultParam, element);
//            前台校验
            if (element.valid != null && !element.valid) {
                return;
            }
            $.ajax(settings);
        }
    });

})(jQuery);

//(function($) {
//    $.extend({
//        // 分页扩展JQuery的ajax函数
//        jdPager : function(element) {
//            var defaultParam = {
//                type : "post",
//                dataType:"json",
//                isWrapParamType : true,
//                data : (new Date()).getTime(),
//                beforeSend:function(){$("#Pagination").hide();},
//                complete : function(jqXHR, textStatus) {
//                    if (jqXHR.status == 200) {
//                        var jsonObj = $.parseJSON(jqXHR.responseText);
//                        if(jsonObj && jsonObj.role && jsonObj.redirect){
//                            window.location.href = jsonObj.url;
//                            return false;
//                        }else if(jsonObj.role && jsonObj.message){
//                            $("#context").html(jsonObj.message);
//                            return false;
//                        }
//                        if (element.callbackdata) {
//                            element.callbackdata(jsonObj.result[0],jsonObj.status[0]);
//                        }
//                        var count = jsonObj.result[0][element.root][0].totalCount;
//                        if(count!=0){
//                            $("#Pagination").pagination(count, {
//                                jdCallback: element.callbackdata,
//                                callback: element.callback,
//                                prev_text: '<< 上一页',
//                                next_text: '下一页 >>',
//                                go_text: 'Go',
//                                items_per_page:element.data.l,
//                                num_display_entries:4,
//                                current_page:element.data.o,
//                                num_edge_entries:2
//                            });
//                        }
//                        $("#Pagination").show();
//                    }
//                }
//            };
//            // defaultParam: jdAjax定义的默认属性；element:用户自己定义的属性可以去修改ajax的默认属性
//            var settings = $.extend({}, defaultParam, element);
//            if (element.data && settings.isWrapParamType) {
//                settings.data = {
//                    parameters : getJsonString(element.data)
//                };
//            }
//            $.ajax(settings);
//        }
//    });
//})(jQuery);
