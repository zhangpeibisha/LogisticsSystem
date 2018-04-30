$(document).ready(function(){
    $('#money').val('');
    $('#startplace').val('');
    $('#endplace').val('');
    $('#descripe').val('');

    $("#operationGoodmodule").css('display','block');
});
$('#enable').click(function(){
    if(addInputCheck()){
        if ($("#password").val() != null && $("#password").val() != '') {
            console.log($("#password").val())
            $(" input[ name='password' ] ").val(hex_md5($("#password").val()));
        }else {
            $(" input[ name='password' ] ").val(hex_md5('123456'));
        }
        $.ajax({
            type: 'POST',
            url: "/administrator/orderAdd",
            dataType: 'json',
            data: $("#info-form").serialize(),
            success: function (o) {
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
                if (XMLHttpRequest.status == 401) {
                    alert("权限不足！！！")
                }
            }
        });
    }
});
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
}