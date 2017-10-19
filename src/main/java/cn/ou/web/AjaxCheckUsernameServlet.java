package cn.ou.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.factory.BasicFactory;
import cn.ou.service.UserService;
import cn.ou.service.impl.UserServiceImpl;
import cn.ou.utils.DaoUtils;
/**
 * 利用Ajax调用后台该Servlet
 * @author Administrator
 *
 */
public class AjaxCheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*//1.处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		//1.1处理请求参数乱码
		request.setCharacterEncoding("UTF-8");*/
		
		//2.获取用户名(接收参数)
		String username = request.getParameter("username");
		
		//3.创建业务层
		//UserService userService = new UserServiceImpl();
		//解耦----> 使用通用工厂类(泛型)
		UserService userService = BasicFactory.getBasicFactory().getInstance(UserService.class);
		
		
		//4.调用业务层的方法检查用户名是否存在
		boolean result = userService.unisExist(username.trim());
		
		//5.根据查询的是否存在的结果做对应的响应
		if(result){//用户名已存在！
				response.getWriter().write("<font style='color:red'>用户名已存在</font>");
				
			}else{
				response.getWriter().write("<font style='color:red'>恭喜，该用户名可以使用</font>");
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
