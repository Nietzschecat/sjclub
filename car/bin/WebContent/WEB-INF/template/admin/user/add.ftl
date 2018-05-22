[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户管理</legend>
</fieldset>

<form class="layui-form" method="post" >
    <input type="text" name="id" style="display: none" value="${user.id}"    class="layui-input">
        <div class="layui-upload layui-form-item">
            <label class="layui-form-label">用户头像:</label>
            <button type="button" class="layui-btn" id="test1">上传</button>
            <div class="layui-input-block">
                <img class="layui-upload-img" src="${user.userLogo}" id="demo1" style="height: 50px;width: 50px">
                <p id="demoText"></p>
            </div>
            <input type="text" name="userLogo" style="display: none" value="${user.userLogo}"    class="layui-input">
        </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名称:</label>
        <div class="layui-input-block">
            <input type="text" name="userName" value="${user.userName}"  autocomplete="off" placeholder="请输入用户名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">登录账户:</label>
        <div class="layui-input-block">
            <input type="text" name="loginName" value="${user.loginName}"  autocomplete="off" placeholder="请输入登录账户" class="layui-input">
        </div>
    </div>
    [#--<div class="layui-form-item">
        <label class="layui-form-label">登录密码:</label>
        <div class="layui-input-block">
            <input type="password" name="password" value="${user.password}" autocomplete="off" placeholder="请输入登录密码" class="layui-input">
        </div>
    </div>--]
    <div class="layui-form-item">
        <label class="layui-form-label">性别:</label>
        <div class="layui-input-block">
            <input type="radio" name="sex"  value="0" title="男" [#if user.sex==0]checked[/#if]>
            <input type="radio" name="sex"  value="1" title="女" [#if user.sex==1]checked[/#if]>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出生日期:</label>
        <div class="layui-input-block">
            <input type="text" name="birthDate" id="date" value="${user.birthDate}"  autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">身高:</label>
        <div class="layui-input-block">
            <input type="text" name="sg" value="${user.sg}"  autocomplete="off" placeholder="请输入身高" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">学校:</label>
        <div class="layui-input-block">
            <input type="text" name="school" value="${user.school}" autocomplete="off" placeholder="请输入学校" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">班级:</label>
        <div class="layui-input-block">
            <input type="text" name="className" value="${user.className}"  autocomplete="off" placeholder="请输入班级" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门:</label>
        <div class="layui-input-inline">
            <select name="department">
                [#list departments as department]
                   <option value="${department.id}" [#if user.department.id=department.id]selected="selected" [/#if] >${department.name}</option>
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">社团:</label>
        <div class="layui-input-inline">
            <select name="club">
                [#list clubList as club]
                    <option value="${club.id}" [#if user.club.id=club.id]selected="selected" [/#if] >${club.name}</option>
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色:</label>
        <div class="layui-input-block">

            [#list roles as role]
                <input type="radio" name="roleIds" value="${role.id}" title="${role.name}" [#if user??][#if user.roles?seq_contains(role)] checked="checked"[/#if][/#if] />
               [#--[#if user??][#if user.roles?seq_contains(role)] checked="checked"[/#if][/#if]--]
            [/#list]
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
    layui.use(["form","layedit","laydate",'upload'],function () {
        var form = layui.form
                ,$ = layui.jquery
                ,layedit = layui.layedit
                ,upload = layui.upload
                ,laydate = layui.laydate;
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //监听提交
        form.on('submit(demo1)', function(data){
            var id=$("input[name='id']").val();
            var userLogo=$("input[name='userLogo']").val();
            var userName=$("input[name='userName']").val();
            var loginName=$("input[name='loginName']").val();
            var sex=$("input[name='sex']").val();
            var birthDate=$("input[name='birthDate']").val();
            var sg=$("input[name='sg']").val();
            var school=$("input[name='school']").val();
            var className=$("input[name='className']").val();
            var department=$("select[name='department']").val();
            var club=$("select[name='club']").val();
            var roles=$("input[name='roleIds']:checked");
            var roleIds=roles.val();
            //alert(roleIds+"=="+club+"=="+department);
            console.log(data);
            /*if(club==""||club=="null"){
                layer.msg("请选择社团");
                return ;
            }*/
            $.ajax({
                url:"${base}/admin/user/save.jhtml",
                data:{id:id,userLogo:userLogo,roleIds:roleIds,userName:userName,loginName:loginName,sex:sex,birthDate:birthDate,sg:sg,school:school,className:className,"department.id":department,"club.id":club},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/user/loadList.jhtml");
                }
            })
            return false;
        });

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '${base}/admin/file/upload.jhtml'
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                //如果上传失败
                if(res.code !="200"){
                    console.log("==="+res);
                    return layer.msg('上传失败');
                }else{
                    console.log(">>++++"+res.url);
                    console.log($("input[name='userLogo']"));
                    $("input[name='userLogo']").val(res.url);
                    form.render();
                    return layer.msg('上传成功!');
                }
            }
            ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
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