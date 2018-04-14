var cityId = getQueryVariable("id");
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}
$(window).resize(function() {
    $('#table').bootstrapTable('resetView', {
        height: tableHeight()
    })
})
function showTable() {
    $('#table').bootstrapTable({
        method: 'get',
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        url:"/city/details/" + cityId,//要请求数据的文件路径
        height:tableHeight(),//高度调整
        toolbar: '#toolbar',//指定工具栏
        striped: true, //是否显示行间隔色
        dataField: "res",//bootstrap table 可以前端分页也可以后端分页，这里
        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        pageNumber: 1, //初始化加载第一页，默认第一页
        pagination:true,//是否分页
        paginationLoop:true,
        queryParamsType:'limit',//查询参数组织方式
        queryParams:queryParams,//请求服务器时所传的参数
        sidePagination:'server',//指定服务器端分页
        pageSize:20,//单页记录数
        pageList:[20,30],//分页步进值
        showRefresh:true,//刷新按钮
        showColumns:true,
        undefinedText:"-",
        clickToSelect: true,//是否启用点击选中行
        toolbarAlign:'right',//工具栏对齐方式
        buttonsAlign:'right',//按钮对齐方式
        toolbar:'#toolbar',//指定工作栏
        onLoadSuccess:function (backData) {
            $('#table').bootstrapTable('removeAll');
            $('#table').bootstrapTable('append', backData.data.dstList);
        },
        columns : [ {
            title:'全选',
            field:'select',
            checkbox : true,
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1%'// 宽度
        }, {
            field : 'line',// 返回值名称
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1',// 宽度
            visible : false
        },{
            title: '序号',//标题  可不加
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '2',// 宽度
            formatter: function (value, row, index) {
                return index+1;
            }
        }, {
            field : 'primaryKey.srcCity.cityName',// 返回值名称
            title : '起点城市',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1'// 宽度
        },  {
            field : 'primaryKey.dstCity.cityName',// 返回值名称
            title : '相邻城市',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1'// 宽度
        }, {
            field : 'distance',// 返回值名称
            title : '距离',// 列名
            align : 'center',// 水平居中显示
            valign : 'middle',// 垂直居中显示
            width : '1'// 宽度
        },  {
            field : 'primaryKey.dstCity.id',// 返回值名称
            visible : false
        },{
            field : '',// 返回值名称
            title : '操作',// 列名
            align : 'center',// 水平居中显示
            valign :'middle',// 垂直居中显示
            width : '10',// 宽度
            formatter: function (value, row, index) {
                return "<input class='btn btn-info' type='button' style='margin-right: 5px' onclick='edit("+JSON.stringify(row)+","+index +")' value='编辑'>" +
                 "<input class='btn btn-danger' type='button' onclick='del("+JSON.stringify(row)+")' value='删除'>";
            }
        }
        ]
    })
}
//请求服务数据时所传参数
function queryParams(params){
    return{
    }
}
function del(data) {
    if(confirm('确认删除?') == true){
        $.ajax({
            method:'POST',
            url: '/city/neighbor?status=delete&srcCityId=' + cityId + "&dstCityId=" + data.primaryKey.dstCity.id,
            success : function(o) {
                if (o.status == 1) {
                    //删除一列数据成功在table中移除那行
                    $('#table').bootstrapTable('remove', {field: 'primaryKey.dstCity.cityName', values: [data.primaryKey.dstCity.cityName]});
                    alert("删除成功");
                }else if(o.code == 'SUCCESS'){
                    alert("删除失败");
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    alert("权限不足！！！")
                }
            }
        });
    }
}
function edit(data) {

}
function tableHeight(){
    //可以根据自己页面情况进行调整
    return $(window).height() - 50;
}

showTable();
