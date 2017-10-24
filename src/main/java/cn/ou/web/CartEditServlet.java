package cn.ou.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ou.entity.Product;

/**
 * 修改购物车商品数量-----增加商品('+'和'-'按钮)
 * @author Administrator
 *
 */
public class CartEditServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接收参数
		//1.1获取商品id
		String id = request.getParameter("id");
		
		//1.2获取购买这个商品的数量
		int buyNum = Integer.parseInt(request.getParameter("newBuyNum"));
		
		//2.从session中获取cart
		HttpSession session = request.getSession();
		Object cartObj = session.getAttribute("cart");
		
		//3.判断cartObj是否为null
		if(cartObj == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else {
			//4.不为null
			//4.1强制类型转换
			Map<Product, Integer> cart = (Map<Product, Integer>) cartObj;
			
			//4.2创建Product对象
			Product product = new Product();
			
			product.setId(id);
			
			//5.执行修改操作
			//添加到集合中
			cart.put(product, buyNum);
			
			//6.跳转到cart.jsp页面
			response.sendRedirect(request.getContextPath()+"/cart.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
