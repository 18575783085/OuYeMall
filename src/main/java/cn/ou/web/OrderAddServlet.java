package cn.ou.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
import cn.ou.entity.Product;
import cn.ou.entity.User;
import cn.ou.exception.MsgException;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;

/**
 * 实现结算订单添加到订单列表
 * @author Administrator
 *
 */
public class OrderAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.判断用户是否登录，如果未登录就请求转发到login.jsp
		//1.1获取登陆用户
		Object userObj = request.getSession().getAttribute("user");
		
		//1.2判断用户是否已登录
		if(userObj == null){
			request.setAttribute("msg","不好意思，请麻烦用户您先登录再添加订单!");
			//为什么要使用请求转发呢，因为把订单信息也跟着一起跳转
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		//2.获取购物车对象
		Object cartObj = request.getSession().getAttribute("cart");
		
		//2.1判断购物车是否为空
		if(cartObj == null){//为null
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		
		//3.创建Order类对象，并为其属性赋值
		Orders orders = new Orders();
		
		//3.1获取订单id
		orders.setId(UUID.randomUUID().toString());
		
		//3.3获取添加订单的日期
		orders.setOrdertime(new Date());
		
		//3.4获取支付状态---0:未支付；1：已支付
		orders.setPaystate(0);
		
		//3.5获取收信人信息
		orders.setReceiverinfo(request.getParameter("receiverinfo"));
		
		//3.6获取用户id
		orders.setUser_id(((User)userObj).getId());
		
		//4.遍历购物车中的商品信息，并封装List<OrderItem>和计算money订单金额
		//4.1定义double类型的money
		double money = 0;
		
		//4.2定义集合List<OrderItem>，保存订单项目对象的集合
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		
		//4.3获取购物车集合
		Map<Product, Integer> cart = (Map<Product, Integer>) cartObj;
		
		//4.4遍历购物车中的商品
		for(Map.Entry<Product, Integer> entry:cart.entrySet()){
			//4.5创建订单列表对象
			OrderItem orderItem = new OrderItem();
			
			//4.6给订单列表的属性赋值
			//4.6.1获取订单id
			orderItem.setOrder_id(orders.getId());
			
			//4.6.2获取商品id
			orderItem.setProduct_id(entry.getKey().getId());
			
			//4.6.3获取订单商品数量
			orderItem.setBuynum(entry.getValue());
			
			//4.6.4计算商品总金额=单价*数量
			money = entry.getKey().getPrice()*entry.getValue();
			
			//4.7将订单列表添加到集合
			oiList.add(orderItem);
		}
		
		//5.(3.2)获取订单总金额
		orders.setMoney(money);
		
		
		//6.创建业务层对象
		OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
		
		try {
			//6.1调用业务层接口添加订单方法---参数2传入集合--是把集中的元素存进到数据库的订单列表中
			orderService.addOrder(orders,oiList);
			
			//7.添加订单成功
			//7.1清空购物车
			cart.clear();
			
			//7.2提示添加成功
			response.getWriter().write("<font style='color:red'>订单添加成功，"+"3秒后自动跳转</font>");
			
			//7.3设置定时刷新---跳转到订单列表
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/OrderListServlet");
			
		} catch (MsgException e) {
			//7.如果添加失败
			//7.1设置提示信息
			request.setAttribute("msg", e.getMessage());
			
			//7.2转发到cart.jsp
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
