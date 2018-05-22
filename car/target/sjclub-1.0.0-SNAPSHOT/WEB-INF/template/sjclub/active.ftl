[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>社团活动 | 视界</title>
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

<link rel="shortcut icon" href="${base}/resources/sjclub/img/global-img/icon.ico">
<link rel="stylesheet" href="${base}/resources/sjclub/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/resources/sjclub/css/global.css">
<link rel="stylesheet" href="${base}/resources/sjclub/css/sjclub.css">
<script type="text/javascript" src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/layui/layui.all.js"></script>
</head>
<body>
<!-- 加载登录|注册模态框 -->
[#include "/sjclub/include/login.ftl"/]
[#include "/sjclub/include/register.ftl"/]
<!-- 加载提示信息模态框 -->
[#include "/sjclub/include/modal.ftl"/]

<!-- navbar -->
[#include "/sjclub/include/navbar.ftl"/]

<!-- header img -->
<div class="header-img">
	<img class="img-responsive" src="${base}/resources/sjclub/img/active.jpeg">
</div>
<!-- /header img -->
<script type="text/javascript">
    $(function(){

		[@flash_message /]
    })
</script>
<!-- content -->
<div class="container-fluid content">
	<!-- club activities -->
	<div class="subfield">
		<h4>社团活动<small>Club Activities</small></h4>
		<hr />
	</div>

	<div class="row">
		[#list clubActivitys as clubActivity]
		<div class="col-md-3 col-sm-6 col-xs-12">
			<div class="thumbnail">
      			<img src="${clubActivity.images}" alt="...">
      			<div class="caption" style="height:200px;overflow:hidden;">
        			<h4 style="margin:5px 0" >${clubActivity.title}</h4>
        			<div style="font-size:95%">[#noescape]${clubActivity.content}[/#noescape]</div>
     			</div>
  				<p style="margin:0;padding:5px 0;border-top:1px solid #ddd"><small>活动截止时间：${clubActivity.endTime}</small></p>
        		<a href="${base}/sjclub/active_detail.jhtml?id=${clubActivity.id}" class="btn btn-primary" role="button">查看详情</a>
        		<a href="${base}/sjclub/activity/joinActivity.jhtml?id=${clubActivity.id}" role="button">参加活动</a>
    		</div>
		</div>
		[/#list]
	</div>


</div>
<!-- /content -->

<!-- footer -->
[#include "/sjclub/include/footer.ftl"]

<script src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script src="${base}/resources/sjclub/js/bootstrap.min.js"></script>
<!-- 数据验证 -->
<script src="${base}/resources/sjclub/js/validate.js"></script>
</body>
</html>
[/#escape]