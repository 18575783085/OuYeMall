package cn.ou.Web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ʵ�ֹ��ܣ��������У�������֤��
 * @author Administrator
 *
 */
public class AjaxCheckSmsCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		// 1.1���������������
		request.setCharacterEncoding("UTF-8");
		
		//2.��ȡ�û��������֤��
		String smsvalistr = request.getParameter("smsvalistr");
		//TODO ��ȡ�û�������ֻ��������
		String phonenumber = request.getParameter("phonenumber");
		
		//3.1.��ȡsession�洢�Ķ�����֤��
		String smscode = (String) request.getSession().getAttribute("smscode");
		
		//3.2.��ȡsession�洢���ֻ�����
		//String smsphone = (String) request.getSession().getAttribute("smsphone");
		
		//System.out.println("��֤���ֻ����룺"+smsphone);
		System.out.println("�ı�����ֻ����룺"+phonenumber);
		System.out.println("������֤�룺"+smscode);
		//4.���ж��Ƿ��ǵ�ǰ�ֻ����뷢�Ͷ���(�⹦����Ǩ�Ƶ�registServet��ʵ��) ---> ���ж϶�����֤���Ƿ�ƥ��
		String data = "1";
//		if(!smsphone.equals(phonenumber)){
//			//����ԭ�������ŵ��ֻ���
//			//response.getWriter().write("<font color='red'>�� ���ֻ����벻��ԭ���ĺ��룬�����»�ȡ��֤��</font>");
//			data = "3";
//		}else {
			
			if(null == smscode){
				data = "2";
			}else if (!smscode.equals(smsvalistr)) {
				data = "0";
			}
		//}
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Cache-Control", "no-cache");  
        PrintWriter out = response.getWriter();  
        out.write(data);
		
		 

        
		/*if(!smsvalistr.equals(smscode) || smsvalistr != smscode){
			//�����ƥ��
			response.getWriter().write("<font color='red'>�� ��֤�벻��ȷ,�����»�ȡ</font>");
			
		}else{
			//���ƥ��
			response.getWriter().write("<font color='#339933'>�� ��֤����ȷ</font>");
		}*/

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
