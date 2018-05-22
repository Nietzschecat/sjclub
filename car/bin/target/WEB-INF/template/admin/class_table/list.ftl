[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>无课表管理</legend>
</fieldset>
<div class="demoTable">
    选择星期几:
    <div class="layui-inline">
        <select name="noClass" id="noClass">
            <option value="">请选择星期</option>
            <option value="星期一" >星期一</option>
            <option value="星期二" >星期二</option>
            <option value="星期三" >星期三</option>
            <option value="星期四" >星期四</option>
            <option value="星期五" >星期五</option>
            <option value="星期六" >星期六</option>
            <option value="星期日" >星期日</option>
        </select>
    </div>
    选择课时:
    <div class="layui-input-inline">
        <select name="classTime" id="classTime">
            <option value="">请选择第几节课</option>
            <option value="第一节" >第一节</option>
            <option value="第二节" >第二节</option>
            <option value="第三节" >第三节</option>
            <option value="第四节" >第四节</option>
            <option value="第五节" >第五节</option>
            <option value="第六节" >第六节</option>
            <option value="第七节" >第七节</option>
            <option value="第八节" >第八节</option>
            <option value="第九节" >第九节</option>
            <option value="第十节" >第十节</option>
            <option value="第十一节">第十一节</option>
            <option value="第十二节">第十二节</option>
        </select>
    </div>
    <button class="layui-btn" id="add">新增无课表</button>
</div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="noClassTable" lay-filter="noClassTable"></table>
[#--<script src="${base}/resources/layui/layui.js"></script>
<script src="${base}/resources/sjclub/js/jquery.min.js"></script>--]
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table,$=layui.jquery;
        table.render({
            elem: '#noClassTable'
            ,url:'${base}/admin/class_table/list.jhtml'
            ,cols: [[
                {field:'id', title: 'ID'}
                ,{field:'noClass', title: '星期几'}
                ,{field:'classTime',  title: '课时'}
                ,{field:'userName',  title: '用户'}
                ,{fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]]
            ,page: true,
            id:'testReload'
        });
        //监听工具条
        table.on('tool(noClassTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    $.ajax({
                        url:"${base}/admin/class_table/delete.jhtml",
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
                $("#main").load("${base}/admin/class_table/add.jhtml?id="+data.id);
            }
        });

        $("#noClass").on("change",function(){
            var noClass=$(this).val();
            var classTime=$("#classTime").val();
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    noClass: noClass,
                    classTime:classTime
                }
            });
        });
        $("#classTime").on("change",function(){
            var noClass=$("#noClass").val();
            var classTime=$(this).val()
            var val=$(this).val();
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    noClass: noClass,
                    classTime:classTime
                }
            });
        });

    });
    $("#add").on("click",function(){
        $("#main").load("${base}/admin/class_table/add.jhtml");
    });
</script>
[/#escape]