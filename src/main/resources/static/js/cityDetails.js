$("#cancel").click(function(){
    window.history.go(-1);
});
$("#ensure").click(function(){
    $.ajax({
        url:'',
        data:'',
        type:'POST',
        dataType:'JSON',
        success:function(){

        },
        error:function(){

        }
    });
});
