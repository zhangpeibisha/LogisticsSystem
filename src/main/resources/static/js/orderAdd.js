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
            var order = {
                money:$('#money').val(),
                startplace:$('#startplace').val(),
                endplace:$('#endplace').val(),
                descripe:$('#descripe').val(),
                item:$('#item').val(),
                price:$('#price').val()
            };
            console.log(order);
            $.ajax({
                type: 'POST',
                url: "/order/publishOrder",
                dataType: 'json',
                data: order,
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
        url:'/city/allList',
        type: 'POST',
        dataType : 'json',
        success: function (data) {
            if (data.length > 0) {
                var option = '';
                for (var i = 0; i < data.length; i++) {
                    option += '<option>' + data[i].cityName + '</option>';
                }
                console.log(option);
                $("#startplace").html(option);
                $('#endplace').html(option);
            }
        },
        error: function () {
            var datas= [{cityName: '1'}, {cityName: '2'},{cityName: '3'}, {cityName: '4'},{cityName: '5'}, {cityName: '6'}];
            if (datas.length > 0) {
                var option = '';
                for (var i = 0; i < datas.length; i++) {
                    option += '<option onclick="selectCityClick()">' + datas[i].cityName + '</option>';
                }
                console.log(option);
                $("#startplace").html(option);
                $('#endplace').html(option);
            }
        }
    });
}

$("#startplace").focus(function(){
    $("#startplace").attr("size","5");
})
$("#endplace").focus(function(){
    $("#endplace").attr("size","5");
})
function selectCityClick(){
    $("#startplace").removeAttr("size");
    $("#startplace").blur();
    $("#endplace").removeAttr("size");
    $("#endplace").blur();
}
function addInputCheck(){
    if($('#money').val() == null || $('#money').val() == ''){
        alert('请输入金额！');
        return false;
    }
    if($('#startplace').val() == null || $('#startplace').val() == ''){
        alert('请输入起点！');
        return false;
    }
    if($('#endplace').val() == null || $('#endplace').val() == ''){
        alert('请输入终点！');
        return false;
    }
    if($('#descripe').val() == null || $('#descripe').val() == ''){
        alert('请输入描述！');
        return false;
    }
    return true;
}