function fullpage(options){
    $(function(){
        var preventRepetition = true;//阻止滚轮事件重复触发
        var con = $('.fullpage-content');
        var conNumber = con.length;
        var INV = 0;
        var speed = 500;//速度
        var main = $('.fullpage-main');

        //页面切换
        function filp(object){
            con.removeClass('in');
            if(object.direction=='top'){
                object.current.animate({'top':'100%'},speed);

            }else {
                object.current.animate({'top':'-100%'},speed);
            };
            con.eq(INV).animate({'top':0},speed,function(){
                $(this).addClass('in');
                if(options&&options.end){
                    options.end(INV);
                };
                $('.fullpage-btn').removeClass('in');
                $('.fullpage-btn').eq(INV).addClass('in');
                $('.menu .li1').removeClass('in');
                $('.fullpage-btn').eq(INV).parents('.li1').addClass('in');
                $('.fullpage-content:lt('+INV+')').css('top','-100%');
                $('.fullpage-content:gt('+INV+')').css('top','100%');
                preventRepetition = true;
            });
        };

        var scrollFunc = function (e) {
            var e = e || window.event;
            var direction = function(){
                var direction;
                if (e.wheelDelta) {
                    if (e.wheelDelta > 0) {
                        return 'top';
                    };
                    if (e.wheelDelta < 0) {
                        return 'bottom';
                    };
                } else if (e.detail) {
                    if (e.detail> 0) {
                        return 'bottom';
                    };
                    if (e.detail< 0) {
                        return 'top';
                    };
                };
            }
            if(preventRepetition){
                preventRepetition = false;
                if(direction()=='top'){
                    if(INV > 0){
						if(options&&options.start){
							options.start(INV);
						};
						INV--;
						filp({
							direction:'top',
							current:$('.fullpage-content.in')
						});
                    }else {preventRepetition = true;};
                }else {
                    if(INV<conNumber-1){
                        if(options&&options.start){
                            options.start(INV);
                        };
                        INV++;
                        filp({
                            direction:'bottom',
                            current:$('.fullpage-content.in')
                        })
                    }else {preventRepetition = true;};
                }
            };
        };

        //给页面绑定滑轮滚动事件
        if (document.addEventListener) {
            document.addEventListener('DOMMouseScroll', scrollFunc, false);
        };
        //滚动滑轮触发scrollFunc方法
        window.onmousewheel = document.onmousewheel = scrollFunc;

        //右侧按钮点击
        $('.fullpage-btn').on('click',function(){
            var subscript = $('.fullpage-btn').index($(this));
            if(preventRepetition&&(subscript!==INV)){
                preventRepetition = false;
                if(options&&options.start){
                    options.start(INV);
                };
                if(subscript>INV){
                    INV = subscript;
                    filp({
                        direction:'bottom',
                        current:$('.fullpage-content.in')
                    });
                }else {
                    INV = subscript;
                    filp({
                        direction:'top',
                        current:$('.fullpage-content.in')
                    });
                }
            };
        });
    })
};
