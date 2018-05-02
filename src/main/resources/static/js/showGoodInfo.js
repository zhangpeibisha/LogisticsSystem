$(document).ready(function(){
    setValue();
});
function getPath(){
    var data=[{time:'2016-3-5',massage:'到达太原'},{time:'2016-3-5',massage:'到达太原'},{time:'2016-3-6',massage:'从太原站发出'},{time:'2016-3-7',massage:'到达杭州'}];
    $.ajax({
      type:"POST",
      url:'',
      dataType:'json',
      success:function(data){
          var htmldata = '<div class="track-rcol" style="position: relative;left: 80px;"><div class="track-list" style="max-height: 350px;overflow-y: auto;"><span>物流信息</span><ul>';
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
    //var orderData = JSON.parse(sessionStorage.getItem("goodData"));
    var datas = [{name:'张三',date:'2016-10-01',replyContent:'东西不好'},{name:'张三',date:'2016-10-01',replyContent:'东西不好'},{name:'李四',date:'2016-10-02',replyContent:'亲，有质量问题我们可以退换噢'},{name:'张三',date:'2016-10-02',replyContent:'那退吧'}];
    $.ajax({
        //data:orderData.id,
        type:'POST',
        url:'',
        dataType:'json',
        success:function(data) {
            var evalutiondata = '<div class="ylcon"><p class="tit">所有评价 </p> <div id="messDivId" style="overflow-y: auto;max-height: 252px;">';
            if(datas.length > 0){
                for(var i = 0; i < datas.length; i++){
                    evalutiondata = evalutiondata + '<div class="story"><div class="opbtn"></div>'+
                        '<p class="story_t">'+ data[i].name+'</p>'+
                        '<p class="story_time">'+ data[i].date+'</p>' +
                        '<p class="story_m">'+ data[i].replyContent+'</p></div>'
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
        error:function(data){
            var evalutiondata = '<div class="ylcon"><p class="tit">所有评价 </p> <div id="messDivId" style="overflow-y: auto;max-height: 252px;">';
            if(datas.length > 0){
                for(var i = 0; i < datas.length; i++){
                    evalutiondata = evalutiondata + '<div class="story"><div class="opbtn"></div>'+
                        '<p class="story_t">'+ datas[i].name+'</p>'+
                        '<p class="story_time">'+ datas[i].date+'</p>' +
                        '<p class="story_m">'+ datas[i].replyContent+'</p></div>'
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
        }
    });
});
function reply(){
    var orderData = JSON.parse(sessionStorage.getItem("goodData"));
    var member = JSON.parse(sessionStorage.getItem("member"));
    $.ajax({
        url:'/order/evaluationReply',
        dataType:'json',
        type:'POST',
        data:{evaluation_id:orderData.id,message:$('#massage').text(),account:member.account},
        success:function(){
            alert('评论成功');
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
    $("#account").val(sData.account);
    $("#accountpassword").val(sData.password);
    $('#money').text(sData.money);
    $('#status').text(sData.orderStatus);
    $('#nowPlace').text(sData.currentCity);
    $('#arriveedTime').text(sData.TimeOfArrival);
    $('#startplace').text(sData.startCity);
    $('#endplace').text(sData.endCity);
    $('#descripe').text(sData.node);
    $('#createTime').text(sData.createTime);
    $("#operationGoodmodule").css('display','block');
    $("#enable").attr('disabled','disabled');
}
$("#cancel").click(function(){
    window.history.go(-1);
});