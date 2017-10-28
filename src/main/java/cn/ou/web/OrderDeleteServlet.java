package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.exception.MsgException;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;
/**
 * 实现删除该用户的一张（未支付）订单
 * @author Administrator
 *
 */
public class OrderDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收订单id（url地址？id）
		//1.1、获取订单id参数
		String id = request.getParameter("id");
		
		//2.创建业务层对象
		OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
		
		try {
			//3.调用业务层的删除（一张）订单的方法
			orderService.deleteOrderByOid(id);
			
			//4、删除订单成功，给予提示
			response.getWriter().write("<font style='color:red;text-align:center'>恭喜您删除成功！</font>");
		} catch (MsgException me) {
			//4、删除订单失败，给予提示
			response.getWriter().write("<font style='color:red'>不好意思，删除失败！</fonr>");
		}
		
		//5、设置自动跳转(定时刷新)
		response.setHeader("refresh", "3;url="+request.getContextPath()+"/OrderListServlet");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
