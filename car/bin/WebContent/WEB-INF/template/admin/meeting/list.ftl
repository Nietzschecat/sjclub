[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>会议记录</legend>
</fieldset>
[#list ["sjclub:manager","sjclub:admin"] as permission]
    [@shiro.hasPermission name = permission]
        <div class="layui-btn-group club">
            <button class="layui-btn" id="add">新增</button>
        </div>
    [/@shiro.hasPermission]
[/#list]
<table class="layui-hide" id="meetingTable" lay-filter="meetingTable"></table>
<script type="text/html" id="barDemo" >
    <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
        [#list ["sjclub:manager","sjclub:admin"] as permission]
            [@shiro.hasPermission name = permission]
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            [/@shiro.hasPermission]
        [/#list]
</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#meetingTable'
            ,url:'${base}/admin/meeting/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'title', title: '会议主题'}
                ,{field:'content',  title: '会议内容'}
                ,{field:'createDate', title: '创建日期'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:"meetingList"
        });
        //监听工具条
        table.on('tool(meetingTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'view'){
                $("#main").load("${base}/admin/meeting/view.jhtml?id="+data.id);
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/meeting/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                $("#main").load("${base}/admin/meeting/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/meeting/add.jhtml");
    })

</script>
[/#escape]