$(document).ready(function(){
    $('#money').val('');
    $('#status').val('');
    $('#nowPlace').val('');
    $('#arriveedTime').val('');
    $('#startplace').val('');
    $('#endplace').val('');
    $('#descripe').val('');
    $('#createTime').val('');

    $("#operationGoodmodule").css('display','block');
});
$("#cancel").click(function(){
    window.location.href='../templates/OrderManage.html';
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
            url: "/administrator/orderHandler",
            dataType: 'json',
            data: $("#info-form").serialize(),
            success: function (o) {
                if (o.code === 'SUCCESS') {
                    alert('受理成功!');

                    //添加成功后再table增加一行数据
                    // $('#table').bootstrapTable('prepend', o.member);
                }else if(o.code === 'FAIL'){
                    alert('受理失败！');
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