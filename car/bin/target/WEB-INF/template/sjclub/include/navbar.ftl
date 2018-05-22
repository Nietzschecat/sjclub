[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!-- navbar -->
<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <!-- logo and club name -->
      <a class="navbar-brand" href="${base}/sjclub/index.html"><img src="${base}/resources/sjclub/img/logo.png" class="navbar-logo" alt="logo">
          福建师范大学社团管理
      </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <!-- 一级菜单，首页 -->
        <li  [#if url=='index']class="active"[/#if] ><a href="${base}/sjclub/index.jhtml">首页</a></li>
        <!-- 一级菜单，社团活动 -->
        <li [#if url=='active']class="active"[/#if]><a href="${base}/sjclub/active.jhtml">社团活动</a></li>
        <!-- 一级菜单，社团列表 -->
        <li [#if url=='list']class="active"[/#if]><a href="${base}/sjclub/list.jhtml">社团列表</a></li>
        <!-- 一级菜单，关于我们 -->
        <li [#if url=='about']class="active"[/#if]><a href="${base}/sjclub/about.jhtml">关于我们</a></li>
      </ul>

      <!-- 登录选项，如果未登录则显示登录，已登录则显示用户姓名或帐号 -->
      <ul class="nav navbar-nav navbar-right nav-login">
          [@shiro.authenticated]
                <li class="dropdown">
                  <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> 欢迎 &nbsp;[@shiro.principal /]&nbsp;
                  <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu" id="menu" >
                      [#list ["sjclub:manager","sjclub:admin","sjclub:user"] as permission]
                          [@shiro.hasPermission name = permission]
                              <li><a href="${base}/admin/common/main.jhtml">管理中心</a></li>
                          [/@shiro.hasPermission]
                      [/#list]
                      <li><a href="${base}/admin/logout.jhtml">退出登录</a></li>
                  </ul>
              </li>
              [/@shiro.authenticated]
          [@shiro.notAuthenticated]
             <li><a data-toggle="modal" data-target="#loginModal">登录</a></li>
             <li><a data-toggle="modal" data-target="#registerModal">注册</a></li>
          [/@shiro.notAuthenticated]


      </ul>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav><!-- /.navbar -->
[/#escape]
