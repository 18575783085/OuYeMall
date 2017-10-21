package cn.ou.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 为了防止别人盗取自己的图片信息，所以建议将图片放在web-inf目录下
 * 因为安全机制，通过servlet来获取web-inf目录下的图片
 * @author Administrator
 *
 */
public class ProdImgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接受参数
		String imgurl = request.getParameter("imgurl");
		
		//TODO 将来可以添加控制代码：比如防盗链的处理
		
		//2.转发图片
		request.getRequestDispatcher(imgurl).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
