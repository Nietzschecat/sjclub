[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>社团详情 | 视界</title>
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

	[#include "/sjclub/include/navbar.ftl"/]

<!-- navbar -->
<jsp:include page="../global-page/navbar.jsp">
	<jsp:param name="pagename" value="sjclub_list.jsp" />
</jsp:include>

<!-- header img -->
<div class="header-img">
	<img class="img-responsive" src="${club.logo}">
</div>
<!-- /header img -->
<!-- content -->
<div class="container-fluid content">
	<!-- club detail -->
	<div class="subfield">
		<h4>社团详情<small><a onclick="self.location=document.referrer;">Club detail</a>&nbsp;>>&nbsp;${club.name}</small></h4>
		<hr />
	</div>
	<!-- add club -->
	<div class="add-club">
			[#if @shiro.principal??]
				<input type="text" id="clubId" value="${club.id}" style="display:none">
				<button class="btn btn-danger btn-default" id="addClub"><span class="glyphicon glyphicon-plus"></span>&nbsp;加入社团</button>

			[#else ]
				<button type="button" data-toggle="modal" data-target="#loginModal" class="btn btn-danger btn-default"><span class="glyphicon glyphicon-plus"></span>&nbsp;加入社团</button>
			[/#if]
		<p>加入社团，参加此社团的活动，了解此社团的更多详情。</p>
	</div>
	
	<!-- active detail -->
	<div class="list_detail">
		<h3>${club.name}</h3>
		${club.info}
	</div>

</div>
<!-- /.content -->

[#include "/sjclub/include/footer.ftl"]

<script src="${base}/resources/sjclub/js/jquery.min.js"></script>
<script src="${base}/resources/sjclub/js/bootstrap.min.js"></script>
<!-- 数据验证 -->
<script src="${base}/resources/sjclub/js/validate.js"></script>
<!-- 加入社团 -->
<script>
$(document).ready(function(){
	//加入社团按钮，当用户登录后可以加入社团
	//但是要判断是否已经是社团的成员了
	$("#addClub").click(function(){
		//获取userId和clubId的值
		var clubId = $("#clubId").val();
		$.ajax({
			url:"${base}/sjclub/joinClub.jhtml",
			data:{id:clubId},
			type:"post",
			dataType:"json",
			success:function(data){
                if(data.code == "userIsClub"){
                    $("#messageModal-body").html("您已经是此社团的成员了，可以去加入其它社团哦。");
                    $("#messageModal").modal('show');
                }else if(data.code  == "refuse"){
                    $("#messageModal-body").html("您已经加入了三个社团了，不能再加入社团了。");
                    $("#messageModal").modal('show');
                }else if(data.code  == "ok"){
                    $("#messageModal-body").html("加入社团成功！<br>您可以查看此社团更多信息或参加此社团的活动。");
                    $("#messageModal").modal('show');
                }
			}
		})

	})
})
</script>
</body>
</html>
[/#escape]