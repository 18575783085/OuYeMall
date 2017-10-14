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
 * 调用发短信接口
 * @author Administrator
 *
 */
public class AjaxCheckSmsServlet extends HttpServlet {

	/**
	 * 短信总条数
	 */
	private static int message = 0;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		// 1.1处理请求参数乱码
		request.setCharacterEncoding("UTF-8");
		
		//2.1.获取电话号码
		String phone = request.getParameter("phonenumber");
		
		//2.2获取随机生成的验证码
        String code="";
       	for(int i=1;i<=6;i++){
       		code += (int)(Math.random()*9);
       	}
       	//2.2.1.将验证码保存在session中
       	//先获取一个session对象
       	HttpSession session = request.getSession();
       	
       	//2.2.2.设置smscode属性保存code值
       	session.setAttribute("smscode", code);
       	//2.2.3.保存当前发送短信的手机号码进session中
       	session.setAttribute("smsphone", phone);
       	
		//3.调用短信接口
       	//ajax返回值：data
       	String data = "0";
       	
		try {
			//#判断：如果发送短信总数超过5条，则不发送短信并返回警告给前台（不执行以下代码）#
			if(message < 5){
				// 校验电话号码
				String regex = "^1[3|5|8][0-9]{9}$";
				if (!phone.matches(regex) || phone == null) {
					// 校验失败
					response.getWriter().write("<font color='red'>× 请输入有效的手机号码</font>");
					return;
				} else {
					
					// 3.1.发送短信（号码，验证码）
					SendSmsResponse sendSms = demo.sendSms(phone, code);

					//等待3秒
					Thread.sleep(3000L);

					// 3.2.查明细
					if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
						//发送成功
						data = "1";
						
						//短信查询api
						QuerySendDetailsResponse querySendDetailsRequest = demo.querySendDetails(sendSms.getBizId(), phone);
						
						//获取短信条数
						String totalCount = querySendDetailsRequest.getTotalCount();
						
						//累加数目
						message = message + Integer.parseInt(totalCount);
						
					}else {
						//发送失败
						data = "0";
					}

				}
			}else {
				//一个手机号码最多可发送5条短信验证码
				data = "2";
				
			}
			
			response.setContentType("application/json;charset=UTF-8");  
	        response.setHeader("Cache-Control", "no-cache");  
	        PrintWriter out = response.getWriter();  
	        out.write(data); 
			
	        
	        //打桩
			System.out.println(phone);
			System.out.println(code);
			System.out.println("条数："+message);
			
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
