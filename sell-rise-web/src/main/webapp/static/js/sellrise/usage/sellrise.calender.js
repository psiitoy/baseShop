function fixCalender(){
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    $("input").css("z-index","1");
    $('.glyphicon-remove').click(function(){
        $(this).closest("div").find("input").val("");
    });
}