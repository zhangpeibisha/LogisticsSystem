var orderData = JSON.parse(sessionStorage.getItem("goodData"));
var member = JSON.parse(sessionStorage.getItem("member"));
$(document).ready(function(){
    setValue();
    $("#next").click(function () {
        
        if (JSON.parse(sessionStorage.getItem("goodData")).orderStatus === "ORDER_ARRIVALS") {
            alert("货物已达到目的地！！！");
            $("#next").hide();
            return;
        }
        
        $.ajax({
            type:'GET',
            url:'/order/transport?orderId=' + JSON.parse(sessionStorage.getItem("goodData")).id,
            dataType:'json',
            success:function(datas) {
                if (datas.status == 1) {
                    $("#next").hide();
                }
                alert(datas.msg);
            }
        });
    });
});

function getPath(){
    var data=[{time:'2016-3-5',massage:'到达太原'},{time:'2016-3-5',massage:'到达太原'},{time:'2016-3-6',massage:'从太原站发出'},{time:'2016-3-7',massage:'到达杭州'}];
    $.ajax({
      type:"POST",
      url:'/order/ways?order_id=' + JSON.parse(sessionStorage.getItem("goodData")).id,
      dataType:'json',
      success:function(data){
          var ways = data.data.way;
          var htmldata = '<div class="track-rcol" style="position: relative;left: 80px;"><div class="track-list" style="max-height: 350px;overflow-y: auto;"><span>物流信息</span><ul>';
          htmldata = htmldata + '<li class="first">' +
              '<i class="node-icon"></i>' +
              '<span class="time">'+ (ways[ways.length-1].arriveDate == undefined ? "未达到" : formatDateTime(new Date(ways[ways.length-1].arriveDate))) + '</span>'+
              '<span class="txt">'+ (ways[ways.length-1].finish ? "到达：" : "运输中：") + ways[ways.length-1].city.cityName + '</span></li>';
          for(var i = ways.length-2; i >=0; i--){
              htmldata = htmldata + '<li>' +
                  '<i class="node-icon"></i>' +
                  '<span class="time">'+ (ways[i].arriveDate == undefined ? "未达到" : formatDateTime(new Date(ways[i].arriveDate))) + '</span>'+
                  '<span class="txt">'+ (ways[i].finish ? "到达：" : "运输中：") + ways[i].city.cityName + '</span></li>'
          }
          htmldata = htmldata + '</ul> </div> </div>' + '<button class="btn btn-default" style="float: right;margin-right: 40px;" onclick="dissmissLookPath()">返回</button>';
          var s = document.getElementById('goodPath');
          s.innerHTML=htmldata;
          $('#goodPath').css('display','block');
          $('.log-window').css('display','block');
      },
      error:function(){
          // alert('操作失败！');
          // window.history.back();
          var htmldata = '<div class="track-rcol" style="position: relative;left: 80px;"><div class="track-list"><span>物流信息</span><ul>';
          htmldata = htmldata + '<li class="first">' +
              '<i class="node-icon"></i>' +
              '<span class="time">'+ data[data.length-1].time + '</span>'+
              '<span class="txt">'+ data[data.length-1].massage + '</span></li>'
          for(var i = data.length-2; i >=0; i--){
              htmldata = htmldata + '<li>' +
                  '<i class="node-icon"></i>' +
                  '<span class="time">'+ data[i].time + '</span>'+
                  '<span class="txt">'+ data[i].massage + '</span></li>'
          }
          htmldata = htmldata + '</ul> </div> </div>' + '<button class="btn btn-default" style="float: right;margin-right: 40px;" onclick="dissmissLookPath()">返回</button>';
          var s = document.getElementById('goodPath');
          s.innerHTML=htmldata;
          $('#goodPath').css('display','block');
          $('.log-window').css('display','block');
      }
  })
};
function dissmissLookPath(){
    $('#goodPath').css('display','none');
    $('.log-window').css('display','none');
}
//返回的数据应该是姓名（name），回复日期(date)，回复内容(replyContent)三条。
$('#evalution').click(function(){
    console.info("点击评价按钮");
    console.info(orderData.id);
    $.ajax({
        data:{orderId:orderData.id},
        type:'GET',
        url:'/order/findAllOrderEvaluationByOrderId',
        dataType:'json',
        success:function(datas) {
            console.info("评价信息data");
            console.info(datas);
            console.info(datas.data[0].sysUser.account);
            console.info("用户名");
            var evalutiondata = '<div class="ylcon"><p class="tit">所有评价 </p> <div id="messDivId" style="overflow-y: auto;max-height: 252px;">';
            if(datas.data.length > 0){
                var evalu =  datas.data;
                for(var i = 0; i <evalu.length; i++){
                    evalutiondata = evalutiondata + '<div class="story"><div class="opbtn"></div>'+
                        '<p class="story_t">'+ "发送用户："+ evalu[i].sysUser.account+'</p>'+
                        //+  evalu[i].createTime.getFullYear()
                        '<p class="story_time">'+ "评价时间："+ formatDateTime(new Date(evalu[i].createTime)) + '</p>' +
                        '<p class="story_m">'+  "评价信息："+ evalu[i].evaluation+'</p></div>'
                }
                evalutiondata = evalutiondata + '</div>';
            }
            evalutiondata = evalutiondata +'<div>'+
                '<div><textarea id="massage" style="margin-top: 5px;width: 30%;height: 150px;margin-left: 15px;min-width: 400px;"></textarea>'+
                '<button type="button" style="margin-left: 15px;position: relative;top: 120px;" class="btn btn-default" onclick="reply()">评价</button>'+
                '<button type="button" style="margin-left: 15px;position: relative;top: 120px;" class="btn btn-default" onclick="replyCancel()">取消</button></div>'+
                '</div>';
            var s = document.getElementById('evalutionBox');
            s.innerHTML=evalutiondata;
            $('#evalutionBox').css('display','block');
            $('.log-window').css('display','block');
        },
        error:function(datas){
            alert("获取评价信息失败!");
        }
    });
});
function reply(){
    $.ajax({
        url:'/order/orderEvaluation',
        dataType:'json',
        type:'POST',
        data:{order_id:orderData.id,message:$('#massage').val(),account:member.account},
        success:function(){
            alert('评论成功');
            console.info("点击评价按钮");
            console.info(orderData.id);
            $.ajax({
                data:{orderId:orderData.id},
                type:'GET',
                url:'/order/findAllOrderEvaluationByOrderId',
                dataType:'json',
                success:function(datas) {
                    console.info("评价信息data");
                    console.info(datas);
                    console.info(datas.data[0].sysUser.account);
                    console.info("用户名");
                    var evalutiondata = '<div class="ylcon"><p class="tit">所有评价 </p> <div id="messDivId" style="overflow-y: auto;max-height: 252px;">';
                    if(datas.data.length > 0){
                        var evalu =  datas.data;
                        for(var i = 0; i <evalu.length; i++){
                            evalutiondata = evalutiondata + '<div class="story"><div class="opbtn"></div>'+
                                '<p class="story_t">'+ "发送用户："+ evalu[i].sysUser.account+'</p>'+
                                //+  evalu[i].createTime.getFullYear()
                                '<p class="story_time">'+ "评价时间："+ formatDateTime(new Date(evalu[i].createTime)) + '</p>' +
                                '<p class="story_m">'+  "评价信息："+ evalu[i].evaluation+'</p></div>'
                        }
                        evalutiondata = evalutiondata + '</div>';
                    }
                    evalutiondata = evalutiondata +'<div>'+
                        '<div><textarea id="massage" style="margin-top: 5px;width: 30%;height: 150px;margin-left: 15px;min-width: 400px;"></textarea>'+
                        '<button type="button" style="margin-left: 15px;position: relative;top: 120px;" class="btn btn-default" onclick="reply()">评价</button>'+
                        '<button type="button" style="margin-left: 15px;position: relative;top: 120px;" class="btn btn-default" onclick="replyCancel()">取消</button></div>'+
                        '</div>';
                    var s = document.getElementById('evalutionBox');
                    s.innerHTML=evalutiondata;
                    $('#evalutionBox').css('display','block');
                    $('.log-window').css('display','block');
                },
                error:function(datas){
                    alert("获取评价信息失败!");
                }
            });
        },
        error:function(){
            alert('评论失败');
        }
    });
}
function replyCancel(){
    $('#evalutionBox').css('display','none');
    $('.log-window').css('display','none');
}
function setValue(){
    var sData = JSON.parse(sessionStorage.getItem("goodData"));
    var member = JSON.parse(sessionStorage.getItem("member"));
    $("#money,#status,#nowPlace,#arriveedTime,#startplace,#endplace,#descripe,#createTime").attr("disabled","true");
    $("#orderId").val(sData.id);
    $("#account").val(sData.sysUser.account);
    $("#accountpassword").val(sData.sysUser.password);
    $('#money').text(sData.cost);
    switch(sData.orderStatus){
        case 'ORDER_PENDING_PAYMENT':$('#status').text("未支付");break;
        case 'ORDER_PAID_NO_SHIPPED':$('#status').text("未发货");break;
        case 'ORDER_BEING_SHIPPED':$('#status').text("已发货");break;
        case 'ORDER_ARRIVALS':$('#status').text("已到达");break;
        case 'ORDER_SIGN':$('#status').text("已签收");break;
    }
    $('#nowPlace').text(sData.currentCity);
    if(sData.timeOfArrival > 0)
        $('#arriveedTime').text(formatDateTime(new Date(sData.timeOfArrival)));
    else
        $('#arriveedTime').text("-");
    $('#startplace').text(sData.startCity);
    $('#endplace').text(sData.endCity);
    $('#descripe').text(sData.node);
    $('#createTime').text(formatDateTime(new Date(sData.createTime)));
    $("#operationGoodmodule").css('display','block');
    if (sData.endCity == sData.currentCity) {
        $("#next").hide();
    }
    $("#enable").attr('disabled','disabled');
}
var formatDateTime = function (date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h=h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    var second=date.getSeconds();
    second=second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};
$("#cancel").click(function(){
    window.history.go(-1);
});