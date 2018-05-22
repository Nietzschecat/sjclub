[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>投票管理</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <input type="text" name="id" style="display: none" value="${electronicVoting.id}" class="layui-input">
    <div class="layui-form-item">
        <label class="layui-form-label">投票内容:</label>
        <div class="layui-input-block">
            <input type="text" name="content" autocomplete="off" value="${electronicVoting.content}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">投票选项:</label>
        <div class="layui-input-block">
            [#list electronicVoting.electronicVotingItemList as item]
                [#if item.itemName??]
                    <input type="radio" name="item"  value="${item.id}" title="${item.itemName}" />
                [/#if]
            [/#list]
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" id="submitBtn" lay-filter="demo1" lay-submit="">立即提交</button>
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
            console.log(data.field);
            var itemId=data.field.item;
            var id=$("input[name='id']").val();
            console.log(itemId+">>>"+id);
            $.ajax({
                url:"${base}/admin/electronic_voting/saveItem.jhtml",
                data:{id:id,itemId:itemId},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    layer.msg("投票成功!");
                    $("#main").load("${base}/admin/electronic_voting/loadList.jhtml");
                }
            })
            return false;
        });
        form.render();
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