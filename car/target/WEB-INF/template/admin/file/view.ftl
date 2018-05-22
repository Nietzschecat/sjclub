[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>文件上传</legend>
</fieldset>
[#--<div class="layui-form-item">
    <label class="layui-form-label">社团:</label>
    <div class="layui-input-inline">
        <select name="clubId" lay-filter="selFilter">
            [#list clubs as club]
                <option value="${club.id}" [#if clubFile.club.id=club.id]selected="selected" [/#if] >${club.name}</option>
            [/#list]
        </select>
    </div>
</div>--]
<div class="layui-upload">
    <button type="button" class="layui-btn" id="test1">上传图片</button>
    <div class="layui-upload-list">
        <img class="layui-upload-img" id="demo1" style="height: 400px;width: 400px">
        <p id="demoText"></p>
    </div>
</div>
<script type="text/javascript">
    //选完文件后不自动上传
    layui.use('upload', function() {
        var $ = layui.jquery
                , upload = layui.upload;
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '${base}/admin/club_file/save.jhtml'
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
                    $("#main").load("${base}/admin/club_file/loadList.jhtml");
                }
                //上传成功
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
    });


</script>
[/#escape]