<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>${sessionScope.user.username}的订单列表</title>
 	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${appPath }/css/orderList.css" rel="stylesheet" type="text/css">
	<script src="${appPath }/js/jquery-1.4.2.js"></script>
	<script>
		$(function(){
			$(".del").click(function(){
				if(confirm("您确定删除吗？")){
					//获取订单id
					var orderid = $(this).attr("id");
					location.href="${appPath}/OrderDeleteServlet?id="+orderid;
				}
			});
		});
	</script>
</head>
<body>
		<%@include file="_head.jsp" %>

	<c:forEach items="${requestScope.infoList }" var="info">
		<dl class="Order_information">
			<dt>
				<h3>订单信息</h3>
			</dt>
			<dd>
				订单编号：${info.orders.id }<br />
				 下单时间：${info.orders.ordertime }<br /> 
				 订单金额：${info.orders.money }<br /> 
				 支付状态：
				 	<c:if test="${info.orders.paystate == 0 }">
						<font color="red">未支付</font>&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${info.orders.paystate == 1 }">
						<font color="blue">已支付</font>
					</c:if>
						<a class="del" id="${info.orders.id }" href="javascript:void(0)"><img src="${appPath }/img/orderList/sc.jpg" width="69" height="19"></a> 
				 		<a href="${appPaht }/pay.jsp?id=${info.orders.id}&money=${info.orders.money}"> <img src="${appPath }/img/orderList/zx.jpg" width="69" height="19"></a><br /> 
				 收货地址：${info.orders.receiverinfo }<br/> 
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
					<th width="232" align="center" valign="middle" bgcolor="#f3f3f3">小计</th>
				</tr>
			<c:forEach items="${pageScope.info.map }" var="entry">
				<tr>
					<td align="center" valign="middle" bgcolor="#FFFFFF">
						<img src="${appPath }/ProdImgServlet?imgurl=${entry.key.imgurl}" width="90" height="105">
					</td>
					<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.name }</td>
					<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price }元</td>
					<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.value }件</td>
					<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price*entry.value }元</td>
				</tr>
			</c:forEach>
		</table>
		
		
		<div class="Order_price">支付金额：${info.orders.money }元</div>
	</c:forEach>
	
		<%@include file="_foot.jsp" %>
</body>
</html>
