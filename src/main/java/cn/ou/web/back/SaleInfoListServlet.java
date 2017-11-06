package cn.ou.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.SaleInfo;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;

public class SaleInfoListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.创建业务层对象
		OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
		
		//2.调用业务层方法
		List<SaleInfo> saleList = orderService.findSaleInfos();
		
		//3.将查询结果保存到request域中
		request.setAttribute("list", saleList);
		
		//4.转发到saleList.jsp
		request.getRequestDispatcher("/back/saleList.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
