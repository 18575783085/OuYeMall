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
		/* 控制浏览器不要缓存验证码图片 */
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		
		/* 绘制一张验证码图片发送给浏览器 */
		VerifyCode vCode = new VerifyCode();
		vCode.drawImage(response.getOutputStream());
		
		//获取验证码文本
		String valistr = vCode.getCode();
		
		//创建session对象
		HttpSession session = request.getSession();
		
		//把图片验证码保存到session中,在servlet验证
		session.setAttribute("code", valistr);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
