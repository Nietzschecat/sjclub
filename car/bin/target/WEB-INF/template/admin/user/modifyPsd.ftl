[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>修改资料</legend>
</fieldset>
<div class="layui-form-item">
    <label class="layui-form-label">新密码</label>
    <div class="layui-input-inline">
        <input type="password" name="password"  placeholder="新密码" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">确认密码</label>
    <div class="layui-input-inline">
        <input type="password" name="repassword"   placeholder="确认密码" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" id="submitBtn" >立即提交</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#submitBtn").on("click",function(){
            var psw=$("input[name='password']").val();
            var repsw=$("input[name='repassword']").val();
            if(psw!=repsw){
                layer.msg("密码和确认密码不相符");
                return;
            }
            $.ajax({
                url:"${base}/admin/user/updatePsd.jhtml",
                data:{psw:psw},
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.code==0){
                        layer.msg("修改成功!");
                    }else{
                        layer.msg("修改失败!");
                    }

                }
            })
        })
    })
</script>
[/#escape]