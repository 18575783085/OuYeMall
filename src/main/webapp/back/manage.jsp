<%@ page language="java" import="java.util.*" isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>后台管理页面</title>
		<meta charset="utf-8"/>
		<style type="text/css">
		</style>
	</head>
	<!-- frameset组织页面结构 -->
	<frameset rows="14%, 86%" frameborder="0" framespacing="0">
		<frame src="${appPath }/back/_top.jsp" noresize="noresize"/>
		<frameset cols="14%, 86%" frameborder="0" framespacing="0">
			<frame src="${appPath }/back/_left.jsp" noresize="noresize"/>
			<frame src="${appPath }/back/_right.jsp" name="rightFrame"  noresize="noresize"/>
		</frameset>
	</frameset>
	<body>
	</body>
</html>