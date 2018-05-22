[#escape x as x?html]
    [#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>签到管理</legend>
</fieldset>
<div class="layui-btn-group ">
    <button class="layui-btn" id="add">新增</button>
</div>
<table class="layui-hide" id="spotCheckInTable" lay-filter="spotCheckInTable"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    [#list ["sjclub:manager","sjclub:admin"] as permission]
        [@shiro.hasPermission name = permission]
        [#--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--]
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        [/@shiro.hasPermission]
    [/#list]
   [#-- <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>--]
</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;
        var $ = layui.$;
        table.render({
            elem: '#spotCheckInTable'
            ,url:'${base}/admin/spot_check_in/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'title',  title: '活动主题'}
                ,{field:'bz',  title: '备注'}
                ,{field:'createDate', title: '创建日期'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:"spotCheckInList"

        });
        //监听工具条
        table.on('tool(spotCheckInTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        url:"${base}/admin/spot_check_in/delete.jhtml",
                        data:{id:data.id},
                        type:"post",
                        success:function(data){
                            if(data.code==200){
                                obj.del();
                                layer.close(index);
                                layer.msg("删除成功!");
                            }else{
                                layer.msg("删除失败!请先删除活动");
                            }

                        }
                    })
                });
            } else if(obj.event === 'edit'){
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
                $("#main").load("${base}/admin/spot_check_in/add.jhtml?id="+data.id);
            }
        });
    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/spot_check_in/add.jhtml");
    })
</script>
[/#escape]