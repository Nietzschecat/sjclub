[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户资料</legend>
</fieldset>
<div class="layui-btn-group ">
    <button class="layui-btn" id="add">新增</button>
</div>
<table class="layui-hide" id="userTable" lay-filter="userTable"></table>
<script type="text/html" id="barDemo">

    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
     <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#userTable'
            ,url:'${base}/admin/user/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'userName', title: '用户名'}
                ,{field:'loginName',  title: '登录名'}
                ,{field:'sex',  title: '性别'}
                ,{field:'sg', title: '身高'}
                ,{field:'birthDate',  title: '生日'}
                ,{field:'school',  title: '学校'}
                ,{field:'password',  title: '密码'}
                ,{field:'className',  title: '班级'}
                ,{field:'roleName',  title: '角色'}
                ,{field:'departmentName',  title: '部门'}
            [#list ["sjclub:admin","sjclub:manager"] as permission]
                [@shiro.hasPermission name = permission]
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            [/@shiro.hasPermission]
            [/#list]
            ]]
            ,page: true,
            id:"userList"

        });
        //监听工具条
        table.on('tool(userTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                if(data.loginName=="admin"){
                    layer.msg("不能删除admin账号!");
                }else{
                    layer.confirm('真的删除行么', function(index){
                        obj.del();
                        $.ajax({
                            url:"${base}/admin/user/delete.jhtml",
                            data:{id:data.id},
                            type:"post",
                            success:function(data){
                                layer.close(index);
                                layer.msg("删除成功!");
                            }
                        })
                    });
                }
            } else if(obj.event === 'edit'){
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
                $("#main").load("${base}/admin/user/add.jhtml?id="+data.id);
            }
        });


        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('idTest')
                        ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
            ,getCheckLength: function(){ //获取选中数目
                var checkStatus = table.checkStatus('idTest')
                        ,data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
            }
            ,isAll: function(){ //验证是否全选
                var checkStatus = table.checkStatus('idTest');
                layer.msg(checkStatus.isAll ? '全选': '未全选')
            }
        };
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/user/add.jhtml");
    });
</script>
[/#escape]