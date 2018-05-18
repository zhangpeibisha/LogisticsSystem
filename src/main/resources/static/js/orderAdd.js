$(document).ready(function(){
    $('#money').val('');
    $('#descripe').val('');
    $('#item').val('');
    $('#price').val('');
    setCity();
    $("#operationGoodmodule").css('display','block');
});
function addOrder(){
    if(addInputCheck()){
        if(confirm('是否付款？') == true){
            $.ajax({
                type: 'POST',
                url: "/order/publishOrder",
                dataType: 'json',
                data: {
                    startplaceId:$('#startplace option:selected').val(),
                    endplaceId:$('#endplace option:selected').val(),
                    cost:$('#money').val(),
                    node:$('#descripe').val(),
                    cargoName:$('#item').val(),
                    cargoPrice:$('#price').val()
                  },
                success: function (o) {
                    console.log(o);
                    if (o.code === 'SUCCESS') {
                        alert('添加成功!');

                        //添加成功后再table增加一行数据
                        // $('#table').bootstrapTable('prepend', o.member);
                    }else if(o.code === 'FAIL'){
                        alert('添加失败！');
                        dismiss();
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert('添加失败！');
                    if (XMLHttpRequest.status == 401) {
                        alert("权限不足！！！")
                    }
                }
            });
        }
    }
}
function setCity() {
    $.ajax({
        url:'/city/list?size=99999',
        type: 'POST',
        dataType : 'json',
        success: function (o) {
            var data = o.data;
            if (data.length > 0) {
                console.log(data);
                if (data.length > 0) {
                    var option = '';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value=' + data[i].id + '>' + data[i].cityName + '</option>';
                    }
                    $("select").html(option);
                }
            }
        },
        error: function () {
           alert('受理失败!');
        }
    });
}

// $("#startplace").focus(function(){
//     $("#startplace").attr("size","5");
// })
// $("#endplace").focus(function(){
//     $("#endplace").attr("size","5");
// })
// function selectCityClick(){
//     $("#startplace").removeAttr("size");
//     $("#startplace").blur();
//     $("#endplace").removeAttr("size");
//     $("#endplace").blur();
// }
function addInputCheck(){
    if($('#money').val() == null || $('#money').val() == ''){
        alert('请输入金额！');
        return false;
    }
    if($('#descripe').val() == null || $('#descripe').val() == ''){
        alert('请输入描述！');
        return false;
    }
    return true;
}