/******************************树结构插件************************************/
(function($){
    $.fn.initTree= function(options){
        $(this).treeview({
            data: options,         // 数据源
            showCheckbox: false,   //是否显示复选框
            highlightSelected: true,    //是否高亮选中
            multiSelect: false,    //多选
            showBorder: false,      //是否显示边框
            onNodeSelected: function (event, data) {
                var tree = $('#tree');
                //获取当前节点对象
                var node = tree.treeview('getNode', data.nodeId);

                if (node.state.expanded) {
                    //处于展开状态则折叠
                    tree.treeview('collapseNode', node.nodeId);
                } else {
                    //展开
                    tree.treeview('expandNode', node.nodeId);
                }
            }
        });
    }
})(jQuery);