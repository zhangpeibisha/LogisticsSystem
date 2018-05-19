var cityDatas = JSON.parse(sessionStorage.getItem('cityData'));
window.onload = init();
function init(){
    $.ajax({
        url:'/city/details/'+cityDatas.id,
        type:'GET',
        dataType:'JSON',
        success:function(o){
            console.log(o.data.dstCityList);
            var html = '';
            var data = o.data.dstCityList;
            $('#cityId').html(cityDatas.id);
            $('#cityName').val(cityDatas.cityName);
            $('#createTime').val(cityDatas.createTime);
            if(data.length > 0){
                for(var i = 0; i < data.length; i++){
                    html+='<tr>' +
                        '<th>下一地点</th><td><select class="haveValue" id="select'+i+'"></select></td>'+
                        '<th>到达下一地点的开销</th><td><input class="haveValue" type="text" id="cost'+i+'"></td></tr>'
                }
            }
            $('#city').after(html);
            setCity(data);
            setIfEdit();
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
    console.log(cityDatas);
    var lists=document.getElementsByClassName("selectValue");
    var dstCityIds = [];
    for(var i=0;i<lists.length;i++){
        dstCityIds.push(list[i].val());
    }
    console.log(dstCityIds);
    var distance=document.getElementsByClassName("inputValue");
    for(var i=0;i<lists.length;i++){
        dstCityIds.push(list[i].val());
    }
    console.log(distance);
    $.ajax({
        data:{
            srcCityId:cityDatas.id,
            dstCityIds:dstCityIds,
            distance:distance
        },
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
$(document).ready(function(){
    $.ajax({
        url:'/city/list?size=99999',
        type: 'POST',
        dataType : 'json',
        success: function (o) {
            var data = o.data;
            if (data.length > 0) {
                var option = '';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value=' + data[i].id + '>' + data[i].cityName + '</option>';
                }
                clone = option;
                //$("#cityOperation").append(row(clone));
            }
        },
        error: function () {
            alert('添加失败!');
        }
    });
});
/*添加一行下一地*/
function addNextCity(){
    // $("#nextBtn").remote();
    console.log(clone);
    $("#cityOperation").append(row(clone));
}
var clone;
let row = c => {
    return $(`<tr>
                <th>下一地点</th>
                <span></span>
                <td>
                    <select class="selectValue">
                        ${c}
                    </select>
                </td>
                <th>到达下一地点的开销</th>
                <td>
                    <input type="text" value="0" class="inputValue">
                </td>
            </tr>`);
};
function setIfEdit(){
    console.log(cityDatas.operation);
    if(cityDatas.operation == 0){
        $('#cityName,#nextCity,#costPrice,#createTime').attr("disabled","disabled");
        $('#ensure').attr('disabled','true');
        $('select').attr('disabled','disabled');
        $('input').attr('disabled','disabled');
        $('#addnextCitybtn').attr('disabled','disabled');
        $(".haveValue").attr("disabled","disabled");
    }else if(cityDatas.operation == 1){
        $('#cityName,#nextCity,#costPrice,#createTime').removeAttr('disabled');
        $('#ensure').removeAttr('disabled');
        $('select').removeAttr('disabled');
        $('input').removeAttr('disabled');
        $(".haveValue").attr("disabled","disabled");
        $('#addnextCitybtn').removeAttr('disabled');
    }
}
function setCity(citys) {
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
                    for (var i = 0; i < citys.length; i++) {
                        var selectId = '#select'+i;
                        var costId = '#cost'+i;
                        $(selectId).val(citys[i].primaryKey.dstCity.id);
                        $(costId).val(citys[i].distance);
                    }
                }
            }
        },
        error: function () {
            alert('数据获取失败!');
        }
    });
}
