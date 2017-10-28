<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE HTML>
<html>
    <head>
    	<title>${sessionScope.user.username }的购物车</title>
		<link href="${appPath }/css/cart.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${appPath }/js/jquery-1.4.2.js"></script>
		<script type="text/javascript">
			/* 当用户点击删除商品按钮时，提醒用户是否确定删除的提示框 */
			$(function(){
				$(".del").click(function(){
					if(confirm("您确定删除吗？~~~~(>_<)~~~~")){
						//如何获取商品的id-->通过调用该事件的商品的父元素(span--div)，再寻找属性名是type，属性值是text的元素，再再获取该元素的id属性
						var id = $(this).parent().parent().find("[type=text]").attr("id");
						//请求Servlet时在地址拼接参数
						location.href="${appPath}/CartDeleteServlet?id="+id;
					}
				});
				
				/* 修改商品数量 */
				//为"-"绑定点击事件
				$(".delNum").click(function(){
					//获取购买的数量对应的输入框对象---"-"按钮下一个元素
					var $buyNumInput = $(this).next();
					
					//获取商品id
					var id = $buyNumInput.attr("id");
					
					//计算修改(减少)后购买数量
					var newBuyNum = $buyNumInput.val()-1;
					
					//将修改后的结果影响到session
					if(newBuyNum <= 0){//删除
						//当商品数量等于0时，整行清空
						location.href="${appPath}/CartDeleteServlet?id="+id;
						
					}else{//修改
						//当商品减少数量但不等于0时，商品价格也跟着一起减----并且拼接（商品id）和（购买商品数量）传输到后台
						location.href="${appPath}/CartEditServlet?id="+id+"&newBuyNum="+newBuyNum;
					}
				});
				
				//为"+"绑定点击事件
				$(".addNum").click(function(){
					//1.获取对应输入框对象----"+"按钮上一个元素
					var $bni = $(this).prev();
					
					//2.获取商品id
					var id = $bni.attr("id");
					
					//3.修改后的属性=获取原购买数量+1
					var newBuyNum = parseInt($bni.val())+1;
					
					//4.跳转到CartEditServlet----并且拼接（商品id）和（购买商品数量）传输到后台
					location.href="${appPath}/CartEditServlet?id="+id+"&newBuyNum="+newBuyNum;
				});
				
				//为输入框绑定失去焦点的事件
				$(".buyNumInp1").blur(function(){
					//1.获取商品的id
					var id = $(this).attr("id");
					
					//2.获取旧的购买数量
					var oldNum = $("#hid_"+id).val();
					
					//3.获取修改后的数量
					var newNum = $(this).val();
					
					//规定了数量的范围
					var regex = /^[1-9][0-9]*$/;
					
					if(oldNum != newNum){
						if(newNum == 0){
							location.href="${appPath}/CartDeleteServlet?id="+id;
							
						}else if(regex.test(newNum)){
							//跳转到CartEditServlet----并且拼接（商品id）和（购买商品数量）传输到后台
							location.href="${appPath}/CartEditServlet?id="+id+"&newBuyNum="+newNum;
							
						}else{
							alert("请输入大于等于0的整数(*^__^*)");
							$(this).val(oldNum);
						}
					}
				});
			});
		</script>
		
	</head>
	<body>
		<%@include file="_head.jsp" %>
	
		<div class="warp">
			${msg }
			
			<!-- 标题信息 -->
			<div id="title">
				<input name="allC" type="checkbox" value="" onclick=""/>
				<span id="title_checkall_text">全选</span>
				<span id="title_img">商品图片</span>
				<span id="title_name">商品</span>
				<span id="title_price">单价（元）</span>
				<span id="title_buynum">数量</span>
				<span id="title_money">小计（元）</span>
				<span id="title_del">操作</span>
			</div>
			<!-- 购物信息 -->
		<c:set var="money" value="0" />
		<c:forEach items="${sessionScope.cart }" var="entry">
			<div id="prods">
				<input name="prodC" type="checkbox" value="" onclick=""/>
				<img src="${appPath }/ProdImgServlet?imgurl=${entry.key.imgurl}" width="90" height="90" />
				<span id="prods_name">${entry.key.name }</span>
				<span id="prods_price">${entry.key.price }</span>
				<span id="prods_buynum"> 
					<a href="javascript:void(0)" class="delNum" >-</a>
					<input id="${entry.key.id }" class="buyNumInp1" type="text" value="${entry.value }" >
					<a href="javascript:void(0)" class="addNum" >+</a>
					<input type="hidden" id="hid_$(entry.key.id)" value="${entry.value }"/>
				</span>
				<span id="prods_money">${entry.key.price*entry.value }</span>
				<span id="prods_del"><a class="del"href="#">删除</a></span>
			</div>
			<c:set var="money" value="${money+entry.key.price*entry.value }" />
		</c:forEach>			
			<!-- 总计条 -->
			<div id="total">
				<div id="total_1">
					<input name="allC" type="checkbox" value=""/> 
					<span id="total_all">全选</span>
					<a id="del_a" href="#">删除选中的商品</a>
					<span id="span_1">总价：</span>
					<span id="span_2">${money }</span>
				</div>
				<div id="total_2">	
					<a id="goto_order" href="${appPath }/orderAdd.jsp">去结算</a>
				</div>
			</div>
		</div>
		
		<%@include file="_foot.jsp" %>
	</body>
</html>