package cn.ou.Web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 实现功能：光标移走校验短信验证码
 * @author Administrator
 *
 */
public class AjaxCheckSmsCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		// 1.1处理请求参数乱码
		request.setCharacterEncoding("UTF-8");
		
		//2.获取用户输入的验证码
		String smsvalistr = request.getParameter("smsvalistr");
		//TODO 获取用户输入的手机号码参数
		String phonenumber = request.getParameter("phonenumber");
		
		//3.1.获取session存储的短信验证码
		String smscode = (String) request.getSession().getAttribute("smscode");
		
		//3.2.获取session存储的手机号码
		//String smsphone = (String) request.getSession().getAttribute("smsphone");
		
		//System.out.println("验证的手机号码："+smsphone);
		System.out.println("文本框的手机号码："+phonenumber);
		System.out.println("短信验证码："+smscode);
		//4.先判断是否是当前手机号码发送短信(这功能已迁移到registServet里实现) ---> 再判断短信验证码是否匹配
		String data = "1";
//		if(!smsphone.equals(phonenumber)){
//			//不是原来发短信的手机号
//			//response.getWriter().write("<font color='red'>× 该手机号码不是原来的号码，请重新获取验证码</font>");
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
			//如果不匹配
			response.getWriter().write("<font color='red'>× 验证码不正确,请重新获取</font>");
			
		}else{
			//如果匹配
			response.getWriter().write("<font color='#339933'>√ 验证码正确</font>");
		}*/

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
