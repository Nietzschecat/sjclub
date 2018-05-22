[#escape x as x?html]
[#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理中心 | 视界</title>
    <link rel="stylesheet" href="${base}/resources/admin/layui/css/layui.css">
    <script src="${base}/resources/admin/layui/layui.js"></script>
    <script src="${base}/resources/sjclub/js/jquery.min.js"></script>
    <script src="${base}/resources/admin/comon/js/jquery.timeTo.min.js"></script>
    <style>
        .time-item{
            position: relative;
            display: inline-block;
            vertical-align: middle;
            line-height: 60px;
        }
        .time-item figcaption{
            display: none;
        }

    </style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">福建师范大学社团管理</div>

        <ul class="layui-nav layui-layout-right">
            [#--<li class="time-item">
                <script>
                    var myDate = new Date();
                    var Week = ['日','一','二','三','四','五','六'];
                    document.write("现在是"+myDate.getFullYear()+"年"+(myDate.getMonth() + 1)+"月"+myDate.getDate()+"日"+"星期"+Week[myDate.getDay()]);
                </script>

            </li>
            <li class="time-item">
                <div id="clock"></div>
            </li>--]
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    [@shiro.principal /]
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" class="userInfo">基本资料</a></dd>
                    <dd><a href="javascript:;" class="modifypsd">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${base}/admin/logout.jhtml">退了</a></li>
        </ul>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 300px;" id="main">
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" id="createSjclub" >创建社团</button>
                        <button class="layui-btn" id="joinSjclub">加入社团</button>
                    </div>
                </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    $("#createSjclub").on("click",function(){
        window.location.href="${base}/admin/common/main.jhtml?type=createSjclub";
    });
    $("#joinSjclub").on("click",function(){
        window.location.href="${base}/admin/common/main.jhtml?type=joinSjclub";
    })

    $(function () {
        $('#clock').timeTo({
            fontSize: 36,
            theme: "black",
            displayCaptions: true,
            fontFamily: 'pan'
        });


    })
</script>
</body>
</html>
[/#escape]