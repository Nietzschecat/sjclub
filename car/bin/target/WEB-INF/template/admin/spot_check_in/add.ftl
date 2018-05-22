[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>签到管理</legend>
</fieldset>
<form class="layui-form" method="post">
    <input name="id" value="${id}" type="hidden"/>
    <div class="layui-form-item">
            <label class="layui-form-label">活动主题:</label>
            <div class="layui-input-inline">
                <select name="activityId" lay-filter="selFilter">
                    [#list clubActivities as clubActiviti]
                       <option value="${clubActiviti.id}" [#if clubActiviti.id=spotCheckIn.activity.id] selected="selected" [/#if]>${clubActiviti.title}</option>
                    [/#list]
                </select>
            </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">签到</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="bz" lay-verify="introduction" id="LAY_demo_editor">${spotCheckIn.bz}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" id="submitBtn" lay-filter="demo1" lay-submit="">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(["form","laydate"],function () {
        var form = layui.form
                ,layedit = layui.layedit,
                $=layui.$;
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //监听提交
        form.on('submit(demo1)', function(data){
           /* layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            });*/
            console.log(">>>"+layedit.getContent(editIndex));
            var activityId=$("select[name='activityId']").val();
            var id=$("input[name='id']").val();
            var introduction=layedit.getContent(editIndex);
            $.ajax({
                url:"${base}/admin/spot_check_in/save.jhtml",
                data:{id:id,"activity.id":activityId,bz:introduction},
                type:"post",
                dataType:"json",
                success:function(data){
                    //console.log(">>>>>>"+data);
                    layer.msg("签到成功!");
                    $("#main").load("${base}/admin/spot_check_in/loadList.jhtml");
                }
            })
            return false;
        });
        form.render();
    });

</script>
[/#escape]