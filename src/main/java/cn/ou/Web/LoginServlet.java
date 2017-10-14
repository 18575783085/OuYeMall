package cn.ou.Web;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ou.Util.JDBCUtils;

/**
 * 处理用户的登录请求
 * @author Administrator
 *
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.处理请求参数乱码
		request.setCharacterEncoding("utf-8");
		
		//2.获取登录信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");
		
		//3.登录用户（根据用户名和密码查询用户）
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "select * from user where username=? and password=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			//3.1.用户名和密码正确
			if(rs.next()){//用户名和密码正确
				//3.1.1.1.判断是否需要记住用户名(判断复选按钮的选择属性)
				if("true".equals(remname)){
					//3.1.1.2.将用户名进行url编码之后再存入Cookie中
					Cookie cookie = new Cookie("remname", URLEncoder.encode(username, "utf-8"));
					
					//3.1.1.3.设置Cookie的最大存活时间
					cookie.setMaxAge(3600 * 24 * 30);//30天
					
					//3.1.1.4.设置Cookie的路径
					cookie.setPath(request.getContextPath()+"/");
					
					//3.1.1.5.将Cookie添加到响应中
					response.addCookie(cookie);
					
				}else {
					//3.1.2.取消记住用户名（删除Cookie）
					Cookie cookie = new Cookie("remname", "");
					//3.1.2.立即删除Cookie!
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}
				
				//3.1.3.1进行登录操作（将用户信息保存进session中）
				//TODO --- 登录操作
				//创建session对象，向session保存当前用户的用户名
				HttpSession session = request.getSession();
				
				//将用户名和密码保存进session中
				session.setAttribute("username", username);
				//session.setAttribute("password", password);
				
				
				
				//3.1.4.登录成功，跳转页面
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				
				
			}else {
				//3.2.用户名或密码不正确
				request.setAttribute("msg", "用户名或密码不正确！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(conn, ps, rs);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
