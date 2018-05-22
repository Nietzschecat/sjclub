[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>关于我们 | 视界</title>
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
<!-- header img -->
<div class="header-img">
	<img class="img-responsive" src="${base}/resources/sjclub/img/about.jpg">
</div>
<!-- /header img -->

<!-- content -->
<div class="container-fluid content">
	<div class="introducation " style="word-spacing:15px">
		<p>大学<span class="strong-1">社团平台</span>是由<span class="strong-2">福建师范大学社团管理</span>设计并推出的</p>
		<p>旨在汇集福建师范大学各社团的<span class="strong-1">最新资讯</span></p>
		<p>让广大师生能够<span class="strong-1">第一时间</span>了解社团最新动态</p>
		<p>选择自己<span class="strong-3">喜欢</span>的社团活动</p>
		<p><span class="strong-1">提高</span>大家活动参与积极度</p>
		<p>最终做到<span class="strong-1">推动</span>福建师范大学社团积极<span class="strong-3">健康发展</span>的目的</p>
		<p>我们<span class="strong-1">由衷欢迎</span>广大师生给我们留言提出<span class="strong-1">宝贵意见</span></p>
		<p>让我们能够<span class="strong-1">进步</span></p>
		<p>也希望有更多福建师范大学学子能够参与到<span class="strong-1">社团活动</span>中来</p>
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