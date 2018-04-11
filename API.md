##物流管理系统API

* 备注：每个接口的url都去掉了服务器路径地址
一般服务器地址为：localhost:8080,本API只给与URI和查询字段
* 错误代码备注：查看错误代码文档
* 成功返回若无数据要求，一般返回状态码 1 ，信息为： SUCCESS
* 如果有返回信息需求，请明确指出字段格式

* 登陆接口
method: POST
url:/public/login
{
  account:用户账号
  password:用户密码
}

*注册接口
method: POST
url:/generalUser/register
{
  account:用户账号
  password:用户密码
}

* 修改密码
method: POST
url:/public/updatePassword
{
  password:用户密码
}
* 条件管理员获取订单列表接口
method: POST
url:/Administrator/orderList
{
    page:分页
    size:页面显示数量
    order:排序字段
    sort:排序方式
    field:条件查询字段
    content:添加查询的内容
    fullMatch:是否是完全匹配
}