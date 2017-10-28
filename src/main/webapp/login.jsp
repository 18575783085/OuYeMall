<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${appPath}/css/login.css"/>
		<title>OuYeMall欢迎您登陆</title>
 		<script src="${appPath}/js/jquery-1.4.2.js"></script>
		 <!-- 使用el+jstl对java解码进行简化 -->
		 <script>
		 	$(function(){
		 		/*  步骤：获取值--->转码--->赋值 */
		 		//获取username对应的输入框
		 		$ipt = $("input[name=username]");
		 		//转码
		 		var usernameval = decodeURI($ipt.val());
		 		
		 		//修改输入框的值
		 		$ipt.val(usernameval);
		 	});
		 </script>
	</head>
	<body>
		<h1>欢迎登陆OuYeMall</h1>
		<form action="${appPath}/servlet/LoginServlet" method="POST">
			<table>
				<%--
					当用户来登陆时，获取请求中带过来的用户名，
					并赋值给用户名输入框完成记住用户名的操作
				 --%>
				
 				<%-- <%
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
 --%>
				 <tr>
				 	<td colspan="2" style="color:red;text-align:center">
				 		${msg }
				 	</td>
				 </tr>
				<tr>
					<td class="tdx">用户名：</td>
					<td><input type="text" name="username"
							value="${cookie.remname.value }"/></td>
				</tr>
				<tr>
					<td class="tdx">密&nbsp;&nbsp; 码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true"
							${empty cookie.remname ? "" : "checked = 'checked'"}
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

