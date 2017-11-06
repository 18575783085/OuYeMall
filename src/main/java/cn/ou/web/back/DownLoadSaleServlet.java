package cn.ou.web.back;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.entity.SaleInfo;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;

public class DownLoadSaleServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.调用业务层对象方法
		//1.1创建业务层对象
		OrderService orderService = BasicFactory.getBasicFactory().getInstance(OrderService.class);
		
		//1.2调用业务层方法(查找销售信息)
		List<SaleInfo> list = orderService.findSaleInfos();
		
		//2.创建StringBuffer对象
		StringBuffer stringBuffer = new StringBuffer("商品id,商品名称,销售数量\n");
		
		//3.遍历list集合
		for(SaleInfo saleInfo:list ){
			//追加字符串
			stringBuffer.append(saleInfo.getProd_id()).append(",").append(saleInfo.getProd_name()).append(",").append(saleInfo.getSale_num()).append("\n");
		}
		//自定义文件名称
		String filename = "saleList"+getTimeStamp()+".csv";
		
		//4.告知浏览器以附件下载的方式识别文件
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		//处理文件内容的乱码问题
		response.setContentType("text/html;charset=UTF-8");
		
		//5.将内容响应浏览器
		response.getWriter().write(stringBuffer.toString());
		
	}
	
	/**
	 * 自定义时间戳的方法
	 */
	private String getTimeStamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
