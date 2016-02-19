function formatDate(date) {
    //format = "yyyy-MM-dd hh:mm:ss";
    format = "yyyy-MM-dd";
    var o = {
        "M+": date.getMonth() + 1, //month
        "d+": date.getDate(),    //day
        "h+": date.getHours(),   //hour
        "m+": date.getMinutes(), //minute
        "s+": date.getSeconds(), //second
        "q+": Math.floor((date.getMonth() + 3) / 3),  //quarter
        "S": date.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

//map
function Map(){
    this.container = new Object();
}


Map.prototype.put = function(key, value){
    this.container[key] = value;
}


Map.prototype.get = function(key){
    return this.container[key];
}


Map.prototype.keySet = function() {
    var keyset = new Array();
    var count = 0;
    for (var key in this.container) {
// 跳过object的extend函数
        if (key == 'extend') {
            continue;
        }
        keyset[count] = key;
        count++;
    }
    return keyset;
}


Map.prototype.size = function() {
    var count = 0;
    for (var key in this.container) {
// 跳过object的extend函数
        if (key == 'extend'){
            continue;
        }
        count++;
    }
    return count;
}


Map.prototype.remove = function(key) {
    delete this.container[key];
}