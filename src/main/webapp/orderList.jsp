<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
 	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${appPath }/orderList.css" rel="stylesheet" type="text/css">
</head>
<body style="text-align:center;">
		<%@include file="_head.jsp" %>

		<dl class="Order_information">
			<dt>
				<h3>订单信息</h3>
			</dt>
			<dd>
				订单编号：1111<br />
				 下单时间：2015-01-11<br /> 
				 订单金额：199.0<br /> 
				 支付状态：
						<font color="red">未支付</font>&nbsp;&nbsp;&nbsp;
						<a href="/orderAction_delOrder.action?id=${oi.order.id }"><img src="img/orderList/sc.jpg" width="69" height="19"></a> 
				 		<a href="/forwardAction_forward.action?path=pay.jsp&id=${oi.order.id }&money=${oi.order.money }"> <img src="img/orderList/zx.jpg" width="69" height="19"></a><br /> 
						<font color="blue">已支付</font>
				 所属用户：xxx<br/> 
				 收货地址：zzz<br/> 
				支付方式：在线支付
			</dd>
		</dl>
	
		<table width="1200" border="0" cellpadding="0"
			cellspacing="1" style="background:#d8d8d8;color:#333333">
			<tr>
				<th width="276" height="30" align="center" valign="middle" bgcolor="#f3f3f3">商品图片</th>
				<th width="247" align="center" valign="middle" bgcolor="#f3f3f3">商品名称</th>
				<th width="231" align="center" valign="middle" bgcolor="#f3f3f3">商品单价</th>
				<th width="214" align="center" valign="middle" bgcolor="#f3f3f3">购买数量</th>
				<th width="232" align="center" valign="middle" bgcolor="#f3f3f3">总价</th>
			</tr>
			<tr>
				<td align="center" valign="middle" bgcolor="#FFFFFF">
					<img src="/prodAction_getImg.action?id=${entry.key.id }" width="90" height="105">
				</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">aaa</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">222元</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">2件</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">444元</td>
			</tr>
		</table>
		<div class="Order_price">444元</div>
		
		<%@include file="_foot.jsp" %>
</body>
</html>
