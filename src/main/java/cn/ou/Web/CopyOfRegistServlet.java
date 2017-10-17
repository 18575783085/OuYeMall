package cn.ou.Web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ou.Util.DaoUtils;
import cn.ou.Util.MDUtil;
import cn.ou.Util.WebUtils;

/**
 * 注册按钮触发的后台操作
 * @author Administrator
 *
 */
public class CopyOfRegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.解决乱码
		//1.1解决请求参数乱码（post提交）
		request.setCharacterEncoding("utf-8");
		//1.2解决响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		
		//2.获取用户参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String phonenumber = request.getParameter("phonenumber");
		String smsvalistr = request.getParameter("smsvalistr");
		String valistr = request.getParameter("valistr");
		
		//3.数据校验（将信息响应到前端）
		//3.1.判断参数是否为空，如果为空，请求转发回到注册页面
		if(WebUtils.isNull(username)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "用户名不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "密码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password2)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "确认密码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(nickname)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "呢称不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(email)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "邮箱不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(phonenumber)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "手机号码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(smsvalistr)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "短信验证码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(valistr)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "验证码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.2.判断两次密码是否一致
		if(!password.equals(password2)){
			request.setAttribute("msg", "两次密码不一致！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.3.判断邮箱格式是否正确
		//邮箱正则：^\\w+@\\w+(\\.[a-z]+)+$
		if(!email.matches("^\\w+@\\w+(\\.[a-z]+)+$")){
			request.setAttribute("msg", "邮箱格式不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.4判断验证码是否一致（根据 手机 获取的 验证码，再进行 判断）
		//3.4.1.判断手机号码是否是11位
		if(!phonenumber.matches("^1[3|5|8][0-9]{9}$")){
			request.setAttribute("msg", "手机号码格式不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.5.判断手机获取短信验证码跟api接口产生的随机码是否一样
		//TODO  略：可以直接通过前端光标移走，Ajax获取调用后台数据来判断短信验证码是否匹配
		
		
		//3.6.校验图片验证码
		//TODO 
		//3.6.1.获取保存在session的图片验证码
		String code = (String) request.getSession().getAttribute("code");
		
		//3.6.2.判断验证码是否匹配
		if(!valistr.toLowerCase().equals(code.toLowerCase())){
			request.setAttribute("msg", "验证码不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.7.判断“点击注册用户”时，手机号码发送短信后跟点击注册时，该手机号码是否前后一致
		//3.7.1.获取session中发送短信后的手机号码（AjaxCheckSmsServlet）
		String smsphone = (String) request.getSession().getAttribute("smsphone");
		
		//3.7.2.进行判断
		if(!smsphone.equals(phonenumber)){//手机号码前后不一致
			request.setAttribute("msg", "× 该手机号码不是原来的号码，请重新获取验证码！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.8.对用户输入的密码参数进行MD5加密再保存到数据库
		//TODO 加密出现的小问题：数据库表设计中的密码字符大小设置了100，结果存储不了，改为varchar(200)
		String MdPassword = "";
		try {
			//3.8.1.获取密码参数，把密码放进加密方法里面
			MdPassword = MDUtil.getEncryptedPwd(password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		
		
		//3.9.判断是否重复提交
		// 1.从隐藏域中获取token
		String token1 = request.getParameter("token");
		// 2.从session中获取token
		Object tkObj = request.getSession().getAttribute("token");

		if (tkObj != null && token1.equals((String) tkObj)) {
			// 第一次添加,从session中清除token
			request.getSession().getAttribute("token");

		} else {// 非第一次添加
			throw new RuntimeException("请不要重复提交");

		}
		
		//4.注册用户（将注册信息保存到数据库）
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DaoUtils.getConnection();
			
			//4.1.判断用户名是否已存在
			String sql = "select * from user where username=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			if(rs.next()){//判断该用户是否存在
				//已存在
				request.setAttribute("msg", "用户名已存在！");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			
			//4.2.用户名不存在，将用户名注册数据保存到数据库
			sql = "insert into user values(null,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			//4.3.设置参数
			ps.setString(1, username);
			ps.setString(2, MdPassword);
			ps.setString(3, nickname);
			ps.setString(4, email);
			ps.setString(5, phonenumber);
			
			//4.4.执行sql语句
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//4.5.关闭资源
			DaoUtils.close(conn, ps, rs);
		}
		
		
		//5.提示用户注册成功，3秒之后跳转到主页
		response.getWriter().write("<h1 style='color:red;text-align:center'>" +
				"恭喜您注册成功, 3秒之后将会跳转到主页...</h1>");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
		
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
