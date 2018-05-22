[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>发布投票</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <div class="layui-form-item">
        <label class="layui-form-label">投票内容:</label>
        <div class="layui-input-block">
            <input type="text" name="content" autocomplete="off" placeholder="请输入投票内容" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项1:</label>
        <div class="layui-input-block">
            <input type="text" name="item1" autocomplete="off" placeholder="请输入投票选项1" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项2:</label>
        <div class="layui-input-block">
            <input type="text" name="item2" autocomplete="off" placeholder="请输入投票选项2" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项3:</label>
        <div class="layui-input-block">
            <input type="text" name="item3" autocomplete="off" placeholder="请输入投票选项3" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项4:</label>
        <div class="layui-input-block">
            <input type="text" name="item4" autocomplete="off" placeholder="请输入投票选项4" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项5:</label>
        <div class="layui-input-block">
            <input type="text" name="item5" autocomplete="off" placeholder="请输入投票选项5" class="layui-input">
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
            var content=$("input[name='content']").val();
            var id=$("input[name='id']").val();
            var item1=$("input[name='item1']").val();
            var item2=$("input[name='item2']").val();
            var item3=$("input[name='item3']").val();
            var item4=$("input[name='item4']").val();
            var item5=$("input[name='item5']").val();
            $.ajax({
                url:"${base}/admin/electronic_voting/save.jhtml",
                data:{id:id,content:content,item1:item1,item2:item2,item3:item3,item4:item4,item5:item5},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/electronic_voting/loadList.jhtml");
                }
            })
            return false;
        });
        form.render();
    });

</script>
[/#escape]