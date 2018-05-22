[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>无课表格</legend>
</fieldset>

<form class="layui-form" >
    <input type="text" name="id" value="${classTable.id}"  style="display: none" class="layui-input">
   [#-- <div class="layui-form-item">
        <label class="layui-form-label">无课名:</label>
        <div class="layui-input-block">
            <input type="text" name="noClass"  value="${classTable.noClass}"   autocomplete="off" placeholder="请输入无课名" class="layui-input">
        </div>
    </div>--]
    <div class="layui-form-item">
        <label class="layui-form-label">课时:</label>
       [#-- <div class="layui-input-block">
            <input type="text" name="classTime" value="${classTable.classTime}"  autocomplete="off" placeholder="请输入课时长" class="layui-input">
        </div>--]
        <div class="layui-input-inline">
            <select name="noClass">
                <option value="">请选择星期</option>
                <option value="星期一" [#if classTable.noClass=='星期一']selected="" [/#if]>星期一</option>
                <option value="星期二" [#if classTable.noClass=='星期二']selected="" [/#if]>星期二</option>
                <option value="星期三" [#if classTable.noClass=='星期三']selected="" [/#if]>星期三</option>
                <option value="星期四" [#if classTable.noClass=='星期四']selected="" [/#if]>星期四</option>
                <option value="星期五" [#if classTable.noClass=='星期五']selected="" [/#if]>星期五</option>
                <option value="星期六" [#if classTable.noClass=='星期六']selected="" [/#if]>星期六</option>
                <option value="星期日" [#if classTable.noClass=='星期日']selected="" [/#if]>星期日</option>
            </select>
        </div>
        <div class="layui-input-inline">
            <select name="classTime">
                <option value="">请选择第几节课</option>
                <option value="第一节" [#if classTable.noClass=='第一节']selected="" [/#if]>第一节</option>
                <option value="第二节" [#if classTable.noClass=='第二节']selected="" [/#if]>第二节</option>
                <option value="第三节" [#if classTable.noClass=='第三节']selected="" [/#if]>第三节</option>
                <option value="第四节" [#if classTable.noClass=='第四节']selected="" [/#if]>第四节</option>
                <option value="第五节" [#if classTable.noClass=='第五节']selected="" [/#if]>第五节</option>
                <option value="第六节" [#if classTable.noClass=='第六节']selected="" [/#if]>第六节</option>
                <option value="第七节" [#if classTable.noClass=='第七节']selected="" [/#if]>第七节</option>
                <option value="第八节" [#if classTable.noClass=='第八节']selected="" [/#if]>第八节</option>
                <option value="第九节" [#if classTable.noClass=='第九节']selected="" [/#if]>第九节</option>
                <option value="第十节" [#if classTable.noClass=='第十节']selected="" [/#if]>第十节</option>
                <option value="第十一节"[#if classTable.noClass=='第十一节']selected="" [/#if]>第十一节</option>
                <option value="第十二节"[#if classTable.noClass=='第十二节']selected="" [/#if]>第十二节</option>
            </select>
        </div>
    </div>
    [#--<div class="layui-form-item">
        <label class="layui-form-label">上课地点:</label>
        <div class="layui-input-block">
            <input type="text" name="address" value="${classTable.address}"  autocomplete="off" placeholder="请输入上课地点" class="layui-input">
        </div>
    </div>--]
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" id="submitBtn" lay-filter="demo1" lay-submit="" >立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(["form","laydate"],function () {
        var form = layui.form
                ,layedit = layui.layedit
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');

        //监听提交
        form.on('submit(demo1)', function(data){
            var noClass=$("select[name='noClass']").val();
            var classTime=$("select[name='classTime']").val();
            var id=$("input[name='id']").val();
            $.ajax({
                url:"${base}/admin/class_table/save.jhtml",
                data:{id:id,noClass:noClass,classTime:classTime},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/class_table/loadList.jhtml");
                }
            })
            return false;
        });

        form.render();
    });

</script>
[/#escape]