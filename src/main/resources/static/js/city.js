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
                    "<input class='btn btn-info' type='button' style='margin-right: 5px' onclick='editCity("+JSON.stringify(row)+")' value='编辑'>";
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
var clone;
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
                $("#addCity").append(row(clone));
            }
        },
        error: function () {
            alert('添加失败!');
        }
    });
});
/**************************************/
/*添加一行下一地*/
function addNextCity(){
    // $("#nextBtn").remote();
    console.log(clone);
    $("#addCity").append(row(clone));
}
let row = c => {
    return $(`<tr>
                <th>下一地点</th>
                <span></span>
                <td>
                    <select style="width: 150px;" name="dstCityIds">
                        ${c}
                    </select>
                </td>
                <th>到达下一地点的开销</th>
                <td>
                    <input type="text" value="0" name="distances">
                </td>
            </tr>`);
};
function addCity(){
    window.location.href='../templates/cityAdd.html';
}
function add(){
    if(checkInput()){
        enableAdd();
    }
}
function City(){
    var city = new Object;
    city.cityId = '';
    city.cost = '';
    return city;
}
/*将用户选择的城市id和开销封装*/
function getNextCitysMassage(){
    var citysMassage = [];
    $("#addCity").find("tr").each(function() {
        var tdArr = $(this).children();
        if (tdArr.length > 2) {
            var cityId = tdArr.eq(1).find("select option:selected").val();
            var cost = tdArr.eq(3).find("input").val();
            var newcity = City();
            newcity.cityId = cityId;
            newcity.cost = cost;
            citysMassage.push(newcity);
        }
    });
    console.log(citysMassage);
    return citysMassage;
}

function enableAdd(){
    $.ajax({
        data:$("#addFrom").serialize(),
        url:'/city/create',
        type:'POST',
        success:function(o){
            console.log(o);
            if(o == 1)
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
    return true;
}
function searchList() {
    $("#table").bootstrapTable('refresh');
    search = true;
}
showTable();
