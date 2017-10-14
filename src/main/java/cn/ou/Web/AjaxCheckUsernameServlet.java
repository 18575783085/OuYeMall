package cn.ou.Web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.Util.JDBCUtils;
/**
 * 利用Ajax调用后台该Servlet
 * @author Administrator
 *
 */
public class AjaxCheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		//1.1处理请求参数乱码
		request.setCharacterEncoding("UTF-8");
		
		//2.获取用户名
		String username = request.getParameter("username");
		
		//3.检查用户名是否存在
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "select * from user where username=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			if(rs.next()){//用户名已存在！
				response.getWriter().write("<font style='color:red'>用户名已存在</font>");
				
			}else{
				response.getWriter().write("<font style='color:red'>恭喜，该用户名可以使用</font>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtils.close(conn, ps, rs);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
