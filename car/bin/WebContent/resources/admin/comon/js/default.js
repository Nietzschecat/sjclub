$.fn.switching = function() {
    return $(this).each(function() {
        var btn = $(this).find('.SwitchBtn');
        var con = $(this).find('.SwitchCon');
        btn.on('click',function(){
            var inv = btn.index($(this));
            btn.removeClass('in');
            $(this).addClass('in');
            con.removeClass('in');
            con.eq(inv).addClass('in');
        });
    });
};

var reverseChild =function(arr) {
    var list=new Array();
    var total = arr.length;
    $.each(arr,function(i) {
        list.push(arr[((total-1)-i)]);
    });
    return list;
}
$(function(){
    $('.Switch').switching();
    $('#nav .has-children .nav-a').on('click',function(){
        $(this).parents('.has-children').toggleClass('in');
    });
	/*$('#nav .has-children .nav-a').on('click',function(){
        var lis=$(this).parents("#nav").find(".has-children");
        $.each(lis,function(){
        	$(this).removeClass("in");
		});
		$(this).parents('.has-children').toggleClass('in');
	});*/
    $('.Popup .popup-remove').on('click',function(){
        $(this).parents('.Popup').removeClass('show');
    });
    $('#add').on('click',function(){
        $('.popup-add').addClass('show');
    });

});