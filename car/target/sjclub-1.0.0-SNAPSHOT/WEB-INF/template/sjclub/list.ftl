[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>社团列表 | 视界</title>
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
<script type="text/javascript" src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/layui/layui.all.js"></script>
	<script type="text/javascript">
		$(function(){

			[@flash_message /]
		})
	</script>
</head>

<body>
<!-- 加载登录|注册模态框 -->
[#include "/sjclub/include/login.ftl"/]
[#include "/sjclub/include/register.ftl"/]

[#include "/sjclub/include/navbar.ftl"/]
<!-- header img -->
<div class="header-img">
	<img class="img-responsive" src="${base}/resources/sjclub/img/list.jpg">
</div>
<!-- /header img -->

<!-- content -->
<div class="container-fluid content">
	<!-- club list -->
	<div class="subfield">
		<h4>社团列表<small>Club List</small></h4>
		<hr />
	</div>
	<div class="row">
			[#list clubList as club]
                <div class="col-md-2 col-sm-4 col-xs-6">
                    <div class="show-club">
                        <img src="${club.logo}" title="社团logo" >

                        <a href="${base}/sjclub/club/joinClub.jhtml?id=${club.id}" role="button">加入${club.name}社团</a>
                        [#--<a href="${base}/sjclub/active_detail.jhtml?id=${club.id}">${club.name}</a>--]
                    </div>
                </div>
			[/#list]



	</div>
	
	<!-- paging -->
	[#--<nav class="paging text-center">
	<ul class="pagination pagination-lg">

			<li><a href="list.jsp?page=">&laquo;</a></li>

			<li ><a href="list.jsp?page=">1</a></li>
			
			<li><a href="list.jsp?page=">&raquo;</a></li>

	</ul>
	</nav>--]
	<!-- /paging -->
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