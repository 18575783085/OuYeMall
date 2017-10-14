package cn.ou.Web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ou.Util.VerifyCode;

public class ValiImageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* �����������Ҫ������֤��ͼƬ */
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		
		/* ����һ����֤��ͼƬ���͸������ */
		VerifyCode vCode = new VerifyCode();
		vCode.drawImage(response.getOutputStream());
		
		//��ȡ��֤���ı�
		String valistr = vCode.getCode();
		
		//����session����
		HttpSession session = request.getSession();
		
		//��ͼƬ��֤�뱣�浽session��,��servlet��֤
		session.setAttribute("code", valistr);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
