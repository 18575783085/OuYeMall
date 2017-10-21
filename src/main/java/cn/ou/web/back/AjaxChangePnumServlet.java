package cn.ou.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 修改商品库存数量
 * @author Administrator
 *
 */
public class AjaxChangePnumServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接收参数
		//获取商品id
		String id = request.getParameter("id");
		//获取商品库存数量
		int pnum = Integer.parseInt(request.getParameter("newPnum"));
		
		//2.创建业务层对象
		ProdService ps = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//3.调用业务层修改数量的方法
		boolean result = ps.changePnum(id,pnum);
		
		//4.将结果响应输出
		response.getWriter().write(result+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
