<%@ page language="java" import="java.util.*" isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>商品管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	body{
		font-family: "微软雅黑";
		background-color: #EDEDED;
	}
	h2{
		text-align: center;
	}
	table{ 
		margin: 0 auto; 
		/* width: 96%; */
		text-align: center;
		border-collapse:collapse;
	}
	td, th{ padding: 7px;}
	th{
		background-color: #DCDCDC;
	}
	th.ths{
		width: 100px;
	} 
	input.pnum{
		width:80px;
		height:25px;
		font-size: 18px;
		text-align:center;
	}
	
</style>
<!--引入jquery的js库-->
</head>
<body>
	<h2>商品管理</h2>
	<table border="1">
		<tr>
			<th>商品图片</th>
			<th width="200px">商品ID</th>
			<th class="ths">商品名称</th>
			<th class="ths">商品种类</th>
			<th class="ths">商品单价</th>
			<th class="ths">库存数量</th>
			<th>描述信息</th>
			<th width="50px">操 作</th>
		</tr>

		<!-- 模版数据 -->
	
	
<%-- 通过jstl+el获取所有商品组成的list集合, 遍历显示 --%>
	<c:forEach items="${requestScope.list }" var="prod">
		<tr>
			<td>
				<img width="120px" height="120px" src="${appPath }${prod.imgurl}" alt="" >
			</td>
			<td>${prod.id }</td>
			<td>${prod.name }</td>
			<td>${prod.category }</td>
			<td>${prod.price }</td>
			<td>
				<input type="text" id="#" class="pnum" oldPnum="#" value="${prod.pnum}"/>
			</td>
			<td>${prod.description }</td>
			<td><a class="del" href="javascript:void(0)">删 除</a></td>
		</tr>	

	</c:forEach>
	</table>
</body>
</html>
