[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>文件管理</legend>
</fieldset>
    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
        <div class="layui-btn-group club">
            <button class="layui-btn" id="add">上传</button>
        </div>
        [/@shiro.hasPermission]
    [/#list]
<table class="layui-hide" id="fileTable" lay-filter="fileTable"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="down">下载</a>
   [#-- <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="view">查看</a>--]

        [#list ["sjclub:manager","sjclub:admin"] as permission]
            [@shiro.hasPermission name = permission]
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            [/@shiro.hasPermission]
        [/#list]

</script>
<script>
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#fileTable'
            ,url:'${base}/admin/club_file/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'fileName', title: '文件名称'}
                ,{field:'fileUrl', title: '文件路径'}
                ,{field:'createDate',  title: '注册时间'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true
        });
        //监听工具条
        table.on('tool(fileTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'down'){
                console.log(data.fileUrl);
                window.location.href=data.fileUrl;
            }else if(obj.event === 'view'){
                $("#main").load("${base}/admin/club_file/view.jhtml?id="+data.id);
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/club_file/delete.jhtml",
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
                $("#main").load("${base}/admin/club_file/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/club_file/add.jhtml");
    });
</script>
[/#escape]