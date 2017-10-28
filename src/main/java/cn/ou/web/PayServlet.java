package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.Orders;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;
import cn.ou.utils.PaymentUtil;
import cn.ou.utils.PropPayUntils;
/**
 * 获取订单参数和第三方支付平台参数，实现在线支付
 * @author Administrator
 *
 */
public class PayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// 1、准备参数
			//业务类型
			String p0_Cmd = "Buy";
			// 商户编号
			String p1_MerId = PropPayUntils.getProperty("p1_MerId");
			// 订单id
			String p2_Order = request.getParameter("orderid");
			// 订单金额：
			//String p3_Amt ="0.01";
			// 测试时使用//正式使用
			//创建订单业务层
			OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
			//调用通过订单id查询订单信息的方法
			Orders orders = orderService.findOrderByOid(p2_Order);
			String p3_Amt = orders.getMoney()+"";
			// 交易币种
			String  p4_Cur = "CNY";
			// 商品名称
			String p5_Pid = "";
			// 商品种类
			String p6_Pcat = "";
			// 商品描述
			String p7_Pdesc = "";
			//回调的url地址
			String p8_Url = PropPayUntils.getProperty("responseURL");
			// 收货地址
			String p9_SAF = "";
			// 商户扩展信息
			String pa_MP = "";
			// 支付通道--->选择银行
			String pd_FrpId = request.getParameter("pd_FrpId");
			//应答机制
			String pr_NeedResponse = "";
			//签名数据
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, PropPayUntils.getProperty("keyValue"));
			
			//2.将准备好的参数保存到request作用域中
			request.setAttribute("p0_Cmd", p0_Cmd);
			request.setAttribute("p1_MerId", p1_MerId);
			request.setAttribute("p2_Order", p2_Order);
			request.setAttribute("p3_Amt", p3_Amt);
			request.setAttribute("p4_Cur", p4_Cur);
			request.setAttribute("p5_Pid", p5_Pid);
			request.setAttribute("p6_Pcat", p6_Pcat);
			request.setAttribute("p7_Pdesc", p7_Pdesc);
			request.setAttribute("p8_Url", p8_Url);
			request.setAttribute("p9_SAF", p9_SAF);
			request.setAttribute("pa_MP", pa_MP);
			request.setAttribute("pd_FrpId", pd_FrpId);
			request.setAttribute("pr_NeedResponse", pr_NeedResponse);
			request.setAttribute("hmac", hmac);
			
			//3.转发到confirm.jsp
			request.getRequestDispatcher("/confirm.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
