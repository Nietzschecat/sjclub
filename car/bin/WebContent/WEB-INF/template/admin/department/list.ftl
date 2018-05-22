[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>部门管理</legend>
</fieldset>
    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
        <div class="layui-btn-group ">
            <button class="layui-btn" id="add">新增</button>
        </div>
        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>
        [/@shiro.hasPermission]
    [/#list]
<table class="layui-hide" id="departmentTable" lay-filter="departmentTable"></table>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#departmentTable'
            ,url:'${base}/admin/department/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'name', title: '部门名称'}
                ,{field:'users',  title: '部门成员人数'}
                ,{field:'introduction',  title: '介绍'}
                ,{field:'createDate', title: '创建日期'}
    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
        [/@shiro.hasPermission]
    [/#list]
            ]]
            ,page: true,
            id:"departmentList"

        });
        //监听工具条
        table.on('tool(departmentTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/department/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
                $("#main").load("${base}/admin/department/add.jhtml?id="+data.id);
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
        $("#main").load("${base}/admin/department/add.jhtml");
    })
</script>
[/#escape]