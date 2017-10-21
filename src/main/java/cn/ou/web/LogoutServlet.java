package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 注销用户
 * @author Administrator 
 *
 */
public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//销毁session
		request.getSession().invalidate();
		
		/*
		 * 遇到一个问题：注销后转发到index.jsp，就变成了未登录
		 * 如果需求是这样：注销后自动登录失效，关闭浏览器下次来还可以实现自动登录，
		 * 解决方法：修改注销的LogoutServlet,删除自动登录的cookie
		 */
		Cookie ck = new Cookie("autologin", null);
		ck.setMaxAge(0);
		ck.setPath("/");
		response.addCookie(ck);
		
		//重定向到首页
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		//注销后，跳转后还是显示登录状态，解决办法---->请求转发
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
