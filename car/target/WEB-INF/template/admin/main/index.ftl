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

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="javascript:;" href="javascript:;">个人中心</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" class="userInfo">个人资料</a></dd>
                        <dd><a href="javascript:;" class="modifypsd">修改密码</a></dd>
                        <dd><a href="javascript:;" id="clubInfo">社团资料</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">社团中心</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" id="clubList">社团列表</a></dd>

                         <dd><a href="javascript:;" id="department">部门列表</a></dd>
                        [#list ["sjclub:user"] as permission]
                            [@shiro.hasPermission name = permission]
                                <dd><a href="javascript:;" id="division">部门</a></dd>
                            [/@shiro.hasPermission]
                        [/#list]
                        <dd><a href="javascript:;" id="clubActivityList">社团活动</a></dd>
                        <dd><a href="javascript:;" id="planActivity">活动安排</a></dd>

                        <dd><a href="javascript:;" id="clubUserList">社团成员</a></dd>

                        <dd><a href="javascript:;" id="clubFile">社团文件</a></dd>
                        [#list ["sjclub:admin"] as permission]
                            [@shiro.hasPermission name = permission]
                                <dd><a href="javascript:;" id="userList">用户资料</a></dd>
                            [/@shiro.hasPermission]
                        [/#list]
                        [#list ["sjclub:manager","sjclub:admin"] as permission]
                            [@shiro.hasPermission name = permission]
                                <dd><a href="javascript:;" id="spotCheckIn">签到管理</a></dd>
                            [/@shiro.hasPermission]
                        [/#list]
                        <dd><a href="javascript:;" id="meeting">会议记录</a></dd>
                        <dd><a href="javascript:;" id="noclass">无课表</a></dd>

                        <dd>
                            <a href="javascript:;" id="electronicVoting">
                                [#list ["sjclub:manager","sjclub:admin"] as permission]
                                    [@shiro.hasPermission name = permission]
                                        投票管理
                                    [/@shiro.hasPermission]
                                [/#list]
                                [#list ["sjclub:user"] as permission]
                                    [@shiro.hasPermission name = permission]
                                        投票
                                    [/@shiro.hasPermission]
                                [/#list]
                            </a>
                        </dd>
                        <dd><a href="javascript:;" id="clubNotice">社团公告</a></dd>

                    </dl>
                </li>


            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;" id="main">

        </div>
    </div>
</div>
<script type="text/javascript">

    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    //默认加载

    $(function () {
        $('#clock').timeTo({
            fontSize: 36,
            theme: "black",
            displayCaptions: true,
            fontFamily: 'pan'
        });

        $("#main").load("${base}/admin/user/getUserInfo.jhtml");

        $(".userInfo").on("click",function(){
            $("#main").load("${base}/admin/user/getUserInfo.jhtml");
        });
        $("#department").on("click",function(){
            $("#main").load("${base}/admin/department/loadList.jhtml");
        });
        $(".modifypsd").on("click",function(){
            $("#main").load("${base}/admin/user/modifypsd.jhtml");
        });
        $("#clubInfo").on("click",function(){
            $("#main").load("${base}/admin/club/clubInfo.jhtml");
        });
        $("#clubList").on("click",function(){
            $("#main").load("${base}/admin/club/loadList.jhtml");
        });
        $("#clubActivityList").on("click",function(){
            $("#main").load("${base}/admin/club_activity/loadList.jhtml");
        });
        $("#clubFile").on("click",function(){
            $("#main").load("${base}/admin/club_file/loadList.jhtml");
        });
        $("#meeting").on("click",function(){
            $("#main").load("${base}/admin/meeting/loadList.jhtml");
        });
        $("#userList").on("click",function(){
            $("#main").load("${base}/admin/user/loadList.jhtml");
        });
        $("#clubUserList").on("click",function(){
            $("#main").load("${base}/admin/club/club_user_list_load.jhtml");
        });

        $("#noclass").on("click",function(){
            $("#main").load("${base}/admin/class_table/loadList.jhtml");
        });
        $("#spotCheckIn").on("click",function(){
            $("#main").load("${base}/admin/spot_check_in/loadList.jhtml");
        });
        $("#addSpotCheckIn").on("click",function(){
            $("#main").load("${base}/admin/spot_check_in/add.jhtml");
        });
        $("#electronicVoting").on("click",function(){
            $("#main").load("${base}/admin/electronic_voting/loadList.jhtml");
        });

        $("#clubNotice").on("click",function(){
            $("#main").load("${base}/admin/club_notice/loadList.jhtml");
        });
        $("#planActivity").on("click",function(){
            $("#main").load("${base}/admin/plan_activity/loadList.jhtml");
        });
        $("#division").on("click",function(){
            $("#main").load("${base}/admin/department/division.jhtml");
        });

    })
</script>
</body>
</html>
[/#escape]