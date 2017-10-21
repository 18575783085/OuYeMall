package cn.ou.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.Product;
import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 后台商品管理-----添加、删除、修改商品 
 * @author Administrator
 *
 */
public class BackProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.创建商品业务层对象
		ProdService prodService = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//2.调用商品业务层查询全部商品的方法
		List<Product> list = prodService.findAll();
		
		//3.将list保存request作用域中
		request.setAttribute("list", list);
		
		//4.转发到/back/prod_list.jsp页面
		request.getRequestDispatcher("/back/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
