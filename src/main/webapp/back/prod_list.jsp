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
<script type="text/javascript" src="${appPath }/js/jquery-1.4.2.js"></script>
<script>
	$(function(){
		//为商品数量的输入框绑定失去焦点的事件
		$("input[name=pnum]").blur(function(){
			//1.获取修改后的值
			var newPnum = $(this).val();
			
			//2.获取对应的隐藏域对象的值
			var oldPnum = $(this).next().val();
			
			//3.只用新的值和原来的值不同时才有必要执行后续代码
			if(newPnum != oldPnum){
				//编写正则表达式
				var regex = /^[0]|([1-9][0-9]*)$/;
				
				if(!regex.test(newPnum.trim())){//判断是否合法
					//不合法
					//提示信息
					alert("请输入大于等于0的整数");
					//数据还原
					$(this).val(oldPnum);
					
				}else{
					//数据合法
					//获取商品id
					var id = $(this).attr("id");
					var $hid = $(this).next();					
					//使用ajax来进行传参更新数据
					$.post(
						"${appPath}/back/AjaxChangePnumServlet",//目标地址
						{"id":id,"newPnum":newPnum},//json参数
						function(data){//返回结果
							if("true"==data){
								alert("商品数量修改成功！^_^ ！");
								//将对应隐藏域的值更新
								//原因：同一件商品第一次被修改成功，页面无刷新，再次修改，修改数量非法；
								//数据还原时还原的是第一次进入当前页面的值
								$hid.val(newPnum);
							}else{
								alert("商品数量修改失败~~~~(>_<)~~~~");
								//失败应返回对上一次修改的数据
								$(this).val(oldPnum);
							}
							
						
						}
					);
				}
			
			}
			
		});
		
		
		/* 为删除的超链接上绑定点击时间----实现删除商品 */
		$(".del").click(function(){
			if(confirm("您确定删除吗？")){
				//获取商品的id
				var id = $(this).parent().prev().prev().children("[type=text]").attr("id");
				
				alert(id);
				
				//跳转到删除的servlet
				location.href="${appPath}/back/BackProdDeleteServlet?id="+id;
			
			}
		});
		
	});
</script>
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
				<img width="120px" height="120px" src="${appPath }/ProdImgServlet?imgurl=${prod.imgurl}" alt="商品图片暂无" >
			</td>
			<td>${prod.id }</td>
			<td>${prod.name }</td>
			<td>${prod.category }</td>
			<td>${prod.price }</td>
			<td>
				<input type="text" id="${prod.id }" name="pnum" class="pnum"  value="${prod.pnum}"/>
				<input type="hidden" name="oldPnum" value="${prod.pnum }" />
			</td>
			<td>${prod.description }</td>
			<td><a class="del" href="javascript:void(0)">删 除</a></td>
		</tr>	

	</c:forEach>
	</table>
</body>
</html>
