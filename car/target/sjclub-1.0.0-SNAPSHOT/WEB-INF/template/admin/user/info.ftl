[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>个人资料</legend>
</fieldset>

<form class="layui-form" action="">
    <input name="id" value="${user.id}" type="hidden"/>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="userName"  value="${user.userName}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">账号</label>
        <div class="layui-input-inline">
            <input type="text" name="loginName" value="${user.loginName}"  disabled="disabled" placeholder="账号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-inline" lay-filter="selFilter">
            <select name="department" >
                [#list departments as department]
                    <option value="${department.id}" [#if user.department.id=department.id]selected="selected" [/#if] >${department.name}</option>
                [#--[#if department.name!="非正常部门"]
                     <option value="${department.id}" [#if planActivity.department.id=department.id]selected="selected" [/#if] >${department.name}</option>
                 [/#if]--]
                [/#list]
            </select>
        </div>
        [#--<div class="layui-input-inline">


            <input type="text" name="depaemant" value="${user.department.name}"    disabled="disabled" placeholder="类型" autocomplete="off" class="layui-input">
        </div>--]
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <input type="radio" name="sex" value="0"  title="男" [#if user.sex==0]checked[/#if ] />
            <input type="radio" name="sex" value="1"  title="女" [#if user.sex==1]checked[/#if ]>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">出生日期</label>
            <div class="layui-input-inline">
                <input type="tel" name="birthDate"  id="date" value="${user.birthDate}"  autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">身高</label>
            <div class="layui-input-inline">
                <input type="tel" name="sg" value="${user.sg}"  autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">学校</label>
            <div class="layui-input-inline">
                <input type="tel" name="school" value="${user.school}"  autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">班级</label>
            <div class="layui-input-inline">
                <input type="tel" name="className" value="${user.className}" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">所属社团</label>
            <div class="layui-input-inline">
                <input type="tel"  value="[#if user.club??]${user.club.name}[/#if]" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>


<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form,layer = layui.layer,$=layui.$,laydate=layui.laydate;
        //监听提交
        form.on('submit(formDemo)', function(data){
            console.log(data);
            $.ajax({
                url:"${base}/admin/user/save.jhtml",
                data:{id:data.field.id,"department.id":data.field.department,userName:data.field.userName,loginName:data.field.loginName,sex:data.field.sex,birthDate:data.field.birthDate,sg:data.field.sg,school:data.field.school,className:data.field.className},
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.code==200){
                        console.log(">>>>>>"+data);
                        layer.msg("修改成功!");
                    }else{
                        layer.msg("修改失败!");
                    }

                }
            })
            return false;
        });
        //日期
        laydate.render({
            elem: '#date'

        });
        form.render();
    });

</script>
[/#escape]