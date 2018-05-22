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
    <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
    <button type="button" class="layui-btn" id="test9">开始上传</button>
</div>
<script type="text/javascript">
    //选完文件后不自动上传
    layui.use('upload', function() {
        var $ = layui.jquery
                , upload = layui.upload;
        //选完文件后不自动上传
        upload.render({
            elem: '#test8'
            ,url: '${base}/admin/club_file/save.jhtml'
            ,auto: false
            ,accept: 'file' //普通文件
            //,multiple: true
            ,bindAction: '#test9'
            ,done: function(res){
                if(res.code="200"){
                    layer.msg("上传成功!");
                }
                console.log(res)
            }
        });
    });


</script>
[/#escape]