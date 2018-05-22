[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>活动安排</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <input type="text" name="id" value="${planActivity.id}" style="display: none" class="layui-input">
    <div class="layui-form-item">
        <label class="layui-form-label">活动标题:</label>
        <div class="layui-input-inline">
            <select name="clubActivity">
                [#list clubActivitys as clubActivity]
                    <option value="${clubActivity.id}" [#if planActivity.clubActivity.id=clubActivity.id]selected="selected" [/#if] >${clubActivity.title}</option>
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label">部门:</label>
        <div class="layui-input-inline" lay-filter="selFilter">
            <select name="department" >
                [#list departments as department]
                    <option value="${department.id}" [#if planActivity.department.id=department.id]selected="selected" [/#if] >${department.name}</option>
                   [#--[#if department.name!="非正常部门"]
                        <option value="${department.id}" [#if planActivity.department.id=department.id]selected="selected" [/#if] >${department.name}</option>
                    [/#if]--]
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">时间</label>
        <div class="layui-input-block">
            <input type="text" name="time"  id="date" value="${planActivity.time}" autocomplete="off" placeholder="请输入时间" class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="content" id="LAY_demo_editor">${planActivity.content}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">时长</label>
        <div class="layui-input-block">
            <input type="text" name="timeLen"   value="${planActivity.timeLen}" autocomplete="off" placeholder="请输入时长" class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="bz" id="LAY_demo_editor1" >${planActivity.bz}</textarea>
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
            layui.use(["form",'layedit','laydate','upload'],function () {
                var form = layui.form
                        ,upload = layui.upload
                        ,layedit = layui.layedit
                        ,laydate = layui.laydate
                $=layui.$;

                //创建一个编辑器
                var editIndex = layedit.build('LAY_demo_editor');
                var editIndex1 = layedit.build('LAY_demo_editor1');

                //监听提交
                form.on('submit(demo1)', function(data){
                    /* layer.alert(JSON.stringify(data.field), {
                         title: '最终的提交信息'
                     });*/
                    console.log(">>>"+layedit.getContent(editIndex));
                    var clubActivity=$("select[name='clubActivity']").val();
                    var id=$("input[name='id']").val();
                    var department=$("select[name='department']").val();
                    var time=$("input[name='time']").val();
                    var timeLen=$("input[name='timeLen']").val();
                    var content=layedit.getContent(editIndex);
                    var bz=layedit.getContent(editIndex1);
                    $.ajax({
                        url:"${base}/admin/plan_activity/save.jhtml",
                        data:{id:id,"clubActivity.id":clubActivity,content:content,"department.id":department,time:time,timeLen:timeLen,bz:bz},
                        type:"post",
                        dataType:"json",
                        success:function(data){
                            console.log(">>>>>>"+data);
                            //layer.msg("修改成功!");
                            $("#main").load("${base}/admin/plan_activity/loadList.jhtml");
                        }
                    })
                    return false;
                });
                //日期
                laydate.render({
                    elem: '#date',
                    type: 'datetime'
                });
                form.render();
            });


</script>
[/#escape]