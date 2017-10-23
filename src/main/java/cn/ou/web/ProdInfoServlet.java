package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.Product;
import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 前台商品详细信息----实现根据商品id查看对应的商品详情信息
 * @author Administrator
 *
 */
public class ProdInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数
		//1.1获取商品id
		String id = request.getParameter("id");
		
		//2.创建业务层对象
		ProdService ps = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//3.调用业务层接口查找商品详情方法
		Product prod = ps.findProdById(id);
		
		//4.将查询的结果保存到request域中
		request.setAttribute("prod", prod);
		
		//5.转发到prodInfo.jsp页面中
		request.getRequestDispatcher("/prodInfo.jsp").forward(request, response);
		
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
