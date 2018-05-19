var cityDatas = JSON.parse(sessionStorage.getItem('cityData'));
window.onload = init();
function init(){
    $.ajax({
        //url:'city/details/',
        //data:{'id':cityDatas.id},
        //type:'POST',
        dataType:'JSON',
        success:function(o){
            var html = '';
            var data = o.data;
            $('#cityId').html(cityDatas.id);
            $('#cityName').val(cityDatas.cityName);
            $('#createTime').val(cityDatas.createTime);
            if(data.length > 0){
                for(var i = 0; i < data.length; i++){
                    html+='<tr>' +
                        '<th>下一地点</th><td><select id="select'+i+'"></select></td>'+
                        '<th>到达下一地点的开销</th><td><input type="text" id="cost'+i+'"></td></tr>'
                }
            }
            $('#city').after(html);
            setCity(data);
        },
        error:function(o){
            alert('获取数据失败!');
            window.history.go(-1);
        }
    })
}
$("#cancel").click(function(){
    window.history.go(-1);
});
$("#ensure").click(function(){
    $.ajax({
        data:$("#editFrom").serialize(),
        url:'/city/create',
        type:'POST',
        success:function(o){
            console.log(o);
            if(o.status == 1)
                alert("编辑成功!");
            else{
                alert("编辑失败！");
            }
        },
        error:function(){
            alert("编辑失败！");
        }
    });
});
function setIfEdit(){
    console.log(cityDatas.operation);
    if(cityDatas.operation == 0){
        $('#cityName,#nextCity,#costPrice,#createTime').attr("disabled","disabled");
        $('#ensure').attr('disabled','true');
        $('select').attr('disabled','disabled');
        $('input').attr('disabled','disabled');
    }else if(cityDatas.operation == 1){
        $('#cityName,#nextCity,#costPrice,#createTime').removeAttr('disabled');
        $('#ensure').removeAttr('disabled');
        $('select').removeAttr('disabled');
        $('input').removeAttr('disabled');
    }
}
function setCity(ids) {
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
                    for (var i = 0; i < ids.length; i++) {
                        var selectId = '#select'+i;
                        var costId = '#cost'+i;
                        $(selectId).val(ids[i].id);
                        $(costId).val(ids[i].cost);
                    }
                }
            }
        },
        error: function () {
            alert('数据获取失败!');
        }
    });
}
