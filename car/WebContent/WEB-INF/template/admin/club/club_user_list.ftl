[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>社团成员</legend>
</fieldset>

<table class="layui-hide" id="clubUserTable" lay-filter="clubUserTable"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#clubUserTable'
            ,url:'${base}/admin/club/club_user_list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'clubName', title: '俱乐部名称'}
                ,{field:'userName', title: '用户名'}
                ,{field:'loginName',  title: '登录名'}
                ,{field:'department',  title: '部门'}
                ,{field:'sex',  title: '性别'}
                ,{field:'sg', title: '身高'}
                ,{field:'school',  title: '学校'}
                ,{field:'className',  title: '班级'}
                [#list ["sjclub:manager","sjclub:admin"] as permission]
                    [@shiro.hasPermission name = permission]
                        ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
                    [/@shiro.hasPermission]
                [/#list]
            ]]
            ,page: true,
            id:"userTable"

        });
        //监听工具条
        table.on('tool(clubUserTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        url:"${base}/admin/club/deleteUser.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            obj.del();
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                layer.alert('编辑行：<br>'+ JSON.stringify(data))
            }
        });

    });

</script>
[/#escape]