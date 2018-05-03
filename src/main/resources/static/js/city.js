var param = {}
var search = false;
function showTable() {
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
        url : '/city/list',
        sidePagination : 'server',// 设置在哪里进行分页
        /*showRefresh: true, */ //显示刷新按钮
        contentType : 'application/x-www-form-urlencoded',// 发送到服务器的数据编码类型
        dataType : 'json',// 服务器返回的数据类型
        queryParams: function queryParams(params) {
            param.page = (params.offset/params.limit) + 1;
            param.size=params.limit;
            param.sort = params.order; // 排序列名
            param.order = params.sort; // 排位命令（desc，asc）
            param.field = search ? "city_name" : "";
            param.content = search ? $("#search").val() : "";
            param.fullMatch = false;
            return param;
        },  // 请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数
        selectItemName : '',// radio or checkbox 的字段名
        onLoadSuccess:function (backData) {
            $('#table').bootstrapTable('removeAll');
            $('#table').bootstrapTable('append', backData.data);
        },
        responseHandler:function(result) {
            return {
                total : result.map.total, //总页数,前面的key必须为"total"
                data : result.data //行数据，前面的key要与之前设置的dataField的值一致.
            };
        },
        columns : [ {
            title:'全选',
            field:'select',
            checkbox : true,
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '2'// 宽度
        },{
            title: '序号',//标题  可不加
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '2',// 宽度
            formatter: function (value, row, index) {
                return index+1;
            }
        }, {
            field : 'id',// 返回值名称
            title : '城市id',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '2',// 宽度
            visible:false
        }, {
            field : 'cityName',// 返回值名称
            title : '城市名',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        }, {
            field : 'createTime',// 返回值名称
            title : '添加时间',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '5'// 宽度
        }, {
            field : '',// 返回值名称
            title : '操作',// 列名
            align : 'center',// 水平居中显示
            valign :'middle',// 垂直居中显示
            width : '5',// 宽度
            formatter: function (value, row, index) {
                return "<input class='btn btn-info' type='button' style='margin-right: 5px' onclick='showCity("+JSON.stringify(row)+")' value='详情'>" +
                    "<input class='btn btn-info' type='button' style='margin-right: 5px' onclick='editCity("+JSON.stringify(row)+")' value='编辑'>" +
                    ("<input class='btn btn-danger' type='button' onclick='delCity("+JSON.stringify(row)+")' value='删除'>");
            }
        }
        ]
        // 列配置项,详情请查看 列参数 表格
        /* 事件 */
    });
}
//0:查看
//1:修改
function showCity(data) {
    var cityData = data;
    cityData.operation = 0;
    sessionStorage.setItem('cityData',JSON.stringify(cityData))
    window.location.href="../templates/cityDetails.html";
}
function editCity(data){
    var cityData = data;
    cityData.operation = 1;
    sessionStorage.setItem('cityData',JSON.stringify(cityData))
    window.location.href="../templates/cityDetails.html";
}

function addCity(){
    window.location.href='../templates/cityAdd.html';
}
function add(){
    if(checkInput()){
        enableAdd();
    }
}
function enableAdd(){
    var cityMassage = {
        cityName:$('#cityName').val(),
        nextCity:$('#nextCity').val(),
        cost:$('#cost').val()
    };
    $.ajax({
        data:cityMassage,
        url:'',
        type:'POST',
        success:function(){
            alert("添加成功!");
        },
        error:function(){
            alert("添加失败！");
        }
    });
}
function checkInput(){
    if($('#cityName').val() == null || $('#cityName').val() == ''){
        alert('请输入城市名！');
        return false;
    }
    if($('#nextCity').val() == null || $('#nextCity').val() == ''){
        alert('请输入下一地点名！');
        return false;
    }
    if($('#cost').val() == null || $('#cost').val() == ''){
        alert('请输入到达下一地点需要的开销！');
        return false;
    }
}

function delCity(data){
    if(confirm('确认删除?') == true){
        $.ajax({
            method:'POST',
            url: '/city/delete',
            data:data.id,
            success : function(o) {
                if (o.code == 'FAIL') {
                    alert("删除失败");
                }else if(o.code == 'SUCCESS'){

                    //删除一列数据成功在table中移除那行
                    $('#table').bootstrapTable('remove', {field: 'id', values: [data.id]});
                    alert("删除成功");
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert('删除失败！');
                if (XMLHttpRequest.status == 401) {
                    alert("权限不足！！！")
                }
            }
        });
    }
}
function searchList() {
    $("#table").bootstrapTable('refresh');
    search = true;
}
showTable();
