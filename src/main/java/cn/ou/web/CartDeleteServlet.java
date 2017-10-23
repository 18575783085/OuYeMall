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
 * 购物车删除商品-----其实就是删除该商品的session
 * @author Administrator
 *
 */
public class CartDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接收参数
		//1.1获取商品id
		String id = request.getParameter("id");
		
		//2.从session中获取cart
		HttpSession session = request.getSession();
		Object cartObj = session.getAttribute("cart");
		
		//3.判断cartObj是否为null
		if(cartObj == null){
			//为null，重定向回到index.jsp
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else {
			//不为null
			//4.执行删除商品的操作
			Map<Product, Integer> cart = (Map<Product, Integer>) cartObj;
			
			//创建商品对象实例
			Product product = new Product();
			product.setId(id);
			
			//从map集合中移除该商品id
			cart.remove(product);
			
			//5.重定向回到首页index.jsp
			response.sendRedirect(request.getContextPath()+"/cart.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
