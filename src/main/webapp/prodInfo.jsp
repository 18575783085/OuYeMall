<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>商品详情</title>
  <link href="${appPath }/css/prodInfo.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="${appPath }/js/jquery-1.4.2.js"></script>
  <script type="text/javascript">
  		$(function(){
	  		/* 给添加购物车按钮增添增加到购物车的事件 */
	  		$(".add_cart_but").click(function(){
	  			//获取数量文本框的参数值
	  			var buynum = $("#buyNumInp").val();
	  			//跳转到添加商品的servlet
	  			location.href="${appPath}/CartAddServlet?id=${prod.id}&buynum="+buynum;
	  		});
  		});
  		
  </script>
</head>
<body>
	<%@include file="_head.jsp" %>
	
	<div id="warp">
		<div id="left">
			<div id="left_top">
				<img src="${appPath }/ProdImgServlet?imgurl=${prod.imgurl}"/>
			</div>
			<div id="left_bottom">
				<img id="lf_img" src="${appPath }/img/prodInfo/lf.jpg"/>
				<img id="mid_img" src="${appPath }/ProdImgServlet?imgurl=${prod.imgurl}" width="60px" height="60px"/>
				<img id="rt_img" src="${appPath }/img/prodInfo/rt.jpg"/>
			</div>
		</div>
		<div id="right">
			<div id="right_top">
				<span id="prod_name">${prod.name }<br/></span>
				<br>
				<span id="prod_desc">${prod.description }<br/></span>
			</div>
			<div id="right_middle">
				<span id="right_middle_span">
				 EasyMall 价：<span class="price_red">￥${prod.price }<br/></span>
			            运     费：满 100 免运费<br />
			            服     务：由EasyMall负责发货，并提供售后服务<br />
			            库     存：${prod.pnum }
			            购买数量：
	            <a href="#" id="delNum" onclick="">-</a>
	            <input id="buyNumInp" name="" type="text" value="1" onblur="">
		        <a href="#" id="addNum" onclick="">+</a>
		        </span>
			</div>
			<div id="right_bottom">
				<input class="add_cart_but" type="button"/>	
			</div>
		</div>
	</div>
	
	<%@include file="_foot.jsp" %>
</body>
</html>