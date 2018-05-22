[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>活动管理</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <input type="text" name="id" value="${clubActivity.id}" style="display: none" class="layui-input">
    <div class="layui-upload layui-form-item">
        <label class="layui-form-label">活动背景图:</label>
        <div class="layui-input-block">
            <img class="layui-upload-img" src="${clubActivity.images}" id="demo1" style="height: 100px;width: 100px">
            <p id="demoText"></p>
        </div>
        <input type="text" name="images" style="display: none" value="${clubActivity.images}"    class="layui-input">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">活动标题:</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${clubActivity.title}" autocomplete="off" placeholder="请输入活动标题" class="layui-input">
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label">截止时间</label>
        <div class="layui-input-inline">
            <input type="text" name="date" id="date" value="${clubActivity.endTime}" placeholder="yyyy-mm-dd hh:mm:ss" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">活动介绍</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="content" lay-verify="introduction" id="LAY_demo_editor">${clubActivity.content}</textarea>
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
        //监听提交
        form.on('submit(demo1)', function(data){
            /* layer.alert(JSON.stringify(data.field), {
                 title: '最终的提交信息'
             });*/
            console.log(">>>"+layedit.getContent(editIndex));
            var name=$("input[name='name']").val();
            var id=$("input[name='id']").val();
            var date=$("input[name='date']").val();
            var isStart=$("input[name='isStart']").val();
            var images=$("input[name='images']").val();
            var content=layedit.getContent(editIndex);
            $.ajax({
                url:"${base}/admin/club_activity/save.jhtml",
                data:{id:id,title:name,content:content,isStart:isStart,endTime:date,images:images},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/club_activity/loadList.jhtml");
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
                    console.log($("input[name='images']"));
                    $("input[name='images']").val(res.url);
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