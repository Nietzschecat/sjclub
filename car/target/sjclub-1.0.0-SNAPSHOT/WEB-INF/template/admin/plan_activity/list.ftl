[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>活动安排</legend>
</fieldset>


    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
            <div class="layui-btn-group club">
                <button class="layui-btn" id="add">新增</button>
            </div>
            <script type="text/html" id="barDemo" >
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>
        [/@shiro.hasPermission]
    [/#list]

<table class="layui-hide" id="planActivityTable" lay-filter="planActivityTable"></table>

<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#planActivityTable'
            ,url:'${base}/admin/plan_activity/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'title', title: '活动标题'}
                ,{field:'department',  title: '活动部门'}
                ,{field:'content',  title: '内容'}
                ,{field:'time',  title: '活动时间'}
                ,{field:'timeLen',  title: '活动时长'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:"planActivityTable"
        });
        //监听工具条
        table.on('tool(planActivityTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/plan_activity/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                $("#main").load("${base}/admin/plan_activity/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/plan_activity/add.jhtml");
    })

</script>
[/#escape]