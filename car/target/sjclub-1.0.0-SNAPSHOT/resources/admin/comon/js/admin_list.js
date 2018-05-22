$().ready( function() {
    var $listForm = $("#listForm");
    var $deleteButton=$("#deleteButton");
    var $pageNumber = $("#pageNumber");
    var $pageSize = $("#pageSize");
    var $listTable = $("#listTable");
    var $ids = $("#listTable input[name='ids']");

    $("#selectAll").on("click",function(){
        var checked=$(this).attr("checked");

        if(checked=="checked"){
            $("input[name='ids']").prop("checked", true);
            $deleteButton.removeClass("disabled");
            $deleteButton.removeClass("btn-ash");
        }else{
            $("input[name='ids']").prop("checked", false);
            $deleteButton.addClass("btn-ash");
            $deleteButton.addClass("disabled");
        }
    });
    var ids=$("input[name='ids']");
    var len=ids.length;
    $.each(ids,function (i,item) {
          $(item).live("click",function () {
             var checked_len= $("input[name='ids']:checked").length;
             //console.log(checked_len+"==="+len);
             if(checked_len==len){
                 $("#selectAll").prop("checked", true);
             }else{
                 $("#selectAll").prop("checked", false);
             }
          });
    });

    // 选择
    $ids.click( function() {
        var $this = $(this);
        if ($this.prop("checked")) {
            //$this.closest("tr").addClass("selected");
            $deleteButton.removeClass("disabled");
            $deleteButton.removeClass("btn-ash");
        } else {
            //$this.closest("tr").removeClass("selected");
            if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
                $deleteButton.removeClass("disabled");
                $deleteButton.removeClass("btn-ash");
            } else {
                $deleteButton.addClass("btn-ash");
                $deleteButton.addClass("disabled");
            }
        }
    });

    // 删除
    $deleteButton.click( function() {
        var $this = $(this);
        if ($this.hasClass("disabled")) {
            return false;
        }
        var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
        console.log($checkedIds.serialize());
        $.dialog({
            type: "warn",
            content: "是否删除",
            ok: "确定",
            cancel: "取消",
            onOk: function() {
                $.ajax({
                    url: "delete.jhtml",
                    type: "POST",
                    data: $checkedIds.serialize(),
                    dataType: "json",
                    cache: false,
                    success: function(message) {
                        $.message(message);
                        if (message.type == "success") {
                            $checkedIds.closest("tr").remove();
                            if ($listTable.find("tr").size() <= 1) {
                                setTimeout(function() {
                                    location.reload(true);
                                }, 1000);
                            }
                        }
                        ///$deleteButton.addClass("disabled");
                        //$selectAll.prop("checked", false);
                        $checkedIds.prop("checked", false);
                    }
                });
            }
        });
        return false;
    });
    // 页码跳转
    $.pageSkip = function(pageNumber) {
        $pageNumber.val(pageNumber);
        $listForm.submit();
        return false;
    };
});
