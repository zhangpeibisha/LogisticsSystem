
var param = {};

//修改密码
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
$('#addbtn').click(function (){
    window.location.href='../templates/orderAdd.html';
});
function getOrdersList() {
    $('#table').bootstrapTable({
        method: 'POST',
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        // contentType : 'application/json',// 发送到服务器的数据编码类型
        striped : true,// 隔行变色效果
        pagination : true,// 在表格底部显示分页条
        pageNumber : 1,// 首页页码
        pageList : [5,10],// 设置可供选择的页面数据条数
        clickToSelect : false,// 设置true 将在点击行时，自动选择rediobox 和 checkbox
        cache : false,// 禁用 AJAX 数据缓存
        sortName : 'id',// 定义排序列
        sortOrder : 'asc',// 定义排序方式 getRceiptlistWithPaging
        // url : '/order/orderListConditionalPaging',// 服务器数据的加载地址
        sidePagination : 'client',// 设置在哪里进行分页
        showRefresh: true,  //显示刷新按钮
        dataType : 'json',// 服务器返回的数据类型
        queryParamsType:'limit',//查询参数组织方式
        queryParams:queryParams,//请求服务器时所传的参数
        selectItemName : '',// radio or checkbox 的字段名
        // onLoadSuccess:function (backData) {
        //     $('#table').bootstrapTable('removeAll');
        //     $('#table').bootstrapTable('append', backData.data);
        // },
        data:[{id:'1',account:'123',password:'123456',node:'0000',money:'1000',orderStatus:'未发货',currentCity:'重庆',TimeOfArrival:'2016-12-20',startCity:'重庆',endCity:'河北',createTime:'2016-12-20'}
            ,{id:'2',account:'234',password:'123456',node:'1111',money:'2000',orderStatus:'已发货',currentCity:'浙江',TimeOfArrival:'2018-03-20',startCity:'台湾',endCity:'新疆',createTime:'2018-03-18'}],
        columns : [ {
            checkbox : true,
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '2'// 宽度
        },{
            title: '序号',//标题  可不加
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5',// 宽度
            formatter: function (value, row, index) {
                return index+1;
            }
        }, {
            field : 'id',// 返回值名称
            title : '订单号',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1',// 宽度
        }, {
            field : 'account',// 返回值名称
            title : '用户名',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1',// 宽度
            visible:false
        }, {
            field : 'password',// 返回值名称
            title : 'password',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1',// 宽度
            visible : false
        } ,{
            field : 'node',// 返回值名称
            title : '备注',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5',// 宽度
            visible : false
        },{
            field : 'money',// 返回值名称
            title : '金额',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '10'// 宽度
        },  {
            field : 'orderStatus',// 返回值名称
            title : '订单状态',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '15',// 宽度
        }, {
            field : 'currentCity',// 返回值名称
            title : '当前地点',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '15',// 宽度
        }, {
            field : 'TimeOfArrival',// 返回值名称
            title : '到达当前地点时的时间',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        },{
            field : 'startCity',// 返回值名称
            title : '起点',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        },{
            field : 'endCity',// 返回值名称
            title : '终点',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        },{
            field : 'createTime',// 返回值名称
            title : '创建时间',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        },{
            field : '',// 返回值名称
            title : '操作',// 列名
            align : 'center',// 水平居中显示
            valign :'middle',// 垂直居中显示
            width : '5',// 宽度
            formatter: function (value, row, index) {
                return "<input class='btn btn-info' type='button' onclick='show("+JSON.stringify(row)+")' value='详情'>"
                +  "<input class='btn btn-info' type='button' onclick='handler("+JSON.stringify(row)+")' value='受理'>"
            }
        }]
        // 列配置项,详情请查看 列参数 表格
        /* 事件 */
    });
}
function handler(date) {
    $.ajax({
        type: 'POST',
        url: "/administrator/orderHandler",
        dataType: 'json',
        //组装order实体参数
        data: {
            field: fiel,
            content: info
        },
        success: function (data) {
        }
    })
}
function queryParams(params) {
    return {
        page : 1,
        size:999999,
        field : 'order_status',
        content : 'ORDER_PAID_NO_SHIPPED',
        fullMatch : true
    };
}

/*展示方法*/
function show(data) {
    sessionStorage.setItem("goodData",JSON.stringify(data));
    window.location.href="../templates/goodInfoShow.html";
}

$('#searchbtn').click(function () {
    var info = $('#search').val();
    var fiel = $("#filed").val();

    $.ajax({
        type: 'POST',
        url: "/member/list.do",
        dataType: 'json',
        data: {
            field: fiel,
            content: info
        },
        success: function (data) {
            console.log(data);
            if (data.code === 'SUCCESS') {
                $('#table').bootstrapTable('removeAll');
                $('#table').bootstrapTable('append', data.list);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == 401) {
                alert("权限不足！！！")
            }
        }
    })
});
getOrdersList();