function checkInput() {
    if($('#account').val() == null || $('#account').val() == ''){
        alert('请输入用户名！');
        return false;
    }
    if($('#password').val() == null || $('#password').val() == ''){
        alert('请输入密码！');
        return false;
    }
    if($('#password').val().length < 2){
        alert('密码必须超过6位！');
        return false;
    }
    return true;
}

function checkLogin(){
    if(checkInput()) {
        // $(" input[ name='password' ] ").val(hex_md5($("#password").val()));
        $.ajax({
            type: 'post',
            url: "/public/login",
            dataType: 'json',
            data: $('#loginForm').serialize(),
            success: function (data) {
                if (data.status == 1) {
                    sessionStorage.setItem("member",JSON.stringify(data.data));
                    location.href = "./html/adminIndex.html";
                }else{
                    alert('用户名或者密码错误！');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    alert("权限不足！！！")
                }
            }
        });
    }
}