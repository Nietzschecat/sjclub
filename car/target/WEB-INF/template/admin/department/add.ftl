[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>部门管理</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <input type="text" name="id" value="${department.id}" style="display: none" class="layui-input">
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称:</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${department.name}" autocomplete="off" placeholder="请输入部门名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">部门简介</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="introduction" lay-verify="introduction" id="LAY_demo_editor">${department.introduction}</textarea>
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
            var name=$("input[name='name']").val();
            var id=$("input[name='id']").val();
            var introduction=layedit.getContent(editIndex);
            $.ajax({
                url:"${base}/admin/department/save.jhtml",
                data:{id:id,name:name,introduction:introduction},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/department/loadList.jhtml");
                }
            })
            return false;
        });
    });
    /*$(function () {
        $("#submitBtn").on("click",function(){
            var name=$("input[name='name']").val();
            var introduction=$("textarea[name='introduction']").val();
            console.log(name+">>>>>>"+introduction);
            $.ajax({
                url:"save.jhtml",
                data:{name:name,introduction:introduction},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("loadList.jhtml");
                }
            })
        })
    })*/
</script>
[/#escape]