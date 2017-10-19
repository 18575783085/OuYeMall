package cn.ou.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
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

import cn.ou.utils.DaoUtils;
import cn.ou.utils.MDUtil;

/**
 * 处理用户的登录请求
 * @author Administrator
 * 序号解释：
 * x.x.x.x
 * 登陆步骤.判断用户名是否存在.判断用户密码是否匹配.判断用户是否勾选记住用户名
 *
 */
public class CopyOfLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.处理请求参数乱码
		request.setCharacterEncoding("utf-8");
		
		//2.获取登录信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");
		
		//3.登录用户（根据用户名和密码查询用户）
		//由于用户的密码加密存放到数据，所以先查询该用户的密码，
		//然后进行解密，再判断输入的密码是否跟解密后的匹配
		//匹配成功进行下一步操作
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DaoUtils.getConnection();
			
			//String sql = "select * from user where username=? and password=?";
			//3.1.先找出登陆用户的密码
			String sql = "select * from user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			//ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			//3.2.用户名和密码正确(先判断是否存在该用户)
			if(rs.next()){//用户名和密码正确(用户存在)
				//3.2.1获取用户的加密密码
				String encodePassword = rs.getString("password");
				
				//3.2.2调用解密方法，对加密密码进行解密，然后判断解密后的密码是否跟当前输入的密码匹配一致
				boolean decodeBoolean = MDUtil.validPassword(password, encodePassword);
				if(decodeBoolean){//3.2.3.如果密码匹配一致
					
					//3.2.3.1.判断是否需要记住用户名(判断复选按钮的选择属性)
					if("true".equals(remname)){//3.2.3.1.如果勾选记住用户名
						
						//3.2.3.1.1.将用户名进行url编码之后再存入Cookie中
						Cookie cookie = new Cookie("remname", URLEncoder.encode(username, "utf-8"));
						
						//3.2.3.1.2.设置Cookie的最大存活时间
						cookie.setMaxAge(3600 * 24 * 30);//30天
						
						//3.2.3.1.3.设置Cookie的路径
						cookie.setPath(request.getContextPath()+"/");
						
						//3.2.3.1.4.将Cookie添加到响应中
						response.addCookie(cookie);
						
					}else {
						//3.2.3.2.取消记住用户名（删除Cookie）----->不勾选
						Cookie cookie = new Cookie("remname", "");
						//3.2.3.2.1.立即删除Cookie!
						cookie.setMaxAge(0);
						cookie.setPath(request.getContextPath()+"/");
						response.addCookie(cookie);
					}
					
					//3.2.3.3.进行登录操作（将用户信息保存进session中）
					//TODO --- 登录操作
					//创建session对象，向session保存当前用户的用户名
					HttpSession session = request.getSession();
					
					//将用户名和密码保存进session中
					session.setAttribute("username", username);
					//session.setAttribute("password", password);
					
					
					
					//3.2.3.4.登录成功，跳转页面
					response.sendRedirect(request.getContextPath()+"/index.jsp");
					
					
				}else {
					//3.2.4.用户名或密码不正确
					request.setAttribute("msg", "用户名或密码不正确！");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					
					return;
				}
				
			}else {
				//3.3.用户名或密码不正确(用户不存在)
				request.setAttribute("msg", "该用户不存在,请重新输入！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.close(conn, ps, rs);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
