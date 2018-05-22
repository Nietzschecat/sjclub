//显示弹出框出
function showDialog(obj){
    $(".hideDialog").click(function(){
        hideDialog(obj);
    });
    center(obj).show();
    $("#fade").show();
}
//隐藏弹出框出
function hideDialog(obj){
    $(obj).hide();
    $("#fade").hide();
}
//设置弹出框出现的位置
function center(obj) {
    var screenWidth =parseInt($(window).width()), screenHeight = parseInt($(window).height());  //当前浏览器窗口的 宽高
    /*var scrolltop =parseInt($(document).scrollTop());//获取当前窗口距离页面顶部高度*/
    var objLeft =  (screenWidth-parseInt($(obj).css("width")))/2;
    var objTop = (screenHeight-parseInt($(obj).css("height")))/2;
    return $(obj).css({"left": objLeft + 'px', "top": objTop + 'px',"position":"fixed"});
}