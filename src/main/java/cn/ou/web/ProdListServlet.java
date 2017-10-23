package cn.ou.web;

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
 * 前台页面-----实现前端展示所有商品列表
 * @author Administrator 
 *
 */
public class ProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接收参数
		//获取商品名称
		String nameString = request.getParameter("name");
		//获取商品种类
		String catesString = request.getParameter("category");
		//获取最小值
		String minpriceString = request.getParameter("minprice");
		//获取最大值
		String maxpriceString = request.getParameter("maxprice");
		
		//2.参数为null的处理
		//2.1、定义四个变量，分别给默认值
		String name = "";
		String cate = "";
		Double min = null;
		Double max = null;
		
		//2.2、处理为null
		//String：判断接收参数有内容去掉空格后赋值
		if(nameString != null &&  !"".equals(nameString)){
			name = nameString.trim();
		}
		if(catesString != null && !"".equals(catesString)){
			cate = catesString.trim();
		}
		//Double：判断接收的参数有内容，去掉空格类型转换后再赋值
		if(minpriceString != null && !"".equals(minpriceString)){
			min = Double.parseDouble(minpriceString.trim());
		}
		if(maxpriceString != null && !"".equals(maxpriceString)){
			max = Double.parseDouble(maxpriceString.trim());
		}
		
		//3.创建业务层对象
		ProdService ps = BasicFactory.getBasicFactory().getInstance(ProdService.class);
		
		//4.调用根据查询条件查全部符合条件的商品
		List<Product> list = ps.findAllById(name,cate,min,max);
		
		//5.将list保存到request域中
		request.setAttribute("list", list);
		
		//6.将4个参数保存到request--->实现查询条件的回显
		request.setAttribute("name", name);
		request.setAttribute("cate", cate);
		request.setAttribute("min", min);
		request.setAttribute("max", max);
		
		//7.转发到/prodList.jsp页面
		request.getRequestDispatcher("/prodList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
