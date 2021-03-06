<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 引入JSTL标签库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<link rel="stylesheet" href="${appPath}/css/head.css"/>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

<div id="common_head">
	<div id="line1">
		<div id="content">
		<%-- <%
			//获取session中保存的username对象
			Object username = session.getAttribute("username");
			
			if(username != null){
			%>
			欢迎&nbsp;<%= (String) username %>&nbsp;&nbsp;|&nbsp;<a href="${appPath}/servlet/LogoutServlet">注销</a>
				
		<%	}else{ %>
			<a href="${appPath}/login.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${appPath}/regist.jsp">注册</a>
			
		<%} %> --%>
		<c:if test="${sessionScope.user != null }" var="flag">
			欢迎&nbsp;${sessionScope.user.username }&nbsp;&nbsp;|&nbsp;<a href="${appPath}/servlet/LogoutServlet">注销</a>
		</c:if>		
		<c:if test="${!flag }">
			<a href="${appPath}/login.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${appPath}/regist.jsp">注册</a>
		</c:if>
		
		</div>
	</div>
	<div id="line2">
		<img id="logo" src="${appPath}/img/head/logo.jpg"/>
		<input type="text" name=""/>
		<input type="button" value="搜 索"/>
		<span id="goto">
			<a id="goto_order" href="${appPath }/OrderListServlet">我的订单</a>
			<a id="goto_cart" href="${appPath }/cart.jsp">我的购物车</a>
		</span>
		<img id="erwm" src="${appPath}/img/head/qr.jpg"/>
	</div>
	<div id="line3">
		<div id="content">
			<ul>
				<li><a href="${appPath }/index.jsp">首页</a></li>
				<li><a href="${appPath }/ProdListServlet">全部商品</a></li>
				<li><a href="#">手机数码</a></li>
				<li><a href="#">电脑平板</a></li>
				<li><a href="#">家用电器</a></li>
				<li><a href="#">汽车用品</a></li>
				<li><a href="#">食品饮料</a></li>
				<li><a href="#">图书杂志</a></li>
				<li><a href="#">服装服饰</a></li>
				<li><a href="#">理财产品</a></li>
			</ul>
		</div>
	</div>
</div>
