[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>公告管理</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <input type="text" name="id" value="${clubNotice.id}" style="display: none" class="layui-input">
    [#--<div class="layui-form-item">
        <label class="layui-form-label">社团:</label>
        <div class="layui-input-inline">
            <select name="clubId" lay-filter="selFilter">
                [#list clubs as club]
                    <option value="${club.id}" [#if meeting.club.id=club.id]selected="selected" [/#if] >${club.name}</option>
                [/#list]
            </select>
        </div>
    </div>--]
    <div class="layui-form-item">
        <label class="layui-form-label">公告标题:</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${clubNotice.title}" autocomplete="off" placeholder="请输入活动标题" class="layui-input">
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label">截止时间</label>
        <div class="layui-input-inline">
            <input type="text" name="date" id="date" value="${clubNotice.endTime}" placeholder="yyyy-mm-dd hh:mm:ss" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否开启</label>
        <div class="layui-input-block">
            <input type="radio" name="isStart" value="0" title="是" [#if clubNotice??][#if clubNotice.isStart==0]checked[/#if] [#else ]checked[/#if] >
            <input type="radio" name="isStart" value="1" title="否" [#if clubNotice??][#if clubNotice.isStart==1]checked[/#if] [/#if]>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">公告内容</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="content" lay-verify="introduction" id="LAY_demo_editor">${clubNotice.content}</textarea>
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
    layui.use(["form",'layedit','laydate'],function () {
        var form = layui.form
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
            var content=layedit.getContent(editIndex);
            $.ajax({
                url:"${base}/admin/club_notice/save.jhtml",
                data:{id:id,title:name,content:content,isStart:isStart,endTime:date},
                type:"post",
                dataType:"json",
                success:function(data){
                    console.log(">>>>>>"+data);
                    //layer.msg("修改成功!");
                    $("#main").load("${base}/admin/club_notice/loadList.jhtml");
                }
            })
            return false;
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