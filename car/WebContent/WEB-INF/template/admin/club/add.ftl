[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>社团管理</legend>
</fieldset>

<form class="layui-form" method="post">
    <input type="text" name="id" value="${club.id}" style="display: none" class="layui-input">
    <div class="layui-form-item">
        <label class="layui-form-label">社团名称:</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${club.name}" autocomplete="off" placeholder="请输入社团名称" class="layui-input">
        </div>
    </div>
    <div class="layui-upload layui-form-item">
        <label class="layui-form-label">社团LOGO:</label>
        <button type="button" class="layui-btn" id="test1">上传</button>
        <div class="layui-input-block">
            <img class="layui-upload-img" src="${club.logo}" id="demo1" style="height: 50px;width: 50px">
            <p id="demoText"></p>
        </div>
        <input type="text" name="userLogo" style="display: none" value="${club.logo}"    class="layui-input">
    </div>
    <div class="layui-form-item">
    <label class="layui-form-label">是否十大校佳</label>
    <div class="layui-input-block">
        <input type="radio" name="tenGood" value="1" title="是" [#if club??][#if club.tenGood==1]checked[/#if] [#else ]checked[/#if] >
        <input type="radio" name="tenGood" value="0" title="否" [#if club??][#if club.tenGood==0]checked[/#if] [/#if]>
    </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">社团简介</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="introduction" lay-verify="introduction" id="LAY_demo_editor">${club.info}</textarea>
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
    layui.use(["form","laydate",'upload'],function () {
        var form = layui.form
                ,upload = layui.upload
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
            var userLogo=$("input[name='userLogo']").val();
            var tenGood=$("input[name='tenGood']").val();
            var introduction=layedit.getContent(editIndex);
            $.ajax({
                url:"${base}/admin/club/save.jhtml",
                data:{id:id,name:name,info:introduction,logo:userLogo,tenGood:tenGood},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/club/loadList.jhtml");
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
        form.render();
    });

</script>
[/#escape]