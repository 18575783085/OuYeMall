package cn.ou.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ou.entity.Product;
import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 加入购物车----实现对单个商品添加到购物车
 * @author Administrator
 *
 */
public class CartAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数
		//1.1获取商品id
		String id = request.getParameter("id");
		//1.2获取要添加商品的数量
		int buynum = Integer.parseInt(request.getParameter("buynum"));
		
		//2.调用业务层方法查询对应的商品对象
		//2.1创建业务对象
		ProdService ps = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//2.2调用业务层接口的方法
		Product prod = ps.findProdById(id);
		
		//3.从session中获取cart对象
		HttpSession session = request.getSession();
		Object cartObj = session.getAttribute("cart");
		
		//4.判断cartObj是否为null
		//（无论是否为null，执行后要保证session存在购物车对应的Map对象）
		//4.1声明一个集合对象
		Map<Product, Integer> cart = null;
		
		//4.2执行判断
		if(cartObj == null){
			//首次购买商品
			cart = new HashMap<Product, Integer>();
			//把购物车存进session域中
			request.getSession().setAttribute("cart", cart);
			
		}else {
			//非首次购买
			cart = (Map<Product, Integer>) cartObj;
		}
		
		//5.将prod 和buynum保存到cart中
		if(cart.containsKey(prod)){
			//当前商品已经购买过---同样的商品的数量加一
			cart.put(prod, cart.get(prod)+buynum);
		}else {
			//当前上未购买过商品
			cart.put(prod, buynum);
		}
		
		//6.跳转到cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
