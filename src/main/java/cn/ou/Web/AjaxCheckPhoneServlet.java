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
 * У���ֻ������Ƿ����
 * @author Administrator
 *
 */
public class AjaxCheckPhoneServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1.������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		// 1.1���������������
		request.setCharacterEncoding("UTF-8");

		// 2.��ȡ�绰����
		String phone = request.getParameter("phonenumber");
		
		//3.У���ֻ������Ƿ����
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "select * from user where phone=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, phone);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				//�ֻ������Ѵ���
				response.getWriter().write("<font color='red'>�� ���ֻ������Ѿ���ע�ᣬ����������</font>");
				
			}else{
				//�ֻ����벻����
				response.getWriter().write("<font color='#339933'>�� ���ֻ��������ע�ᣬ������ȷ</font>");
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
