package cn.ou.Web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.ou.Code.demo;
/**
 * ���÷����Žӿ�
 * @author Administrator
 *
 */
public class AjaxCheckSmsServlet extends HttpServlet {

	/**
	 * ����������
	 */
	private static int message = 0;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		// 1.1���������������
		request.setCharacterEncoding("UTF-8");
		
		//2.1.��ȡ�绰����
		String phone = request.getParameter("phonenumber");
		
		//2.2��ȡ������ɵ���֤��
        String code="";
       	for(int i=1;i<=6;i++){
       		code += (int)(Math.random()*9);
       	}
       	//2.2.1.����֤�뱣����session��
       	//�Ȼ�ȡһ��session����
       	HttpSession session = request.getSession();
       	
       	//2.2.2.����smscode���Ա���codeֵ
       	session.setAttribute("smscode", code);
       	//2.2.3.���浱ǰ���Ͷ��ŵ��ֻ������session��
       	session.setAttribute("smsphone", phone);
       	
		//3.���ö��Žӿ�
       	//ajax����ֵ��data
       	String data = "0";
       	
		try {
			//#�жϣ�������Ͷ�����������5�����򲻷��Ͷ��Ų����ؾ����ǰ̨����ִ�����´��룩#
			if(message < 5){
				// У��绰����
				String regex = "^1[3|5|8][0-9]{9}$";
				if (!phone.matches(regex) || phone == null) {
					// У��ʧ��
					response.getWriter().write("<font color='red'>�� ��������Ч���ֻ�����</font>");
					return;
				} else {
					
					// 3.1.���Ͷ��ţ����룬��֤�룩
					SendSmsResponse sendSms = demo.sendSms(phone, code);

					//�ȴ�3��
					Thread.sleep(3000L);

					// 3.2.����ϸ
					if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
						//���ͳɹ�
						data = "1";
						
						//���Ų�ѯapi
						QuerySendDetailsResponse querySendDetailsRequest = demo.querySendDetails(sendSms.getBizId(), phone);
						
						//��ȡ��������
						String totalCount = querySendDetailsRequest.getTotalCount();
						
						//�ۼ���Ŀ
						message = message + Integer.parseInt(totalCount);
						
					}else {
						//����ʧ��
						data = "0";
					}

				}
			}else {
				//һ���ֻ��������ɷ���5��������֤��
				data = "2";
				
			}
			
			response.setContentType("application/json;charset=UTF-8");  
	        response.setHeader("Cache-Control", "no-cache");  
	        PrintWriter out = response.getWriter();  
	        out.write(data); 
			
	        
	        //��׮
			System.out.println(phone);
			System.out.println(code);
			System.out.println("������"+message);
			
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
