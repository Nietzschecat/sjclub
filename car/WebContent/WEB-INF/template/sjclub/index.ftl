[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>福建师范大学社团管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- 使浏览器默认启用极速模式，但并不是所有浏览器都能正确执行 -->
    <meta name="renderer" content="webkit">
    <!-- 使IE浏览器启用最新的版本 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 使页面宽度与视口宽度相同，并且禁止用户缩放页面 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 页面关键字 -->
    <meta name="keywords" content="">
    <!-- 页面描述 -->
    <meta name="description" content="">
    <link rel="shortcut icon" href="${base}/resources/sjclub/img/icon.ico">
    <link rel="stylesheet" href="${base}/resources/sjclub/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/resources/sjclub/css/global.css">
    <link rel="stylesheet" href="${base}/resources/sjclub/css/sjclub.css">
</head>
<body>
<!-- 加载登录|注册模态框 -->
[#include "/sjclub/include/login.ftl"/]
[#include "/sjclub/include/register.ftl"/]

<!-- navbar -->
[#include "/sjclub/include/navbar.ftl"/]

<!-- slider -->
<!-- img show on here -->
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        [#list activies as activity]
            <div class="item  [#if activity_index==1]active[/#if]">
                <img src="${activity.images}" alt="index-1">
            </div>
        [/#list]
        [#--<div class="item">
            <img src="${base}/resources/sjclub/img/index-2.jpg" alt="index-2">
        </div>
        <div class="item">
            <img src="${base}/resources/sjclub/img/index-3.jpg" alt="index-3">
        </div>--]
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<!-- /.slider -->

<!-- content -->
<div class="container-fluid content">
    <div class="row">
        <!-- content left -->
        <div class="col-md-9 col-xs-12">
            <!-- news -->
            <div class="subfield">
                <h4>最新活动<small>News</small></h4>
                <hr />
            </div>
            <div class="row">
                <!-- news left -->
                <div class="col-md-6 col-xs-12">
                    [#list activies as activity]
                        [#if activity_index%2!=0]
                            <div class="row news">
                                <div class="col-md-6 col-sm-6">
                                    <p>${activity.title}</p>
                                </div>
                                <div class="col-md-6 col-sm-6">
                                    <p>${activity.content}<small>${activity.startTime}</small></p>
                                </div>
                            </div>
                        [/#if]
                    [/#list]
                    </div>
                </div>
                <!-- ./news left -->

                <!-- news right -->
                <div class="col-md-6 col-xs-12 news-right">

                    [#list activies as activity]
                        [#if activity_index%2==0]
                            <div class="row news">
                                <div class="col-md-6 col-sm-6">
                                    <p>${activity.title}</p>
                                </div>
                                <div class="col-md-6 col-sm-6">
                                    <p>${activity.content}<small>${activity.startTime}</small></p>
                                </div>
                            </div>
                        [/#if]
                    [/#list]
                </div>
                <!-- ./news right -->
            </div>
            <!-- ./news -->

            <!-- top ten outstanding associations -->
            <div class="subfield">
                <h4>校十佳社团<small>Top Ten Outstanding Associations</small></h4>
                <hr />
            </div>
            <div class="row">
                [#list clubs as club]
                    [#if club.tenGood=='1']
                        <div class="col-md-3 col-sm-4 col-xs-6">
                            <div class="show-club">
                                <img src="${club.logo}" >
                                [#--<a href="${base}/sjclub/list_detail.jhtml?id=${club.id}">测试说</a>--]
                                <a href="javascript:;">${club.name}</a>
                            </div>
                        </div>
                    [/#if]
                [/#list]
            </div>
            <!-- ./top ten outstanding associations -->

            <!-- special recommendation -->
           [#-- <div class="subfield">
                <h4>特别推荐<small>Special Recommendation</small></h4>
                <hr />
            </div>
            <div>
                aaa
            </div>--]
            <!-- ./special recommendation -->
        </div>
        <!-- ./content left -->

        <!-- content right -->
        <div class="col-md-3 col-xs-12">
            <!-- popularity -->
            [#--<div class="subfield">
                <h4>校园人气榜<small>Popularity</small></h4>
                <hr />
            </div>
            <div>

            </div>--]
            <!-- ./popularity -->

            <!-- links -->
            <div class="subfield">
                <h4>友情链接<small>Links</small></h4>
                <hr />
            </div>
            <div>
                <p><a href="http://www.fjnu.edu.cn/">福建师范大学</a></p>
                <p><a href="http://jwgl.fjnu.edu.cn/">福建师范大学教务管理系统</a></p>
                <p><a href="http://xgxt.fjnu.edu.cn/">福建师范大学学生工作信息管理系统</a></p>
            </div>
            <!-- ./links -->
        </div>
        <!-- ./content right -->
    </div>
</div>
<!-- ./content -->

<!-- footer -->
[#include "/sjclub/include/footer.ftl"]
<script src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script src="${base}/resources/sjclub/js/bootstrap.min.js"></script>
<!-- 数据验证 -->
<script src="${base}/resources/sjclub/js/validate.js"></script>
</body>
</html>
[/#escape]