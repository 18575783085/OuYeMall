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
 * �����û��ĵ�¼����
 * @author Administrator
 *
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.���������������
		request.setCharacterEncoding("utf-8");
		
		//2.��ȡ��¼��Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");
		
		//3.��¼�û��������û����������ѯ�û���
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
			
			//3.1.�û�����������ȷ
			if(rs.next()){//�û�����������ȷ
				//3.1.1.1.�ж��Ƿ���Ҫ��ס�û���(�жϸ�ѡ��ť��ѡ������)
				if("true".equals(remname)){
					//3.1.1.2.���û�������url����֮���ٴ���Cookie��
					Cookie cookie = new Cookie("remname", URLEncoder.encode(username, "utf-8"));
					
					//3.1.1.3.����Cookie�������ʱ��
					cookie.setMaxAge(3600 * 24 * 30);//30��
					
					//3.1.1.4.����Cookie��·��
					cookie.setPath(request.getContextPath()+"/");
					
					//3.1.1.5.��Cookie��ӵ���Ӧ��
					response.addCookie(cookie);
					
				}else {
					//3.1.2.ȡ����ס�û�����ɾ��Cookie��
					Cookie cookie = new Cookie("remname", "");
					//3.1.2.����ɾ��Cookie!
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}
				
				//3.1.3.1���е�¼���������û���Ϣ�����session�У�
				//TODO --- ��¼����
				//����session������session���浱ǰ�û����û���
				HttpSession session = request.getSession();
				
				//���û��������뱣���session��
				session.setAttribute("username", username);
				//session.setAttribute("password", password);
				
				
				
				//3.1.4.��¼�ɹ�����תҳ��
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				
				
			}else {
				//3.2.�û��������벻��ȷ
				request.setAttribute("msg", "�û��������벻��ȷ��");
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
