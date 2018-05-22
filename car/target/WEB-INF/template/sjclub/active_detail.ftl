[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>活动详情 | 视界</title>
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

<link rel="shortcut icon" href="${base}/resources/sjclub/icon.ico">
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
<!-- header img -->
<div class="header-img">
	<img class="img-responsive" src="${base}/resources/sjclub/img/active.jpg">
</div>
<!-- /.header img -->

<!-- content -->
<div class="container-fluid content">
	<div class="row">
		<!-- content left -->
		<div class="col-md-9 col-sm-12 col-xs-12">

			<!-- club activities -->
			<div class="subfield">
				<h4>社团活动<small><a onclick="self.location=document.referrer;">Club Activities</a>&nbsp;>>&nbsp;活动主题</small></h4>
				<hr />
			</div>
			
			<!-- active detail -->
			<div class="active_content">
				<h3>${clubActivity.title}</h3>
				[#noescape]${clubActivity.content}[/#noescape]
			</div>
		</div>
		<!-- /.content left -->

    	
		<!-- content right -->

		[#list  otherActivitys as other]
            <div class="col-md-3 col-sm-12 col-xs-12">
                <!-- other active -->
                <div class="subfield">
                    <h4>其他活动<small>Others Active</small></h4>
                    <hr />
                </div>
                <p><a href="${base}/sjclub/active_detail.jhtml?id=${other.id}">${other.title}</a></p>
            </div>
		[/#list]


	</div>
</div>
<!-- /.content -->

<!-- footer -->
[#include "/sjclub/include/footer.ftl"]

<script src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script src="${base}/resources/sjclub/js/bootstrap.min.js"></script>
<!-- 数据验证 -->
<script src="${base}/resources/sjclub/js/validate.js"></script>
</body>
</html>
[/#escape]