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

import cn.ou.Util.DaoUtils;
/**
 * 校验手机号码是否存在
 * @author Administrator
 *
 */
public class AjaxCheckPhoneServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1.处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		// 1.1处理请求参数乱码
		request.setCharacterEncoding("UTF-8");

		// 2.获取电话号码
		String phone = request.getParameter("phonenumber");
		
		//3.校验手机号码是否存在
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DaoUtils.getConnection();
			
			String sql = "select * from user where phone=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, phone);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				//手机号码已存在
				response.getWriter().write("<font color='red'>× 该手机号码已经被注册，请重新输入</font>");
				
			}else{
				//手机号码不存在
				response.getWriter().write("<font color='#339933'>√ 该手机号码可以注册，输入正确</font>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DaoUtils.close(conn, ps, rs);
		}
				

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
