package cn.ou.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 后台商品管理---删除商品
 * @author Administrator
 *
 */
public class BackProdDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取参数
		//获取商品id
		String id = request.getParameter("id");
		
		//2.创建业务层对象
		ProdService ps = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//3.调用业务层的删除方法
		boolean result = ps.deleteProdById(id);
		
		//4.根据result提示相关信息
		if (result) {
			response.getWriter().write("删除成功！");
		}else {
			response.getWriter().write("删除失败！");
		}
		//5.设置定时刷新---->跳转到显示全部商品的servlet（原因：返回商品列表.jsp，没有商品显示）
		response.setHeader("refresh", "3;url="+request.getContextPath()+"/back/BackProdListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
