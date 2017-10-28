package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;
import cn.ou.utils.PaymentUtil;
import cn.ou.utils.PropPayUntils;
/**
 * 调用第三方支付平台的API
 * @author OYE
 *
 */
public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收第三方支付平台传回的参数
		String p1_MerId = request.getParameter("p1_MerId");//商户编号
		String r0_Cmd = request.getParameter("r0_Cmd");//业务类型
		String r1_Code = request.getParameter("r1_Code");//支付结果
		String r2_TrxId = request.getParameter("r2_TrxId");//支付交易流水号
		String r3_Amt = request.getParameter("r3_Amt");//支付金额
		String r4_Cur = request.getParameter("r4_Cur");//交易币种
		String r5_Pid = request.getParameter("r5_Pid");//商品名称
		String r6_Order = request.getParameter("r6_Order");//商品订单号（订单id）
		String r7_Uid = request.getParameter("r7_Uid");//易宝支付会员ID
		String r8_MP = request.getParameter("r8_MP");//商户拓展信息
		String r9_BType = request.getParameter("r9_BType");//交易结果返回的类型：1表示重定向；2表示点对点通讯
		String rb_BankId = request.getParameter("rb_BankId");//支付通道编码
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");//银行订单编号
		String rp_PayDate = request.getParameter("rp_PayDate");//支付成功时间
		String rq_CardNo = request.getParameter("rq_CardNo");//充值卡序号
		String ru_Trxtime = request.getParameter("ru_Trxtime");//交易结果通知时间
		String hmac = request.getParameter("hmac");//签名数据
		
		
		//2.验证数据是否被篡改
		boolean result = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, PropPayUntils.getProperty("keyValue"));
		if(!result){//表示数据被篡改了
			System.out.println("数据被篡改了...");
			
		}else {//未被篡改
			//判断重定向过来的还是点对点通讯过来的
			if ("1".equals(r9_BType)) {
				//以下两行代码正式部署时要记得删除掉
				//创建订单业务层对象
				OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
				
				//修改订单的支付状态
				orderService.changePaystate(r6_Order,1);
				
				//重定向过来的
				response.getWriter().write("您的支付请求已被受理，支付结果需等待进一步的通知");
				
			}else if ("2".equals(r9_BType)&&"1".equals(r1_Code)) {
				//点对点通知
				//创建订单业务层对象
				OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
				
				//修改订单的支付状态
				orderService.changePaystate(r6_Order,1);
				
				//响应给第三方支付平台success---意思是告诉支付平台，确认支付的意思
				response.getWriter().write("success");
				
				
				
			}
			
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
