package cn.ou.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.OrderInfo;
import cn.ou.entity.User;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;
/**
 * 实现用户的订单列表
 * @author Administrator
 *
 */
public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.判断用户是否登录
		//1.1、从session获取获取用户参数
		Object userObj = request.getSession().getAttribute("user");
		
		//1.2、判断用户是否登录
		if(userObj == null){
			//未登录---跳转到login.jsp
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		//2.获取当前用户id
		int userId = ((User)userObj).getId();
		
		//3、调用业务层的查询方法
		//3.1、创建业务层OrderService对象
		OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
		
		//3.2、调用业务层的方法查询结构
		List<OrderInfo> list = orderService.findOrderInfosByUid(userId);
		
		//4.将查询结果保存到request中
		request.setAttribute("infoList", list);
		
		//5.转发到orderList.jsp
		request.getRequestDispatcher("/orderList.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
