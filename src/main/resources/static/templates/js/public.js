// left.html 的切换节点  JS
$(function(){
	// 点击展开事件，切换图片
	$(".leftsidebar_box dt").click(function(){
		// 先处理掉所有存在的节点,再判断是否存在
		$('.icon1').hide();
		$('.icon2').show();
		$(".leftsidebar_box dt").removeClass('menu_chioce1');
		if ($(this).parent().find('dd').is(":hidden")) {
			$(this).parent().find('.icon1').show();
			$(this).parent().find('.icon2').hide();
			$(this).parent().find('.icon3').show();
			$(this).parent().find('.icon4').hide();
			$(this).addClass('menu_chioce1');
		}else{
			$(this).parent().find('.icon1').hide();
			$(this).parent().find('.icon2').show();
			$(this).parent().find('.icon3').hide();
			$(this).parent().find('.icon4').show();
			$(this).removeClass('menu_chioce1');
		}
		$(this).parent().find('dd').removeClass("menu_chioce");
		$(".menu_chioce").slideUp();
		$(this).parent().find('dd').slideToggle();
		$(this).parent().find('dd').addClass("menu_chioce");
        showGradeOperation();
	});
	$(".cks").click(function(){
		// 先处理掉所有存在的节点,再判断是否存在
		$(".icon5").hide();
		$('.coin11').hide();
		$('.coin22').show();
		$(this).parent().find('.coin11').show();
		$(this).parent().find('.coin22').hide();
		$(".leftsidebar_box .cks").removeClass('menu_chioce2');
		if ($(this).parent().find('.icon5').is(":hidden")) {
			$(this).addClass('menu_chioce2');
			$(this).parent().find('.icon5').show();
		}else{
			$(this).removeClass('menu_chioce2');
			$(this).parent().find('.icon5').hide();
		}
        showGradeOperation();
	});
    showGradeOperation();
})
// left.html 的切换节点  JS end
function showGradeOperation(){
    var member = JSON.parse(sessionStorage.getItem('member'));
    if(member.grade == 'general'){
        $('#orderMassage').css('display','none');
        $('#orderAccept').css('display','none');
        $('#gather').css('display','none');
        $('#memberManage').css('display','none');
    }else{
        $('#orderAdd').css('display','none');
        $('#cityManage').css('display','block');
    }
}
//修改密码
function change(){
    if(checkPasswordInput()){
        $.ajax({
            type:'POST',
            dataType:'JSON',
            //url:'/public/updatePassword',
            data:$('#editUserInfo').serialize(),
            success:function(data){
                alert('修改成功!');
            },
            error:function(data){
                alert('修改失败!');
            }
        });
    }
}
function checkPasswordInput() {
    if($('#password').val() == null || $('#password').val() == ''){
        alert('请输入密码！');
        return false;
    }

    if($('#password').val().length < 2){
        alert('密码必须超过6位！');
        return false;
    }

    if($('#ensurepassword').val() == null || $('#ensurepassword').val() == ''){
        alert('请确认密码！');
        return false;
    }

    if($('#password').val() != $('#ensurepassword').val()){
        alert('两次密码输入不一致！');
        return false;
    }
    return true;
}
$("#cancel").click(function(){
    window.history.back();
});