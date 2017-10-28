<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>

	<title>支付订单</title>
	<link href="${appPath }/css/pay.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%@include file="_head.jsp" %>

	<form action="${appPath }/payServlet" method="post">
		<dl class="payment_page">
			<dt>
				<strong>订单号：</strong> 
				<input class="idinp" name="orderid" readonly="readonly" type="text" value="">
				<strong>支付金额：</strong>
				<input class="moneyinp" name="" type="text" readonly="readonly" type="text" value="">元
			</dt>
			<dd class="payment_page_name">
				<strong>请您选择在线支付银行 :</strong>
			</dd>
			<dd class="banks">
				<ul>
					<li>
						<input name="pd_FrpId" type="radio" value="ICBC-NET-B2C"> 
						<img src="${appPath}/img/pay/01gs.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="CMBCHINA-NET-B2C">
						<img src="${appPath}/img/pay/02zs.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="CCB-NET-B2C">
						<img src="${appPath}/img/pay/03js.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="ABC-NET-B2C">
						<img src="${appPath}/img/pay/04ny.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="BOC-NET-B2C">
						<img src="${appPath}/img/pay/05zg.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="BOCO-NET-B2C">
						<img src="${appPath}/img/pay/06jt.jpg" width="130" height="52">
					</li>
					<li>
						<input name="pd_FrpId" type="radio" value="HXB-NET-B2C">
						<img src="${appPath}/img/pay/07hx.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="CIB-NET-B2C">
						<img src="${appPath}/img/pay/08xy.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="GDB-NET-B2C">
						<img src="${appPath}/img/pay/09gd.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="SDB-NET-B2C">
						<img src="${appPath}/img/pay/10sz.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="CMBC-NET-B2C">
						<img src="${appPath}/img/pay/11ms.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="SPDB-NET-B2C">
						<img src="${appPath}/img/pay/12sh.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="ECITIC-NET-B2C">
						<img src="${appPath}/img/pay/13zx.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="CEB-NET-B2C">
						<img src="${appPath}/img/pay/14gd.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="">
						<img src="${appPath}/img/pay/15cq.jpg" width="130" height="52">
					</li>
	
					<li>
						<input name="pd_FrpId" type="radio" value="CBHB-NET-B2C">
						<img src="${appPath}/img/pay/16bh.jpg" width="130" height="52">
					</li>
				</ul>
			</dd>
			<div style="clear: both;"></div>
			<dd class="ok_dd">
				<input  type="submit" class="ok_pay" value="确认支付">
			</dd>
		</dl>
	</form>
	
	<%@include file="_foot.jsp" %>
</body>
</html>
