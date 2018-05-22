[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>投票管理</legend>
</fieldset>

    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
            <div class="layui-btn-group ">
                <button class="layui-btn" id="add">发布投票</button>
            </div>
        [/@shiro.hasPermission]
    [/#list]
<table class="layui-hide" id="electronicVotingTable" lay-filter="electronicVotingTable"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>

        [#list ["sjclub:manager","sjclub:admin"] as permission]
            [@shiro.hasPermission name = permission]
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            [/@shiro.hasPermission]
        [/#list]

    [@shiro.hasPermission name = "sjclub:user"]
        <a class="layui-btn layui-btn-xs" lay-event="edit">投票</a>
    [/@shiro.hasPermission]

</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#electronicVotingTable'
            ,url:'${base}/admin/electronic_voting/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'content', title: '投票内容'}
                ,{field:'createDate', title: '创建日期'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:"electronicVotingList"

        });
        //监听工具条
        table.on('tool(electronicVotingTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                $("#main").load("${base}/admin/electronic_voting/view.jhtml?id="+data.id)
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        url:"${base}/admin/electronic_voting/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            obj.del();
                            layer.close(index);
                            layer.msg(data.message);
                        }
                    })
                });
            } else if(obj.event === 'edit'){
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
                /*$.ajax({
                    url:"${base}/admin/electronic_voting/check.jhtml",

                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if(data.code=200){*/
                            $("#main").load("${base}/admin/electronic_voting/add.jhtml?id="+data.id);
                      /*  }else{
                            layer.msg("管理员不具备投票资格");
                        }
                    }
                })*/
            }
        });

    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/electronic_voting/add.jhtml");
    })
</script>
[/#escape]