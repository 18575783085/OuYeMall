package cn.ou.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import cn.ou.entity.User;

public class RoleFilter implements Filter {

	//分别保存user.txt 和 admin.txt
	private List<String> userList;
	private List<String> adminList;
	
	/**
	 * 通过判断角色来决定访问哪些页面和使用哪些功能
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		userList = new ArrayList<String>();
		adminList = new ArrayList<String>();
		
		try {
			//普通用户权限
			//获取文件的全文件名（绝对路径+文件名+后缀名）
			//获取文件所在的路径
			String path = filterConfig.getServletContext().getRealPath("/WEB-INF/user.txt");
			
			//创建字符输入流，来进行读取文件数据
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = null;
			
			while((line = reader.readLine()) != null){
				//每读取一行数据，就把数据存进用户集合中
				userList.add(line);
			}
			
			//管理员权限
			//获取文件的绝对路径
			path = filterConfig.getServletContext().getRealPath("/WEB-INF/admin.txt");
			
			//创建字符输入缓冲流---->读取文件里面的数据
			reader = new BufferedReader(new FileReader(path));
			
			while((line = reader.readLine()) != null){
				//每读取一行数据，就把数据存进集合中
				adminList.add(line);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断当前登录的账号是什么角色
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//先强制转换类型
		HttpServletRequest request2 = (HttpServletRequest) request;
		
		//1.获取用户请求的uri(web应用)
		String uri = request2.getRequestURI();
		
		if(userList.contains(uri) || adminList.contains(uri)){
			//需要权限、
			Object userObj = request2.getSession().getAttribute("user");
			
			if(userObj == null){//用户未登录 --> login.jsp
				//请求转发回到登录页面
				request2.getRequestDispatcher("/login.jsp").forward(request, response);
				
			}else {//已经登录
				//获取用户角色
				String role = ((User)userObj).getRole();
				
				if("user".equals(role) && userList.contains(uri)){
					//放行
					chain.doFilter(request, response);
				}else {
					response.getWriter().write("权限不足!");
				}
			}
		}else {//不需要权限，直接放行
			//假如浏览一些不需要权限的页面，放行请求响应
			chain.doFilter(request, response);
			
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
