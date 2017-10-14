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
 * ����Ajax���ú�̨��Servlet
 * @author Administrator
 *
 */
public class AjaxCheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		//1.1���������������
		request.setCharacterEncoding("UTF-8");
		
		//2.��ȡ�û���
		String username = request.getParameter("username");
		
		//3.����û����Ƿ����
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "select * from user where username=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			if(rs.next()){//�û����Ѵ��ڣ�
				response.getWriter().write("<font style='color:red'>�û����Ѵ���</font>");
				
			}else{
				response.getWriter().write("<font style='color:red'>��ϲ�����û�������ʹ��</font>");
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
