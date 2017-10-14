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
import cn.ou.Util.WebUtils;

/**
 * ע�ᰴť�����ĺ�̨����
 * @author Administrator
 *
 */
public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.�������
		//1.1�������������루post�ύ��
		request.setCharacterEncoding("utf-8");
		//1.2�����Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		
		//2.��ȡ�û�����
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String phonenumber = request.getParameter("phonenumber");
		String smsvalistr = request.getParameter("smsvalistr");
		String valistr = request.getParameter("valistr");
		
		//3.����У�飨����Ϣ��Ӧ��ǰ�ˣ�
		//3.1.�жϲ����Ƿ�Ϊ�գ����Ϊ�գ�����ת���ص�ע��ҳ��
		if(WebUtils.isNull(username)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "�û�������Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "���벻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password2)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "ȷ�����벻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(nickname)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "�سƲ���Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(email)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "���䲻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(phonenumber)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "�ֻ����벻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(smsvalistr)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "������֤�벻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(valistr)){
			//����û���Ϊ�գ���ת��ע��ҳ����ʾ�û����û�������Ϊ�գ���
			request.setAttribute("msg", "��֤�벻��Ϊ�գ�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.2.�ж����������Ƿ�һ��
		if(!password.equals(password2)){
			request.setAttribute("msg", "�������벻һ�£�");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.3.�ж������ʽ�Ƿ���ȷ
		//��������^\\w+@\\w+(\\.[a-z]+)+$
		if(!email.matches("^\\w+@\\w+(\\.[a-z]+)+$")){
			request.setAttribute("msg", "�����ʽ����ȷ��");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.4�ж���֤���Ƿ�һ�£����� �ֻ� ��ȡ�� ��֤�룬�ٽ��� �жϣ�
		//3.4.1.�ж��ֻ������Ƿ���11λ
		if(!phonenumber.matches("^1[3|5|8][0-9]{9}$")){
			request.setAttribute("msg", "�ֻ������ʽ����ȷ��");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.5.�ж��ֻ���ȡ������֤���api�ӿڲ�����������Ƿ�һ��
		//TODO  �ԣ�����ֱ��ͨ��ǰ�˹�����ߣ�Ajax��ȡ���ú�̨�������ж϶�����֤���Ƿ�ƥ��
		
		
		//3.6.У��ͼƬ��֤��
		//TODO 
		//3.6.1.��ȡ������session��ͼƬ��֤��
		String code = (String) request.getSession().getAttribute("code");
		
		//3.6.2.�ж���֤���Ƿ�ƥ��
		if(!valistr.equals(code) || valistr != code || code == null){
			request.setAttribute("msg", "��֤�벻��ȷ��");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.7.�жϡ����ע���û���ʱ���ֻ����뷢�Ͷ��ź�����ע��ʱ�����ֻ������Ƿ�ǰ��һ��
		//3.7.1.��ȡsession�з��Ͷ��ź���ֻ����루AjaxCheckSmsServlet��
		String smsphone = (String) request.getSession().getAttribute("smsphone");
		
		//3.7.2.�����ж�
		if(!smsphone.equals(phonenumber)){//�ֻ�����ǰ��һ��
			request.setAttribute("msg", "�� ���ֻ����벻��ԭ���ĺ��룬�����»�ȡ��֤�룡");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.8.�ж��Ƿ��ظ��ύ
		// 1.���������л�ȡtoken
		String token1 = request.getParameter("token");
		// 2.��session�л�ȡtoken
		Object tkObj = request.getSession().getAttribute("token");

		if (tkObj != null && token1.equals((String) tkObj)) {
			// ��һ�����,��session�����token
			request.getSession().getAttribute("token");

		} else {// �ǵ�һ�����
			throw new RuntimeException("�벻Ҫ�ظ��ύ");

		}
		
		//4.ע���û�����ע����Ϣ���浽���ݿ⣩
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			
			//4.1.�ж��û����Ƿ��Ѵ���
			String sql = "select * from user where username=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			if(rs.next()){//�жϸ��û��Ƿ����
				//�Ѵ���
				request.setAttribute("msg", "�û����Ѵ��ڣ�");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			
			//4.2.�û��������ڣ����û���ע�����ݱ��浽���ݿ�
			sql = "insert into user values(null,?,?,?,?,?)";
			
			//4.3.���ò���
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, nickname);
			ps.setString(4, email);
			ps.setString(5, phonenumber);
			
			//4.4.ִ��sql���
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//4.5.�ر���Դ
			JDBCUtils.close(conn, ps, rs);
		}
		
		
		//5.��ʾ�û�ע��ɹ���3��֮����ת����ҳ
		response.getWriter().write("<h1 style='color:red;text-align:center'>" +
				"��ϲ��ע��ɹ�, 3��֮�󽫻���ת����ҳ...</h1>");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
		
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
