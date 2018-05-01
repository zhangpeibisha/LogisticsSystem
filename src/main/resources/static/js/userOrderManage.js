
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
//请求的数据是该用户的订单信息，账户名是参数
function getOrdersList() {
    var member = JSON.parse(sessionStorage.getItem('member'));
    $('#table').bootstrapTable({
        method: 'POST',
        striped : true,// 隔行变色效果
        pagination : true,// 在表格底部显示分页条
        pageNumber : 1,// 首页页码
        pageList : [5,10],// 设置可供选择的页面数据条数
        clickToSelect : false,// 设置true 将在点击行时，自动选择rediobox 和 checkbox
        cache : false,// 禁用 AJAX 数据缓存
        sortName : 'id',// 定义排序列
        sortOrder : 'asc',// 定义排序方式 getRceiptlistWithPaging
        //url : '/Administrator/orderList' + member.account,
        sidePagination : 'client',// 设置在哪里进行分页
        /*showRefresh: true, */ //显示刷新按钮
        contentType : 'application/x-www-form-urlencoded',// 发送到服务器的数据编码类型
        dataType : 'json',// 服务器返回的数据类型
        queryParams: function queryParams(params) {
            param.page = (params.offset/params.limit) + 1;
            param.size=params.limit;
            param.sort = params.sort; // 排序列名
            param.order = params.order; // 排位命令（desc，asc）
            param.field = 'order_status';
            param.content = 'ORDER_PAID_NO_SHIPPED';
            param.fullMatch = true;
            return param;
        },  // 请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数
        selectItemName : '',// radio or checkbox 的字段名
        onLoadSuccess:function (backData) {
            $('#table').bootstrapTable('removeAll');
            $('#table').bootstrapTable('append', backData.list);
        },
        data:[{id:'1',account:'123',password:'123456',node:'0000',money:'1000',orderStatus:'002',currentCity:'重庆',TimeOfArrival:'2016-12-20',startCity:'重庆',endCity:'河北',createTime:'2016-12-20'}
            ,{id:'2',account:'234',password:'123456',node:'1111',money:'2000',orderStatus:'004',currentCity:'浙江',TimeOfArrival:'2018-03-20',startCity:'台湾',endCity:'新疆',createTime:'2018-03-18'}],
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
            width : '1'// 宽度
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
            }
        }]
        // 列配置项,详情请查看 列参数 表格
        /* 事件 */
    });
}

/*展示方法*/
function show(data) {
    sessionStorage.setItem("goodData",JSON.stringify(data));
    window.location.href="../templates/showGoodInfo.html";
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