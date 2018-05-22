$(function(){

    $("#submitButton").on("click",function(){
        var productCategoryIds=$("select[id^='productCategoryId_']");
        if(productCategoryIds.length>1){
            var names=checkedProductCategoryIds(productCategoryIds);
            if(names!=""){
                alert(names+"类型商品已存在!");
                return false;
            }
        }
        $("#inputForm").submit();
    });

    //检查数组是否有重复
    var checkedProductCategoryIds=function(obj){
        var result=[];
        var names=[];
        for(var i=0; i<obj.length; i++){
            if(result.indexOf($(obj[i]).val())==-1){
                result.push($(obj[i]).val());
            }else{
                names.push($(obj[i]).find("option:selected").attr("attrName"));
                return names;
            }
        }
        return names;
    }
});
var changeProductCategory=function(obj){
    var productCategoryIds=$("select[id^='productCategoryId_']");
    var result=[];
    var names="";
    var self=$(obj).val();
    $.each(productCategoryIds,function(){
        result.push(this);
    });
    result.splice(result.indexOf(obj),1);
    $.each(result,function(){
        if(self==$(this).val()){
            names=$(this).find("option:selected").attr("attrName");
        }
    });
    if(names!=""){
        alert(names+"类型商品已存在!");
        return false;
    }
}
var getMaxWeight=function(obj){
    var id=$(obj).attr("attrId");
    var productId=$(obj).attr("productId");
    console.log(productId);
    var weight=$(obj).val();
    var $weight=$(obj).attr("weight");
    var $oldWeight=$(obj).attr("oldWeight");
    $.ajax({
        url:"getMaxWeight.jhtml",
        type:"post",
        data:{id:id,selfId:productId,weight:weight},
        dataType:"json",
        success:function(data){
                console.log(data);
                console.log($weight);
                //$weight这个是总计划的
                var sweight=$weight.trim();

                var intweight=parseInt(sweight);
                var intallWeight=parseInt(data.allWeight);
                var oldWeight=parseInt($oldWeight);
                var maxWeight=(intweight-intallWeight)+oldWeight;
                console.log(intweight+"=="+intallWeight+"=="+oldWeight);
                if(maxWeight!=0){
                    //alert("还允许分配"+maxWeight+"斤");
                    console.log("=="+maxWeight);
                    var $maxWeight=$(obj).parent().find(".maxWeight");
                    $maxWeight.html(maxWeight);
                    //$("#maxWeight").html(maxWeight);
                    if(weight!=0&&maxWeight<weight){
                        $(obj).val(maxWeight);
                    }
                }
                if(maxWeight==0){
                    $(obj).val(0);
                    if(""!=data.content){
                        alert(data.content);
                    }
                }
        }
    })
}
var submitFun=function(obj){
    var inputs=$("input[id^='items']");
    $.each(inputs,function(){
        $(this).trigger("change");
    });
    $.dialog({
        type: "warn",
        content: message("您是否确定生成计划？"),
        ok: message("admin.dialog.ok"),
        cancel: message("admin.dialog.cancel"),
        onOk: function() {
            $(obj).submit();
        }
    })
}
