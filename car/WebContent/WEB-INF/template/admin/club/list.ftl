[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>社团列表</legend>
</fieldset>

    [#list ["sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
            <div class="layui-btn-group ">
                <button class="layui-btn" id="add">新增</button>
            </div>
            <script type="text/html" id="barDemo" >
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">解散</a>
            </script>
        [/@shiro.hasPermission]
    [/#list]
<table class="layui-hide" id="clubTable" lay-filter="clubTable"></table>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#clubTable'
            ,url:'${base}/admin/club/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'name', title: '社团名称'}
                ,{field:'info',  title: '社团简介'}
                ,{field:'users',  title: '社团成员人数'}
                ,{field:'createDate', title: '创建日期'}
                [@shiro.hasPermission name = "sjclub:admin"]
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
                [/@shiro.hasPermission]
            ]]
            ,page: true,
            id:"clubList"
        });
        //监听工具条
        table.on('tool(clubTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/club/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            layer.close(index);
                            layer.msg("删除成功!");
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                $("#main").load("${base}/admin/club/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/club/add.jhtml");
    })

</script>
[/#escape]