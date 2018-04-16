##物流管理系统API

* 备注：每个接口的url都去掉了服务器路径地址
一般服务器地址为：localhost:8080,本API只给与URI和查询字段
* 错误代码备注：查看错误代码文档
* 成功返回若无数据要求，一般返回状态码 1 ，信息为： SUCCESS
* 如果有返回信息需求，请明确指出字段格式

###公共API

* 接口名：登陆
* 使用介绍：用于用户进入系统的入口，验证为系统用户才能使用该系统功能
* 使用权限：财务管理、系统管理、用户
* uri:/public/login
* 参数
{
   * account:用户账号
   * password:用户加密密码
  
}

==========
* 接口名：修改密码
* 使用介绍：更新用户密码
* 使用权限：所有角色都可以使用
* url：/public/updatePassword
* 参数
{
   * password:用户修改后的密码
}

###普通用户API

* 接口：注册接口
* 使用介绍：普通用户注册路径
* 使用权限：只适用于注册用户使用
* url: /generalUser/register
* 参数
{
  * account:用户账号
  * password:用户密码

}

###管理员API

* 接口：查看订单列表
* 使用介绍：通过条件请求到需要查询的订单列表
* 使用权限：管理员可以查看所有人的订单和订单详情，普通用户只能查看自己的订单和订单详情
* url: /administrator/orderList
* 参数
{
    * page: 当前页码
    * size: 每页多少行数据
    * order: order的id
    * sort: 是否排序
    * field: 数据库字段
    * content: 查询内容
    * fullMatch: 是否完全匹配

}

==========
* 接口：订单受理
* 使用介绍：管理员查询到订单付款后，手动受理订单，并开始派送
* 使用权限：管理员角色
* url: /administrator/orderHandler
* 参数
{
   * order：包含了order信息的对象
}

###城市API

* 接口：添加城市
* 使用介绍：为派送业务增加一个能够送达的城市
* 使用权限：管理员
* url: /city/create
* 参数
{
   * name:城市的名字
}

==========
* 接口：添加相邻城市或者修改城市间距离
* 使用介绍：为一个城市添加一个相邻的城市，或者修改两个城市之间的距离
* 使用权限：管理员
* url: /city/neighbor
* 参数
{
   * srcCityId; 起始城市
   * dstCityId: 目的城市
   * distance: 距离
   * status: ??
}

==========
* 接口：删除城市
* 使用介绍：删除一个或多个不需要派送的城市
* 使用权限：管理员
* url:  /city/delete
* 参数
{
   * ids:城市的id
}

==========
* 接口：查询城市信息
* 使用介绍：根据城市名字查询城市信息
* 使用权限：管理员
* url: /city/search
* 参数
{
   * name:城市名字
}

==========
* 接口：查询城市详情
* 使用介绍：根据城市id查询城市的详细信息
* 使用权限：管理员
* url: /city/details/{id}
* 参数
{
   * id:城市id
}

==========
* 接口：分页查询城市列表
* 使用介绍：通过给定条件查询城市列表信息
* 使用权限：管理员
* url: /city/cityList 
* 参数
{
  * : 当前页码 
  * size: 每页多少行数据
  * order: order的id
  * sort: 是否排序
  * field: 数据库字段
  * content: 查询内容
  * fullMatch: 是否完全匹配
}

###定单API

* 接口：下订单
* 使用介绍：用户填写信息，然后提交订单等待付款
* 使用权限：普通用户
* url: /order/publishOrder
* 参数
{
   * order:包含了订单信息的对象
}

==========
* 接口：支付订单
* 使用介绍：用户下单后，需要支付后公司才能受理
* 使用权限：普通用户
* url: /order/paymentOrder
* 参数
{
   * order_id:订单id
}

==========
* 接口：查看订单详情
* 使用介绍：根据订单查询到订单的详细信息
* 使用权限：管理员、普通用户
* url: /order/viewOrderInfo
* 参数
{
   * order_id:订单id
}

==========
* 接口：订单签收
* 使用介绍：货物运到，用户签收订单，完成交易
* 使用权限：普通用户
* url: /url/signOrder
* 参数
{
   * order_id:订单id
}

==========
* 接口：评价订单
* 使用介绍：用户根据公司运送情况评价订单
* 使用权限：普通用户
* url: /order/orderEvaluation
* 参数
{
   * order_id:被评价的订单
   * message:评价的信息
}

==========
* 接口：回复订单评价
* 使用介绍：用户可以和客户互相评论
* 使用权限：普通用户，管理员
* url: /order/evaluationReply
* 参数
{
   * evaluation_id:评论id
   * message:评价信息 
}

==========
* 接口：
* 使用介绍：
* 使用权限：
* url: 
* 参数
{
   
}