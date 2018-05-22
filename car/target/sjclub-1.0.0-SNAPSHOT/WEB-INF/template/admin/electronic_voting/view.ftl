[#escape x as x?html]
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>发布投票</legend>
</fieldset>

<form class="layui-form" method="post"  >
    <div class="layui-form-item">
        <label class="layui-form-label">投票内容:</label>
        <div class="layui-input-block">
            <input type="text" name="content" value="${electronicVoting.content}" class="layui-input">
        </div>
    </div>
    [#list electronicVoting.electronicVotingItemList as item]
        [#if item.itemName??]
            <div class="layui-form-item">
                <label class="layui-form-label">${item.itemName}:</label>
                <div class="layui-input-block">
                    <input type="text" name="item1" disabled="disabled"  value="[#if item.count=='']0[#else]${item.count}[/#if]" class="layui-input">
                </div>
            </div>
        [/#if]
    [/#list]
</form>
<script type="text/javascript">
    layui.use(["form","laydate"],function () {
        var form = layui.form
                ,layedit = layui.layedit,
                $=layui.$;
        form.render();
    });
[/#escape]