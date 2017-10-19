<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css"/>
		<title>OuYeMall欢迎您登陆</title>
<%-- 		<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
		 <!-- 使用el+jstl对java解码进行简化 -->
		 <script>
		 	$(function(){
		 		var uname = "${cookie.remname.value}";
		 		$("#username").val(decodeURI(uname));
		 	});
		 </script> --%>
	</head>
	<body>
		<h1>欢迎登陆OuYeMall</h1>
		<form action="${pageContext.request.contextPath }/servlet/LoginServlet" method="GET">
			<table>
				<%--
					当用户来登陆时，获取请求中带过来的用户名，
					并赋值给用户名输入框完成记住用户名的操作
				 --%>
				
 				<%
					Cookie[] cs = request.getCookies();
					String username = "";
					
					if(cs != null){
						for(Cookie c : cs){
							if("remname".equals(c.getName())){
								username = c.getValue();
								
								//对用户名进行url解码
								username = URLDecoder.decode(username, "utf-8");
							}
						}
					}
				 %> 

				 <tr>
				 	<td colspan="2" style="color:red;text-align:center">
				 		<%= request.getAttribute("msg") == null ? "" : request.getAttribute("msg") %>
				 	</td>
				 </tr>
				<tr>
					<td class="tdx">用户名：</td>
					<td><input type="text" name="username"
							value="<%= username %>"/></td>
				</tr>
				<tr>
					<td class="tdx">密&nbsp;&nbsp; 码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true"
							<%= "".equals(username) ? "" : "checked='checked'" %>
						/>记住用户名
						<input type="checkbox" name="autologin" value="true"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<input type="submit" value="登 陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>

