$(document).ready(function(){
    getPath();
    setValue();
});
function getPath(){
    var data=[{time:'2016-3-5',massage:'到达太原'},{time:'2016-3-6',massage:'从太原站发出'},{time:'2016-3-7',massage:'到达杭州'}];
    $.ajax({
      type:"POST",
      url:'',
      dataType:'json',

      success:function(data){
          var htmldata = '<div class="track-rcol"><div class="track-list"><span>物流信息</span><ul>';
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
          htmldata = htmldata + '</ul> </div> </div>';
          var s = document.getElementById('goodPath');
          s.innerHTML=htmldata;
      },
      error:function(){
          alert('操作失败！');
          window.history.back();
      }
  })
};
function setValue(){
    var sData = JSON.parse(sessionStorage.getItem("goodData"));
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