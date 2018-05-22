[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>社团公告</legend>
</fieldset>
    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
            <div class="layui-btn-group club">
                <button class="layui-btn" id="add">新增</button>
            </div>
        [/@shiro.hasPermission]
    [/#list]


<table class="layui-hide" id="activityTable" lay-filter="activityTable"></table>
<script type="text/html" id="barDemo" >
    <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#activityTable'
            ,url:'${base}/admin/club_notice/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'title', title: '公告标题'}
                ,{field:'endTime',  title: '截止时间'}
                ,{field:'content',  title: '内容'}
                ,{field:'userName',  title: '创建者'}
                ,{field:'isStart',  title: '是否开启'}
                ,{field:'createDate', title: '创建日期'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:"activityList"
        });
        //监听工具条
        table.on('tool(activityTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'view'){
                $("#main").load("${base}/admin/club_notice/view.jhtml?id="+data.id);
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/club_notice/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                $("#main").load("${base}/admin/club_notice/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/club_notice/add.jhtml");
    })

</script>
[/#escape]