//0:普通用户
//1:管理员
function checkData() {
    if($('#judgeOperation').val() == 0){
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
    }else if($('#judgeOperation').val() == 1){
        if($('#registerAccount').val() == null || $('#registerAccount').val() == ''){
            alert('请输入用户名！');
            return false;
        }

        if($('#registerPassword').val() == null || $('#registerPassword').val() == ''){
            alert('请输入密码！');
            return false;
        }

        if($('#registerPassword').val().length < 2){
            alert('密码必须超过6位！');
            return false;
        }

        if($('#surePassword').val() == null || $('#surePassword').val() == ''){
            alert('请确认密码！');
            return false;
        }

        if($('#surePassword').val() != $('#registerPassword').val()){
            alert('两次密码输入不一致！');
            return false;
        }
    }
    return true;
}

function checkLogin(){
    if(checkData()) {
        $(" input[ name='password' ] ").val(hex_md5($("#password").val()));
        var member = {
            account:$('#account').val(),
            password:$(" input[ name='password' ] ").val(),
            grade:$(" input[ name='grade' ]:checked").val(),
        };
        $.ajax({
            type: 'post',
            url: "/public/login",
            dataType: 'json',
            data: member,
            success: function (data) {
                if (data.status == 1) {
                    sessionStorage.setItem("member",JSON.stringify(member));
                    location.href = "./index.html";
                }else{
                    alert('用户名或者密码错误！');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                sessionStorage.setItem("member",JSON.stringify(member));
                location.href = "./index.html";
                if (XMLHttpRequest.status == 401) {
                    alert("权限不足！！！")
                }
            }
        });
    }
}
function register(){
    $('.log-window').css('display','block');
    $('#registermodule').css('display','block');
    $('#judgeOperation').val('1');
}
function enableregister(){
    if(checkData()) {
        var member = {
            account:$('#account').val(),
            password:$(" input[ name='password' ] ").val(),
            grade:$(" input[ name='grade' ]:checked ").val()
        };
        console.log(member);
        $.ajax({
            type: 'POST',
            url: "/generalUser/register",
            dataType: 'json',
            data:$('#registerForm').serialize(),
            success: function (data) {
                if (data == 'SUCCESS') {
                    sessionStorage.setItem("member",JSON.stringify(member));
                    location.href = "../html/index.html";
                }else{
                    alert('用户名或者密码错误！');
                    location.href='https://www.baidu.com/';
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    alert("操作失败！！！")
                }
            }
        });
    }
}
function dismissRegister(){
    $('.log-window').css('display','none');
    $('#registermodule').css('display','none');
    $('#registerAccount').text('');
    $('#registerPassword').text('');
    $('#surePassword').text('');
    $('#judgeOperation').val('0');
}